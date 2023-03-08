package com.jgbravo.translaptor.translate.presentation

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.jgbravo.translapptor.core.presentation.UiLanguage
import com.jgbravo.translapptor.translate.domain.history.models.HistoryItem
import com.jgbravo.translapptor.translate.domain.translate.Translate
import com.jgbravo.translapptor.translate.presentation.TranslateEvent
import com.jgbravo.translapptor.translate.presentation.TranslateState
import com.jgbravo.translapptor.translate.presentation.TranslateViewModel
import com.jgbravo.translapptor.translate.presentation.UiHistoryItem
import com.jgbravo.translaptor.translate.data.local.FakeHistoryDataSource
import com.jgbravo.translaptor.translate.data.remote.FakeTranslateClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

class TranslateViewModelTest {

    private lateinit var viewModel: TranslateViewModel

    private lateinit var client: FakeTranslateClient
    private lateinit var dataSource: FakeHistoryDataSource

    @BeforeTest
    fun setUp() {
        client = FakeTranslateClient()
        dataSource = FakeHistoryDataSource()
        val translate = Translate(
            client = client,
            historyDataSource = dataSource
        )

        viewModel = TranslateViewModel(
            coroutineScope = CoroutineScope(Dispatchers.Default),
            translate = translate,
            historyDataSource = dataSource
        )
    }

    @Test
    fun `State and history items are properly combined`() = runBlocking {
        viewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(TranslateState())

            val item = HistoryItem(
                id = 0,
                fromText = "Hello",
                fromLanguageCode = "en",
                toText = "Hola",
                toLanguageCode = "es"
            )
            dataSource.insertHistoryItem(item)

            val state = awaitItem()

            val expected = UiHistoryItem(
                id = item.id!!,
                fromText = item.fromText,
                fromLanguage = UiLanguage.byCode(item.fromLanguageCode),
                toText = item.toText,
                toLanguage = UiLanguage.byCode(item.toLanguageCode)
            )
            assertThat(state.history).isEqualTo(listOf(expected))
        }
    }

    @Test
    fun `Translate success - state properly updated`() = runBlocking {
        viewModel.state.test {
            awaitItem()

            viewModel.onEvent(TranslateEvent.ChangeTranslationText("test"))
            awaitItem()

            viewModel.onEvent(TranslateEvent.Translate)

            val loadingState = awaitItem()
            assertThat(loadingState.isTranslating).isTrue()

            val resultState = awaitItem()
            assertThat(resultState.isTranslating).isFalse()
            assertThat(resultState.toText).isEqualTo(client.translatedText)
        }
    }
}