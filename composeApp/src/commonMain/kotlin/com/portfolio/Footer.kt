package com.portfolio

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Footer(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth()) {
        Rule()
        Spacer(Modifier.height(22.dp))
        Caption("© 2026 ANATOLII BERCHANOV", AshGrey)
        Spacer(Modifier.height(40.dp))
    }
}
