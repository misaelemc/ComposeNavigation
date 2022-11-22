package com.rappi.featureA.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel

@Composable
fun FeatureAScreen(
    viewModel: FeatureAViewModel,
    featureBOnClick: () -> Unit,
    featureCOnClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                contentColor = Color.Black,
                backgroundColor = Color.White,
            ) {
                Text(text = "Title Feature A", style = MaterialTheme.typography.h6)
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Try navigating to one of these features",
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                InternalButton(onClick = featureBOnClick, "Feature B")
                InternalButton(onClick = featureCOnClick, "Feature C")
            }
        }
    }
}

@Composable
internal fun InternalButton(onClick: () -> Unit, text: String) {
    Button(onClick = onClick) {
        Text(
            text = text,
            style = MaterialTheme.typography.caption
        )
    }
}

@Preview
@Composable
internal fun Preview() {
    MaterialTheme {
        FeatureAScreen(
            viewModel = FeatureAViewModel(),
            featureBOnClick = {},
            featureCOnClick = {}
        )
    }
}