package com.duzceders.bookshelf.views.bookshelf

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.duzceders.bookshelf.R
import com.duzceders.bookshelf.model.Book
import com.duzceders.bookshelf.model.BookShelfItems

@Composable
fun BookShelfView(
    bookShelfUIState: BookShelfUIState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (bookShelfUIState) {
        is BookShelfUIState.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        is BookShelfUIState.Error -> {
            ErrorScreen(retryAction = retryAction, modifier = modifier)
        }

        is BookShelfUIState.Success -> {
            BookShelfGrid(bookList = bookShelfUIState.data, contentPadding = contentPadding)
        }
    }
}


@Composable
fun BookShelfGrid(
    bookList: BookShelfItems,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = contentPadding
    ) {
        items(bookList.items.size) { index ->
            BookCard(book = bookList.items[index])
        }
    }
}

@Composable
fun BookCard(book: Book) {
    val imageUrl = book.volumeInfo.imageLinks?.thumbnail ?: "https://picsum.photos/536/354"
    Column {
        AsyncImage(
            modifier = Modifier
                .fillMaxHeight()
                .size(128.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl.replace("http", "https"))
                .crossfade(true)
                .listener(
                    onError = { request, throwable ->
                        Log.e("Coil", "Image loading failed: ${throwable.throwable.message}")
                    }
                )
                .build(),
            contentDescription = book.volumeInfo.title,
            placeholder = painterResource(id = R.drawable.loading_img),
            error = painterResource(id = R.drawable.ic_connection_error)
        )
        Text(
            text = book.volumeInfo.title,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(dimensionResource(R.dimen.small))
        )
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = stringResource(
                R.string.connection_error
            )
        )
        Text(
            stringResource(R.string.loading_failed),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(dimensionResource(R.dimen.medium))
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading),
        modifier = modifier
    )
}