package com.jgbravo.translapptor

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform