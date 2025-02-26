package com.duzceders.bookshelf.data

import com.duzceders.bookshelf.network.BookShelfApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val bookShelfRepository: BookShelfRepository
}


class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://www.googleapis.com/books/v1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: BookShelfApi by lazy {
        retrofit.create(BookShelfApi::class.java)
    }

    override val bookShelfRepository: BookShelfRepository by lazy {
        BookShelfRepositoryImpl(retrofitService)
    }
}