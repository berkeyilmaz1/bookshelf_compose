package com.duzceders.bookshelf.network

import com.duzceders.bookshelf.model.Book
import com.duzceders.bookshelf.model.BookShelfItems
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookShelfApi {
    @GET("volumes")
    suspend fun getBooks(@Query("q") query: String): BookShelfItems

    @GET("volumes/{volume_id}")
    suspend fun getBookById(@Path("volume_id") volume_id: String): Book
}