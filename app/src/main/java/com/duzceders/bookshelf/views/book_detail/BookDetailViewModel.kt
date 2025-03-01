package com.duzceders.bookshelf.views.book_detail


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.duzceders.bookshelf.BookShelfApplication
import com.duzceders.bookshelf.R
import com.duzceders.bookshelf.data.BookShelfRepository
import com.duzceders.bookshelf.model.Book
import kotlinx.coroutines.launch

sealed interface BookDetailUIState {
    data class Success(val book: Book) : BookDetailUIState
    data object Loading : BookDetailUIState
    data class Error(val message: Int) : BookDetailUIState
}

class BookDetailViewModel(
    private val bookShelfRepository: BookShelfRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var bookDetailUIState: BookDetailUIState = BookDetailUIState.Loading
        private set

    val bookId = mutableStateOf("")

    init {
        bookId.value = savedStateHandle.get<String>("bookId") ?: ""
        getBook(bookId.value)
    }

    fun getBook(bookId: String) {
        bookDetailUIState = BookDetailUIState.Loading
        viewModelScope.launch {
            try {
                bookDetailUIState =
                    BookDetailUIState.Success(bookShelfRepository.getBookById(bookId))
            } catch (e: Exception) {
                Log.e("BookDetailViewModel", "Unexpected error: ${e.message}")
                bookDetailUIState = BookDetailUIState.Error(R.string.error)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookShelfApplication)
                val bookShelfRepository = application.container.bookShelfRepository
                BookDetailViewModel(bookShelfRepository, this.createSavedStateHandle())
            }
        }
    }
}