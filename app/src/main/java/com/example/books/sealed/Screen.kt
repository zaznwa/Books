package com.example.books.sealed

import com.example.books.model.BookModel

sealed class Screen {
    object Main : Screen()
    data class Detail(val book : BookModel) : Screen()
}