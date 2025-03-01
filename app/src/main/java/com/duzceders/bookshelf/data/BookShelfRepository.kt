package com.duzceders.bookshelf.data

import com.duzceders.bookshelf.model.Book
import com.duzceders.bookshelf.model.BookShelfItems
import com.duzceders.bookshelf.network.BookShelfApi

interface BookShelfRepository {

    suspend fun getBooks(): BookShelfItems

    suspend fun getBookById(bookId: String): Book
}

class BookShelfRepositoryImpl(private val bookShelfApi: BookShelfApi) :
    BookShelfRepository {

    override suspend fun getBooks(): BookShelfItems = bookShelfApi.getBooks("jazz history")

    override suspend fun getBookById(bookId: String): Book = bookShelfApi.getBookById(bookId)
}