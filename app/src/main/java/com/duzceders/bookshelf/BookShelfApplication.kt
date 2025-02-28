package com.duzceders.bookshelf

import android.app.Application
import com.duzceders.bookshelf.data.AppContainer
import com.duzceders.bookshelf.data.DefaultAppContainer

class BookShelfApplication:Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}