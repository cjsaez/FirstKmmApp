package com.example.firstkmmapp.android.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    emptyText: String = "There are not items to show!"
){
    Box(modifier = modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colors.background)){

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = emptyText,
            style = MaterialTheme.typography.body1
        )
    }
}