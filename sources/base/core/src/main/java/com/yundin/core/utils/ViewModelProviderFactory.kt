package com.yundin.core.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

private class ViewModelProviderFactory(
    private val createViewModel: () -> ViewModel
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return createViewModel() as T
    }
}

fun daggerViewModel(createViewModel: () -> ViewModel): ViewModelProvider.Factory {
    return ViewModelProviderFactory(createViewModel)
}
