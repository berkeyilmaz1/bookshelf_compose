package com.duzceders.bookshelf.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.duzceders.bookshelf.views.book_detail.BookDetailView
import com.duzceders.bookshelf.views.bookshelf.BookShelfApp

enum class RouteNames(val route: String) {
    BookShelf("bookShelf"),
    BookDetail("bookDetail")
}

enum class RouteArguments(val argument: String) {
    BookId("bookId")
}

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = RouteNames.BookShelf.route) {
        composable(RouteNames.BookShelf.route) {
            BookShelfApp()
        }
        composable(
            RouteNames.BookDetail.route,
            arguments = listOf(navArgument(RouteArguments.BookId.argument) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString(RouteArguments.BookId.argument) ?: ""
            BookDetailView(bookId)
        }
    }
}