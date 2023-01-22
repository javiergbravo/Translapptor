package com.jgbravo.translapptor.android.di

import android.content.Context
import com.jgbravo.translapptor.translate.data.local.DatabaseDriverFactory
import com.jgbravo.translapptor.translate.data.remote.HttpClientFactory
import com.jgbravo.translapptor.translate.data.translate.KtorTranslateClient
import com.jgbravo.translapptor.translate.domain.translate.TranslateClient
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun provideTranslateClient(
        httpClient: HttpClient
    ): TranslateClient {
        return KtorTranslateClient(httpClient = httpClient)
    }

    @Provides
    @Singleton
    fun provideDatabaseDriver(
        @ApplicationContext context: Context
    ): SqlDriver {
        return DatabaseDriverFactory(context = context).create()
    }
}