package com.example.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.AddDiaryUseCase
import com.example.domain.DiaryDto
import com.example.domain.DiaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    Scaffold(bottomBar = {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
        ) {
            Button(onClick = {
                viewModel.save(
                    viewModel.dateText.value, viewModel.contentText.value
                )
            }) {
                Text(text = "save")
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = {
                    viewModel.clearAllText()
                },
            ) {
                Text(text = "delete")
            }
        }

    }, content = { innerPaddings ->
        HomeContent(innerPaddings, viewModel)
    })

}

@Composable
fun HomeContent(innerPaddings: PaddingValues, viewModel: HomeViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = innerPaddings.calculateTopPadding(),
                bottom = innerPaddings.calculateBottomPadding()
            )
            .padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(text = "일기 작성", fontSize = 24.sp, color = Color.Gray)

        Spacer(
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth()
        )

        InputComponent(
            description = "날짜",
            singleLine = true,
            value = viewModel.dateText.collectAsState().value,
            onValueChange = viewModel::updateDate
        )

        InputComponent(
            description = "내용",
            singleLine = false,
            value = viewModel.contentText.collectAsState().value,
            onValueChange = viewModel::updateContent
        )

    }
}

@Composable
fun InputComponent(
    description: String,
    singleLine: Boolean,
    value: String,
    onValueChange: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        Text(text = description, fontSize = 16.sp, color = Color.Gray)

        Spacer(
            modifier = Modifier
                .width(16.dp)
                .heightIn(min = 60.dp)
        )

        BasicTextField(
            value = value,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 60.dp),
            onValueChange = onValueChange,
            singleLine = singleLine
        ) { innerTextField ->
            Box(
                modifier = Modifier.background(Color.Gray.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                if (value.isEmpty()) {
                    Text(text = "입력 해주세요", fontSize = 16.sp, color = Color.Gray)
                }
                innerTextField()
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun HomeScreenPreview() {
    val fakeDiaries = MutableStateFlow<List<DiaryDto>>(emptyList())
    val fakeRepo = FakeRepository(fakeDiaries)
    val previewViewModel = HomeViewModel(AddDiaryUseCase(fakeRepo))

    LaunchedEffect(Unit) {
        previewViewModel.updateDate("1")
        previewViewModel.updateContent("2\n3456788\n가나다라마바사\n%!@#!@#!\n'\n" +
                "QQQWASDASD!@#!@")
    }
    HomeScreen(viewModel = previewViewModel)
}

class FakeRepository(override val diaries: StateFlow<List<DiaryDto>>) : DiaryRepository {
    override fun addDiary(diary: DiaryDto) {
        TODO("Not yet implemented")
    }

    override fun getDiaryList(): Flow<List<DiaryDto>> {
        TODO("Not yet implemented")
    }

    override fun deleteDiaryList(index: Int) {
        TODO("Not yet implemented")
    }

}