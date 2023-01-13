package com.jgbravo.translapptor.translate.data.remote

import io.ktor.client.HttpClient

expect class HttpClientFactory {

    fun create(): HttpClient
}