package com.duzceders.bookshelf.network

import com.duzceders.bookshelf.model.Book
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookShelfApi {

    @GET("volumes")
    suspend fun getBooks(@Query("q") query: String): List<Book>

    @GET("volumes/{volume_id}")
    suspend fun getBook(@Path("volume_id") volumeId: String): Book
}