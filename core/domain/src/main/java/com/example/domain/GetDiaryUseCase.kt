package com.example.domain

import kotlinx.coroutines.flow.Flow

class GetDiaryUseCase(private var repository: DiaryRepository) {

    fun getDiaryList() : Flow<List<DiaryDto>> {
        return repository.getDiaryList()
    }

    fun deleteDiary(index : Int) {
        repository.deleteDiaryList(index)
    }
}