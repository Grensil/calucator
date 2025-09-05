package com.example.calculator.di

import com.example.data.DiaryRepositoryImpl
import com.example.domain.AddDiaryUseCase
import com.example.domain.DiaryRepository
import com.example.domain.GetDiaryUseCase

class AppModule {
    
    // Repository
    private val diaryRepository: DiaryRepository by lazy {
        DiaryRepositoryImpl()
    }
    
    // UseCase
    private val _addDiaryUseCase: AddDiaryUseCase by lazy {
        AddDiaryUseCase(diaryRepository)
    }
    private val _getDiaryListUseCase: GetDiaryUseCase by lazy {
        GetDiaryUseCase(diaryRepository)
    }
    
    fun addDiaryUseCase() : AddDiaryUseCase = _addDiaryUseCase
    fun getDiaryListUseCase() : GetDiaryUseCase = _getDiaryListUseCase

    companion object {
        @Volatile
        private var INSTANCE: AppModule? = null

        /**
         * Singleton 인스턴스 생성 (Thread-Safe)
         */
        fun getInstance(): AppModule {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppModule().also { INSTANCE = it }
            }
        }
    }
}