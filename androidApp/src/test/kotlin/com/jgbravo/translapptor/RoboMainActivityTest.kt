package com.jgbravo.translapptor

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
import com.jgbravo.translapptor.android.R.string
import com.jgbravo.translapptor.android.di.AppModule
import com.jgbravo.translapptor.android.di.DataModule
import com.jgbravo.translapptor.android.di.DomainModule
import com.jgbravo.translapptor.android.voice_to_text.di.VoiceToTextModule
import com.jgbravo.translapptor.translate.domain.translate.TranslateClient
import com.jgbravo.translapptor.voice_to_text.domain.VoiceToTextParser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import fakes.translate.data.remote.FakeTranslateClient
import fakes.voice_to_text.domain.FakeVoiceToTextParser
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.LooperMode
import javax.inject.Inject

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
@UninstallModules(AppModule::class, VoiceToTextModule::class, DomainModule::class, DataModule::class)
class RoboMainActivityTest {

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule(order = 2)
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.RECORD_AUDIO
    )

    @Inject
    lateinit var fakeVoiceParser: VoiceToTextParser

    @Inject
    lateinit var fakeClient: TranslateClient

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun recordAndTranslate() = runBlocking<Unit> {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val parser = fakeVoiceParser as FakeVoiceToTextParser
        val client = fakeClient as FakeTranslateClient

        // TranslateScreen
        composeRule
            .onNodeWithContentDescription(context.getString(string.record_audio))
            .performClick()

        // VoiceToTextScreen
        composeRule
            .onNodeWithContentDescription(context.getString(string.record_audio))
            .performClick()

        composeRule
            .onNodeWithContentDescription(context.getString(string.stop_recording))
            .performClick()

        composeRule.onNodeWithText(parser.voiceResult).assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription(context.getString(string.apply))
            .performClick()

        composeRule.onNodeWithText(parser.voiceResult).assertIsDisplayed()

        // TranslateScreen
        composeRule
            .onNodeWithText(context.getString(string.translate), ignoreCase = true)
            .performClick()

        composeRule.onNodeWithText(parser.voiceResult).assertIsDisplayed()
        composeRule.onNodeWithText(client.translatedText).assertIsDisplayed()
    }
}