package com.duzceders.bookshelf.views.book_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageUrl = book.volumeInfo.imageLinks?.thumbnail ?: "https://picsum.photos/536/354"

        // Book cover with elevation and rounded corners
       Surface(
            modifier = Modifier.padding(vertical = 16.dp),
            shadowElevation = 8.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl.replace("http", "https"))
                    .crossfade(true)
                    .build(),
                contentDescription = book.volumeInfo.title,
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.ic_connection_error),
                modifier = Modifier.size(240.dp, 320.dp)
            )
        }

        // Title with emphasis
        Text(
            text = book.volumeInfo.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        // Authors with styling
        book.volumeInfo.authors?.let { authors ->
            Text(
                text = authors.joinToString(", "),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Description in a scrollable container with styled background
        book.volumeInfo.description?.let { description ->
           Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Column {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()

                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text(
                                text = description,
                                style = MaterialTheme.typography.bodyMedium,
                                lineHeight = 24.sp,
                                textAlign = TextAlign.Justify
                            )
                        }
                    }
                }
            }
        }
    }
}
