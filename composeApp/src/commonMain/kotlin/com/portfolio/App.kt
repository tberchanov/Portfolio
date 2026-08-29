package com.portfolio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * The whole site is one sheet: a single centred column of facts, scrolled top to
 * bottom. No nav, no descriptions — the canvas removed both.
 */
@Composable
@Preview
fun App() {
    PortfolioTheme {
        val metrics = LocalMetrics.current
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SheetBlack)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = SheetMaxWidth)
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(horizontal = metrics.sheetPadding),
                verticalArrangement = Arrangement.spacedBy(metrics.blockGap)
            ) {
                AnimatedSection { Hero() }
                AnimatedSection { WhatIDo() }
                AnimatedSection { SelectedWork() }
                AnimatedSection { ContactBlock() }
                Footer()
            }
        }
    }
}
