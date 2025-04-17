package com.example.books.ui.screen.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.books.ui.components.CenterTittleTopAppBar
import com.example.books.model.BookModel

@SuppressLint("RememberReturnType")
@Composable
fun MainScreen(
    onClickItem: (BookModel) -> Unit
) {
    val allBooks = remember { mutableStateOf(BookModel.getData()) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Все") }

    val categories = remember {
        listOf("Все") + allBooks.value.map { it.category }.distinct()
    }

    val filteredBooks = remember(searchQuery, selectedCategory) {
        val filtered = allBooks.value.filter { book ->
            (selectedCategory == "Все" || book.category == selectedCategory) &&
                    book.name.contains(searchQuery, ignoreCase = true)
        }
        if (searchQuery.isNotBlank() && filtered.size == 1){
            filtered
        }else if (searchQuery.isNotBlank() && filtered.isEmpty()){
            emptyList()
        }else{
            filtered
        }
    }


    Scaffold(
        topBar = {
            CenterTittleTopAppBar(
                title = "Geeks Books",
                navigationIcon = Icons.AutoMirrored.Filled.ExitToApp
            ) {

            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Поиск") },
                singleLine = true,
                shape = RoundedCornerShape(8.dp)
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(categories.size) { index ->
                    val category = categories[index]
                    val isSelected = category == selectedCategory
                    val bgColor = if (isSelected) Color.Black else Color.LightGray
                    val textColor = if (isSelected) Color.White else Color.Black

                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(bgColor)
                            .clickable { selectedCategory = category }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = category,
                            color = textColor
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding( vertical = 10.dp),
                contentPadding = PaddingValues( horizontal = 16.dp)
            ) {
                items(filteredBooks) { book ->
                    BookItem(book, onClickItem = { onClickItem(it) })
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun BookItem(model: BookModel, onClickItem: (BookModel) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.LightGray)
            .clickable { onClickItem(model) }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        AsyncImage(
            model = model.image,
            contentDescription = model.name,
            modifier = Modifier
                .width(150.dp)
                .height(180.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .alignByBaseline(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = model.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = model.date,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = model.author,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen {}
}
