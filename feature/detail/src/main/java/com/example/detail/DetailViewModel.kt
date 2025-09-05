package com.example.detail

import androidx.lifecycle.ViewModel
import com.example.domain.GetDiaryUseCase

class DetailViewModel(private val getDiaryUseCase: GetDiaryUseCase) : ViewModel() {

    val dateList = getDiaryUseCase.getDiaryList()

    fun delete(index : Int) {
        getDiaryUseCase.deleteDiary(index)
    }
}