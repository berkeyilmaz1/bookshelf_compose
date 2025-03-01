package com.duzceders.bookshelf.views.bookshelf

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.duzceders.bookshelf.R
import com.duzceders.bookshelf.model.Book
import com.duzceders.bookshelf.model.BookShelfItems
import com.duzceders.bookshelf.model.ImageLinks
import com.duzceders.bookshelf.model.SearchInfo
import com.duzceders.bookshelf.model.VolumeInfo

@Composable
fun BookShelfView(
    bookShelfUIState: BookShelfUIState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavController
) {
    when (bookShelfUIState) {
        is BookShelfUIState.Loading -> {
            LoadingView(modifier = modifier)
        }

        is BookShelfUIState.Error -> {
            ErrorView(retryAction = retryAction, modifier = modifier)
        }

        is BookShelfUIState.Success -> {
            BookShelfGrid(
                bookList = bookShelfUIState.data,
                contentPadding = contentPadding,
                navController = navController
            )
        }
    }
}


@Composable
fun BookShelfGrid(
    bookList: BookShelfItems,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = contentPadding
    ) {
        items(bookList.items.size) { index ->
            BookCard(book = bookList.items[index], navController = navController)
        }
    }
}


@Composable
fun BookCard(book: Book, navController: NavController) {
    val imageUrl = book.volumeInfo.imageLinks?.thumbnail ?: "https://picsum.photos/536/354"

    Card(
        modifier = Modifier
            .clickable { navController.navigate("bookDetail/${book.id}") }
            .padding(dimensionResource(R.dimen.small))
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(dimensionResource(R.dimen.medium))
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl.replace("http", "https"))
                    .crossfade(true)
                    .build(),
                contentDescription = book.volumeInfo.title,
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.ic_connection_error),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.small)))
            Text(
                text = book.volumeInfo.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.small)),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = book.volumeInfo.authors.first(),
                style = MaterialTheme.typography.titleSmall.copy(color = Color.Gray),
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.small)),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}


@Composable
fun ErrorView(retryAction: () -> Unit, modifier: Modifier = Modifier) {
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
fun LoadingView(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading),
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookShelfGridPreview() {
    val mockBooks = listOf(
        Book(
            kind = "book",
            id = "1",
            etag = "etag1",
            selfLink = "",
            volumeInfo = VolumeInfo(
                title = "Book One",
                authors = listOf("Author A"),
                publisher = "Publisher X",
                publishedDate = "2023",
                description = "A great book",
                pageCount = 300,
                printType = "BOOK",
                categories = listOf("Fiction"),
                maturityRating = "NOT_MATURE",
                allowAnonLogging = false,
                contentVersion = "1.0",
                imageLinks = ImageLinks(
                    smallThumbnail = "https://picsum.photos/200",
                    thumbnail = "https://picsum.photos/200"
                ),
                language = "en",
                previewLink = "",
                infoLink = "",
                canonicalVolumeLink = "",
                subtitle = "A great story",
                averageRating = 4,
                ratingsCount = 120
            ),
            searchInfo = SearchInfo(textSnippet = "Snippet 1")
        ),
        Book(
            kind = "book",
            id = "2",
            etag = "etag2",
            selfLink = "",
            volumeInfo = VolumeInfo(
                title = "Book Two",
                authors = listOf("Author B"),
                publisher = "Publisher Y",
                publishedDate = "2022",
                description = "An interesting book",
                pageCount = 250,
                printType = "BOOK",
                categories = listOf("Non-fiction"),
                maturityRating = "NOT_MATURE",
                allowAnonLogging = false,
                contentVersion = "1.0",
                imageLinks = ImageLinks(
                    smallThumbnail = "https://picsum.photos/201",
                    thumbnail = "https://picsum.photos/201"
                ),
                language = "en",
                previewLink = "",
                infoLink = "",
                canonicalVolumeLink = "",
                subtitle = "An insightful read",
                averageRating = 5,
                ratingsCount = 200
            ),
            searchInfo = SearchInfo(textSnippet = "Snippet 2")
        )
    )

    val mockBookShelfItems = BookShelfItems(
        kind = "books#bookshelf",
        totalItems = mockBooks.size.toLong(),
        items = mockBooks
    )

    BookShelfGrid(
        bookList = mockBookShelfItems,
        navController = NavController(LocalContext.current)
    )
}

