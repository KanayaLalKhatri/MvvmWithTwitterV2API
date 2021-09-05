package com.kanaya.common.di

import org.koin.dsl.module
import com.kanaya.presentation.screens.map.ListTaskViewModel
import org.koin.android.viewmodel.dsl.viewModel


val viewModelModule = module {
    viewModel() {
        ListTaskViewModel(get())
    }
}