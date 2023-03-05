package com.jgbravo.translapptor.android.voice_to_text.di

import android.content.Context
import com.jgbravo.translapptor.android.voice_to_text.domain.AndroidVoiceToTextParser
import com.jgbravo.translapptor.voice_to_text.domain.VoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object VoiceToTextModule {

    @Provides
    @ViewModelScoped
    fun provideVoiceToTextParser(
        @ApplicationContext context: Context
    ): VoiceToTextParser = AndroidVoiceToTextParser(
        context = context
    )
}