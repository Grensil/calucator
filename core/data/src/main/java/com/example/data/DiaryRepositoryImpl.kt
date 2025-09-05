package com.example.data

import com.example.domain.DiaryDto
import com.example.domain.DiaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DiaryRepositoryImpl : DiaryRepository {

    private val _diaries = MutableStateFlow<List<DiaryDto>>(emptyList())
    override val diaries = _diaries.asStateFlow()

    override fun addDiary(diary: DiaryDto) {
        _diaries.update { diaryList ->
            diaryList + diary
        }
    }

    override fun getDiaryList(): Flow<List<DiaryDto>> = diaries

    override fun deleteDiaryList(index: Int) {
        _diaries.update { diaryList ->
            diaryList.filterIndexed { idx, _ -> idx != index }
        }
    }

}