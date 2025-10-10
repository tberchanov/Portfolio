package com.portfolio

import kotlin.js.js

actual object UrlOpener {
    actual fun openUrl(url: String) {
        openUrlInNewTab(url)
    }
}

private fun openUrlInNewTab(url: String) {
    js("window.open(url, '_blank')")
}
