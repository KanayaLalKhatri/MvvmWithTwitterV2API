package com.kanaya.presentation.screens.map

import androidx.lifecycle.MutableLiveData
import com.kanaya.common.base.viewmodel.BaseViewModel
import com.kanaya.common.network.ResponseWrapper
import com.kanaya.core.repository.TaskRepository
import kotlinx.coroutines.launch

class ListTaskViewModel(private val taskRepository: TaskRepository) : BaseViewModel() {
    val tweetList = MutableLiveData<ResponseWrapper>()
    fun getTweets(query:String) {
        isLoading.value = true
        coroutineScope.launch {
            val myTaskApiResponse = taskRepository.getTweets(query)
            isLoading.value = false
            myTaskApiResponse?.let {
                tweetList.value = it
            } ?: run {}
        }
    }
}