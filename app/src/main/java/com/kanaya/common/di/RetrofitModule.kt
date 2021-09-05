package com.kanaya.common.di

import com.kanaya.common.network.retrofitService.RetrofitService
import org.koin.dsl.module

val retrofitModule = module {
    single {
        RetrofitService().getInstance()
    }
}