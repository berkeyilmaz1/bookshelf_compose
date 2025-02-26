package com.duzceders.bookshelf.data

import com.duzceders.bookshelf.model.Book
import com.duzceders.bookshelf.network.BookShelfApi

interface BookShelfRepository {
    /// This function will return a list of books
    suspend fun getBooks(): List<Book>

    /// This function will return a book by its id
    // suspend fun getBookById(id: Int): Book
}

class BookShelfRepositoryImpl(private val BookShelfApi: BookShelfApi) :
    BookShelfRepository {
    /// This function will return a list of books by calling the getBooks function from BookShelfApi
    /// it will return a list of books with the keyword "turkish history"
    override suspend fun getBooks(): List<Book> = BookShelfApi.getBooks("turkish history")
    /// This function will return a book by its id by calling the getBookById function from BookShelfApi
    // override suspend fun getBookById(id: Int): Book = BookShelfApi.getBookById(bookId)
}