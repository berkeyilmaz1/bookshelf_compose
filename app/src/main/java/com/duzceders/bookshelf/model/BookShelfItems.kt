package com.duzceders.bookshelf.model

data class BookShelfItems(
    val kind: String,
    val totalItems: Long,
    val items: List<Book>,
)

data class Book(
    val kind: String,
    val id: String,
    val etag: String,
    val selfLink: String,
    val volumeInfo: VolumeInfo,
    val searchInfo: SearchInfo,
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>,
    val publisher: String?,
    val publishedDate: String,
    val description: String?,
    val pageCount: Long,
    val printType: String,
    val categories: List<String>,
    val maturityRating: String,
    val allowAnonLogging: Boolean,
    val contentVersion: String,
    val imageLinks: ImageLinks,
    val language: String,
    val previewLink: String,
    val infoLink: String,
    val canonicalVolumeLink: String,
    val subtitle: String?,
    val averageRating: Long?,
    val ratingsCount: Long?,
)


data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String,
)


data class ListPrice(
    val amount: Double,
    val currencyCode: String,
)


data class SearchInfo(
    val textSnippet: String,
)
