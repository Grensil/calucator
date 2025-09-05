package com.example.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface DiaryRepository {
    val diaries: StateFlow<List<DiaryDto>>
    fun addDiary(diary: DiaryDto)

    fun getDiaryList(): Flow<List<DiaryDto>>

    fun deleteDiaryList(index : Int)
}