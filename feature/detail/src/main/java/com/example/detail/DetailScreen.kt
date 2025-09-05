package com.example.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailScreen(viewModel: DetailViewModel) {

    val dateList = viewModel.dateList.collectAsStateWithLifecycle(initialValue = emptyList())

    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(dateList.value.size) { index ->
                val item = dateList.value.get(index)
                Row(
                    Modifier
                        .fillMaxWidth()
                        .size(40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "date : ${item.date} content : ${item.content}",
                        modifier = Modifier.weight(1f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )

                    Image(
                        imageVector = Icons.Default.Delete, contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                viewModel.delete(index)
                            })
                }
            }
        }
    }

}