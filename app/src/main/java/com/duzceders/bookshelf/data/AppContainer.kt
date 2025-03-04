package com.duzceders.bookshelf.data

import com.duzceders.bookshelf.network.BookShelfApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val bookShelfRepository: BookShelfRepository
}

class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://www.googleapis.com/books/v1/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    private val retrofitService: BookShelfApi by lazy {
        retrofit.create(BookShelfApi::class.java)
    }

    override val bookShelfRepository: BookShelfRepository by lazy {
        BookShelfRepositoryImpl(retrofitService)
    }
}