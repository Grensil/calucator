package com.example.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    object Success : UiState()
    data class Error(val message: String) : UiState()
}

data class DiaryDto(val date: String,
    val content: String)

class HomeViewModel() : ViewModel() {

    private val _dateText = MutableStateFlow<String>("")
    val dateText = _dateText.asStateFlow()

    private val _contentText = MutableStateFlow<String>("")
    val contentText = _contentText.asStateFlow()

    private val _dateList = MutableStateFlow<List<DiaryDto>>(emptyList())
    val dateList = _dateList.asStateFlow()

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
                _dateList.update { currentList ->
                    currentList + newDiary
                }
            }
        }
    }


}