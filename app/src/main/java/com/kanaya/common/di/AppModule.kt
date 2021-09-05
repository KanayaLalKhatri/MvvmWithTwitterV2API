package com.kanaya.common.di

import com.kanaya.common.constant.AppConstants
import org.koin.dsl.module

val appModule = module {
    single {
        AppConstants()
    }
}