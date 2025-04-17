package com.example.books

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.books.model.BookModel
import com.example.books.navigtion.Navigation
import com.example.books.sealed.Screen
import com.example.books.sealed.Screen.*
import com.example.books.ui.components.CenterTittleTopAppBar
import com.example.books.ui.screen.detail.DetailScreen
import com.example.books.ui.screen.main.MainScreen
import com.example.books.ui.theme.BooksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var titleState = rememberSaveable { mutableStateOf("Библиотека") }
            var currentScreen by remember { mutableStateOf<Screen>(Main) }

            BooksTheme {
                Scaffold(
                    topBar = {
                        CenterTittleTopAppBar(
                            title = titleState.value,
                            navigationIcon = if (currentScreen != Main) Icons.AutoMirrored.Filled.ArrowBack else null,
                            onNavigationClick = if (currentScreen != Main) {
                                { currentScreen = Main; titleState.value = "Библиотека" }
                            } else null
                        )
                    }
                ) { padding ->
                    when (val screen = currentScreen) {
                        is Main -> MainScreen(
                            padding = padding,
                            title = titleState,
                            onClickItem = { clickedBook ->
                                currentScreen = Detail(clickedBook)
                                titleState.value = clickedBook.name ?: ""
                            }
                        )

                        is Detail -> DetailScreen(
                            padding = padding,
                            title = titleState,
                            book = screen.book,
                            onNavigationClick = {
                                currentScreen = Main
                                titleState.value = "Библиотека"
                            }
                        )

                    }
                }
            }
        }
    }
}

//@Composable
//private fun Navigation() {
//    val currentScreenState = remember { mutableStateOf(Navigation.MAIN) }
//    val bookItem = remember { mutableStateOf(BookModel()) }
//
//    when (currentScreenState.value) {
//        Navigation.MAIN -> {
//            MainScreen{
//                bookItem.value = it
//                currentScreenState.value = Navigation.DETAIL
//            }
//        }
//
//        Navigation.DETAIL -> {
//            DetailScreen(model = bookItem.value){
//                currentScreenState.value = Navigation.MAIN
//            }
//        }
//    }
//}