package com.example.calculator.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory<T : ViewModel>(
    private val create: () -> T
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return create() as T
    }
}

inline fun <reified T : ViewModel> createViewModelFactory(
    noinline create : () -> T
) : ViewModelProvider.Factory {
    return ViewModelFactory(create)
}