package com.example.domain

class AddDiaryUseCase(private val repository: DiaryRepository) {

    fun addDiary(diary : DiaryDto) {
        repository.addDiary(diary)
    }
}