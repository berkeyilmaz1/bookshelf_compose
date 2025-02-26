package com.duzceders.bookshelf.views.bookshelf

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duzceders.bookshelf.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookShelfApp() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding()),
            color = MaterialTheme.colorScheme.background
        ) {
            val bookShelfViewModel: BookShelfViewModel =
                viewModel(factory = BookShelfViewModel.Factory)
            BookShelfView(
                retryAction = bookShelfViewModel::getBooks,
                bookShelfUIState = bookShelfViewModel.bookShelfUIState,
                contentPadding = innerPadding,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}