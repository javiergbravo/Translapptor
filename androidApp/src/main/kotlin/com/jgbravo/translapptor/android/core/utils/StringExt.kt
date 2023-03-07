package com.jgbravo.translapptor.android.core.utils

import java.util.Locale

fun String.capitalizeText(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) {
            it.titlecase(Locale.getDefault())
        } else {
            it.toString()
        }
    }
}