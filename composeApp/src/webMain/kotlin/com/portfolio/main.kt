package com.portfolio

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlin.js.js

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    setPageTitle("Anatolii Berchanov")
    
    setFavicon()
    
    ComposeViewport {
        App()
    }
}

private fun setPageTitle(title: String) {
    js("document.title = title")
}

private fun setFavicon() {
    js("""
        const link = document.createElement('link');
        link.rel = 'icon';
        link.type = 'image/svg+xml';
        link.href = 'data:image/svg+xml;base64,' + btoa(`
            <svg width="32" height="32" viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg">
                <rect width="32" height="32" fill="#1D2024" rx="4"/>
                <text x="16" y="22" font-family="Arial, sans-serif" font-size="16" font-weight="bold" text-anchor="middle" fill="#00FFFF">AB</text>
            </svg>
        `);
        document.head.appendChild(link);
    """)
}