package com.duzceders.bookshelf.views.bookshelf

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.duzceders.bookshelf.BookShelfApplication
import com.duzceders.bookshelf.R
import com.duzceders.bookshelf.data.BookShelfRepository
import com.duzceders.bookshelf.model.Book
import com.duzceders.bookshelf.model.BookShelfItems
import kotlinx.coroutines.launch

sealed interface BookShelfUIState {
    data class Success(val data: BookShelfItems) : BookShelfUIState
    data object Loading : BookShelfUIState
    data class Error(val message: Int) : BookShelfUIState
}

class BookShelfViewModel(val bookShelfRepository: BookShelfRepository) : ViewModel() {
    var bookShelfUIState: BookShelfUIState by mutableStateOf(BookShelfUIState.Loading)
        private set


    init {
        getBooks()
    }

      fun getBooks() {
        bookShelfUIState = BookShelfUIState.Loading
        viewModelScope.launch {
            try {
                bookShelfUIState = BookShelfUIState.Success(bookShelfRepository.getBooks())
            } catch (e: Exception) {
                Log.e("BookShelfViewModel", "Unexpected error: ${e.message}")
                bookShelfUIState = BookShelfUIState.Error(R.string.error)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookShelfApplication)
                val bookShelfRepository = application.container.bookShelfRepository
                BookShelfViewModel(bookShelfRepository)
            }
        }
    }
}