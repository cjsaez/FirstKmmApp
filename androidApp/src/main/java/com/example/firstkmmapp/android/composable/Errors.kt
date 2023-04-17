package com.example.firstkmmapp.android.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.firstkmmapp.android.R

fun PagingHeaderError(
    listScope: LazyListScope,
    modifier: Modifier = Modifier,
    errorText: String,
    retry: () -> Unit
) {
    PagingFooterError(
        listScope = listScope,
        modifier = modifier,
        errorText = errorText,
        retry = retry
    )
}

fun PagingFooterError(
    listScope: LazyListScope,
    modifier: Modifier = Modifier,
    errorText: String,
    retry: () -> Unit
) {
    listScope.item {
        Box(
            modifier = modifier
                .fillParentMaxWidth()
                .padding(vertical = 10.dp)
        ) {

            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(
                    space = 25.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = errorText,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )

                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                    shape = RoundedCornerShape(6.dp),
                    onClick = { retry.invoke() }
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 30.dp, vertical = 3.dp),
                        text = "Retry",
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    }
}

@Composable
fun FirstRequestError(
    modifier: Modifier = Modifier,
    errorText: String,
    retry: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
    ) {

        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(
                space = 25.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            Box(
                modifier = Modifier
                    .width(110.dp)
                    .height(110.dp)
                    .background(Color(0x5503DAC5), CircleShape)
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    modifier = Modifier.align(Alignment.Center),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_no_internet_connection),
                    contentDescription = ""
                )
            }

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = errorText,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                shape = RoundedCornerShape(6.dp),
                onClick = { retry.invoke() }
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 30.dp, vertical = 3.dp),
                    text = "Try again",
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}