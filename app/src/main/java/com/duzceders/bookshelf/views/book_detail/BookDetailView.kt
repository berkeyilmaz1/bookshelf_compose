package com.duzceders.bookshelf.views.book_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.duzceders.bookshelf.R
import com.duzceders.bookshelf.model.Book
import com.duzceders.bookshelf.views.bookshelf.ErrorView
import com.duzceders.bookshelf.views.bookshelf.LoadingView


@Composable
fun BookDetailView(
    bookDetailViewModel: BookDetailViewModel
) {
    when (
        bookDetailViewModel.bookDetailUIState
    ) {
        is BookDetailUIState.Success -> {
            BookDetail(book = (bookDetailViewModel.bookDetailUIState as BookDetailUIState.Success).book)
        }

        is BookDetailUIState.Loading -> {
            LoadingView()
        }

        is BookDetailUIState.Error -> {
            ErrorView(
                retryAction = { bookDetailViewModel.getBook(bookDetailViewModel.bookId.value) },
            )
        }
    }

}

@Composable
fun BookDetail(book: Book) {
    Column(modifier = Modifier.padding(16.dp)) {
        val imageUrl = book.volumeInfo.imageLinks?.thumbnail ?: "https://picsum.photos/536/354"
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl.replace("http", "https"))
                .crossfade(true)
                .build(),
            contentDescription = book.volumeInfo.title,
            placeholder = painterResource(id = R.drawable.loading_img),
            error = painterResource(id = R.drawable.ic_connection_error),
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = book.volumeInfo.title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = book.volumeInfo.authors?.joinToString(", ") ?: "",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = book.volumeInfo.description ?: "",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
