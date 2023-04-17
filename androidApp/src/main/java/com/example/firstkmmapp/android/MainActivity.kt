package com.example.firstkmmapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.firstkmmapp.android.composable.PagingListView
import com.example.firstkmmapp.domain.model.Character
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {

                val viewModel: AndroidCharacterListViewModel by viewModel()
                val uiState by viewModel.pagingUiState.collectAsState()

                val itemView: @Composable (Character, Int, (Character) -> Unit) -> Unit  = Compose@{ item, _, onSelect ->
                    Text(text = "name: ${item.name}", modifier = Modifier.padding(bottom = 10.dp))
                }

                PagingListView(
                    modifier = Modifier.background(MaterialTheme.colors.background),
                    uiState = uiState,
                    receiveActions = viewModel.receivePagingListActions,
                    itemView = itemView
                ) {  }
            }
        }
    }
}
