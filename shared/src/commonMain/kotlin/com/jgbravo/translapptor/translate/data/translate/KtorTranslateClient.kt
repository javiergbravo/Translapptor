package com.jgbravo.translapptor.translate.data.translate

import com.jgbravo.translapptor.NetworkConstants
import com.jgbravo.translapptor.core.domain.language.Language
import com.jgbravo.translapptor.translate.data.translate.models.TranslateRequest
import com.jgbravo.translapptor.translate.data.translate.models.TranslatedResponse
import com.jgbravo.translapptor.translate.domain.TranslateError
import com.jgbravo.translapptor.translate.domain.TranslateException
import com.jgbravo.translapptor.translate.domain.translate.TranslateClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException

class KtorTranslateClient(
    private val httpClient: HttpClient
) : TranslateClient {

    override suspend fun translate(
        fromText: String,
        fromLanguage: Language,
        toLanguage: Language
    ): String {
        val result = try {
            httpClient.post {
                url(NetworkConstants.BASE_URL + "/translate")
                contentType(ContentType.Application.Json)
                setBody(
                    TranslateRequest(
                        textToTranslate = fromText,
                        sourceLanguageCode = fromLanguage.langCode,
                        targetLanguageCode = toLanguage.langCode
                    )
                )
            }
        } catch (e: IOException) {
            throw TranslateException(TranslateError.SERVICE_UNAVAILABLE)
        } catch (e: Exception) {
            throw TranslateException(TranslateError.UNKNOWN_ERROR)
        }

        when (result.status.value) {
            in 200..299 -> Unit
            in 400..499 -> throw TranslateException(TranslateError.CLIENT_ERROR)
            500 -> throw TranslateException(TranslateError.SERVER_ERROR)
            else -> throw TranslateException(TranslateError.UNKNOWN_ERROR)
        }

        return try {
            result.body<TranslatedResponse>().translatedText
        } catch (e: Exception) {
            throw TranslateException(TranslateError.SERVER_ERROR)
        }
    }

}