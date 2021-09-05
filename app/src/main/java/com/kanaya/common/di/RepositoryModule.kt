package com.kanaya.common.di

import com.kanaya.core.repository.TaskRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        TaskRepository(get())
    }
}