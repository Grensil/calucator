package com.example.calculator

import android.app.Application
import com.example.calculator.di.AppModule

class MyApplication : Application() {

    private val appModule: AppModule by lazy {
        AppModule.getInstance()
    }

    fun getWikipediaModule(): AppModule = appModule
}