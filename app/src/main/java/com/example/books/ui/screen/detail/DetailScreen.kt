package com.example.books.ui.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.books.ui.components.CenterTittleTopAppBar
import com.example.books.model.BookModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    padding: PaddingValues,
    title: MutableState<String>,
    book: BookModel,
    onNavigationClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CenterTittleTopAppBar(
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            title = book.name,
        ) {
            onNavigationClick()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = book.name,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = book.date,
                fontSize = 24.sp
            )
            Text(
                text = book.author,
                fontSize = 24.sp
            )
            Text(
                text = book.category,
                fontSize = 22.sp
            )
            Text(
                text = book.description,
                fontSize = 18.sp
            )
            AsyncImage(
                model = book.image,
                contentDescription = book.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}
