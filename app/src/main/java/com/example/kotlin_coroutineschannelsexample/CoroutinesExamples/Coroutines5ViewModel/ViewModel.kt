package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines5ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val repository: Repository = Repository()

    fun doSomeApiCalls() {

        viewModelScope.launch{
            repository.returnSomeItems{human, animal ->
                Log.d("MyLog", human.toString())
                Log.d("MyLog", animal.toString())
            }
        }
    }
}