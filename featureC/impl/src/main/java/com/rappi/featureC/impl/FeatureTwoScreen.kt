package com.rappi.featureC.impl

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FeatureTwoScreen(onBackClick: () -> Unit) {
    InternalScaffold(
        title = "Feature Two Screen",
        textBody = "This is the final screen of the internal navigation",
        onBackClick = onBackClick,
    )
}

@Preview
@Composable
internal fun PreviewFeatureTwoScreen() {
    MaterialTheme {
        FeatureTwoScreen(onBackClick = {})
    }
}