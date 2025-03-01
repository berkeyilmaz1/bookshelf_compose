package com.duzceders.bookshelf.core.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.duzceders.bookshelf.views.book_detail.BookDetailView
import com.duzceders.bookshelf.views.book_detail.BookDetailViewModel
import com.duzceders.bookshelf.views.bookshelf.BookShelfApp

enum class RouteNames(val route: String) {
    BookShelf("bookShelf"),
    BookDetail("bookDetail/{bookId}")
}

enum class RouteArguments(val argument: String) {
    BookId("bookId")
}

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = RouteNames.BookShelf.route) {
        composable(RouteNames.BookShelf.route) {
            BookShelfApp(navController)
        }
        composable(
            RouteNames.BookDetail.route,
            arguments = listOf(navArgument(RouteArguments.BookId.argument) {
                type = NavType.StringType
            })
        ) {
            val bookId = remember {
                it.arguments?.getString(RouteArguments.BookId.argument)
            }

            val bookDetailViewModel: BookDetailViewModel =
                viewModel(factory = BookDetailViewModel.Factory)
            BookDetailView(
                bookDetailViewModel = bookDetailViewModel
            )
        }
    }
}