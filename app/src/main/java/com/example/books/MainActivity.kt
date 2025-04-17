package com.example.books

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.books.model.BookModel
import com.example.books.navigtion.Navigation
import com.example.books.ui.screen.detail.DetailScreen
import com.example.books.ui.screen.main.MainScreen
import com.example.books.ui.theme.BooksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BooksTheme {
                Navigation()
            }
        }
    }
}

@Composable
private fun Navigation() {
    val currentScreenState = remember { mutableStateOf(Navigation.MAIN) }
    val bookItem = remember { mutableStateOf(BookModel()) }

    when (currentScreenState.value) {
        Navigation.MAIN -> {
            MainScreen{
                bookItem.value = it
                currentScreenState.value = Navigation.DETAIL
            }
        }

        Navigation.DETAIL -> {
            DetailScreen(model = bookItem.value){
                currentScreenState.value = Navigation.MAIN
            }
        }
    }
}