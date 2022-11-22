package com.rappi.featureC.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FeatureOneScreen(onClickNavigation: () -> Unit, onBackClick: () -> Unit) {
    InternalScaffold(
        title = "Feature One Screen",
        textBody = "Navigate internally to feature two",
        textButton = "Feature Two Screen",
        onBackClick = onBackClick,
        onClickNavigation = onClickNavigation
    )
}

@Composable
internal fun InternalScaffold(
    title: String,
    textBody: String,
    onBackClick: () -> Unit,
    textButton: String? = null,
    onClickNavigation: (() -> Unit)? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title, style = MaterialTheme.typography.h6)
                },
                contentColor = Color.Black,
                backgroundColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = textBody,
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )
            if (textButton != null && onClickNavigation != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = onClickNavigation) {
                        Text(
                            text = textButton,
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
internal fun PreviewFeatureOneScreen() {
    MaterialTheme {
        FeatureOneScreen(onClickNavigation = {}, onBackClick = {})
    }
}