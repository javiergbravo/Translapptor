package com.jgbravo.translapptor.presentation

import android.Manifest
import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.GrantPermissionRule
import com.jgbravo.translapptor.android.MainActivity
import com.jgbravo.translapptor.android.R
import com.jgbravo.translapptor.android.di.AppModule
import com.jgbravo.translapptor.android.di.DataModule
import com.jgbravo.translapptor.android.di.DomainModule
import com.jgbravo.translapptor.android.voice_to_text.di.VoiceToTextModule
import com.jgbravo.translapptor.translate.data.remote.FakeTranslateClient
import com.jgbravo.translapptor.translate.domain.translate.TranslateClient
import com.jgbravo.translapptor.voice_to_text.domain.FakeVoiceToTextParser
import com.jgbravo.translapptor.voice_to_text.domain.VoiceToTextParser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class, VoiceToTextModule::class, DomainModule::class, DataModule::class)
class VoiceToTextE2ETest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.RECORD_AUDIO
    )

    @Inject
    lateinit var fakeVoiceParser: VoiceToTextParser

    @Inject
    lateinit var fakeClient: TranslateClient

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun recordAndTranslate() = runBlocking<Unit> {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val parser = fakeVoiceParser as FakeVoiceToTextParser
        val client = fakeClient as FakeTranslateClient

        // TranslateScreen
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.record_audio))
            .performClick()

        // VoiceToTextScreen
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.record_audio))
            .performClick()

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.stop_recording))
            .performClick()

        composeRule.onNodeWithText(parser.voiceResult).assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.apply))
            .performClick()

        composeRule.onNodeWithText(parser.voiceResult).assertIsDisplayed()

        // TranslateScreen
        composeRule
            .onNodeWithText(context.getString(R.string.translate), ignoreCase = true)
            .performClick()

        composeRule.onNodeWithText(parser.voiceResult).assertIsDisplayed()
        composeRule.onNodeWithText(client.translatedText).assertIsDisplayed()
    }
}