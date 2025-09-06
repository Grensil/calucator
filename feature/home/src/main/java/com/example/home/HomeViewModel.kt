package com.example.home

import androidx.lifecycle.ViewModel
import com.example.domain.AddDiaryUseCase
import com.example.domain.DiaryDto
import com.example.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class HomeViewModel(private val addDiaryUseCase: AddDiaryUseCase) : ViewModel() {

    private val _dateText = MutableStateFlow<String>("")
    val dateText = _dateText.asStateFlow()

    private val _contentText = MutableStateFlow<String>("")
    val contentText = _contentText.asStateFlow()

    private val _errorMessage = MutableStateFlow<UiState>(UiState.Idle)
    val errorMessage = _errorMessage.asStateFlow()

    fun updateDate(date: String) {
        _dateText.value = date
    }

    fun updateContent(content: String) {
        _contentText.value = content
    }

    fun clearAllText() {
        updateDate("")
        updateContent("")
    }

    fun save(date: String, content: String) {
        when {
            date.isEmpty() -> {
                _errorMessage.value = UiState.Error("날짜를 입력해주세요")
            }

            content.isEmpty() -> {
                _errorMessage.value = UiState.Error("내용을 입력해주세요")
            }

            else -> {
                val newDiary = DiaryDto(date = date, content = content)
                addDiaryUseCase.addDiary(newDiary)
                clearAllText()
            }
        }
    }

}