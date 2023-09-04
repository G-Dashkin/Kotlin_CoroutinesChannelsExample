package com.example.kotlin_coroutineschannelsexample.LooperHandlerMessageQueue

import android.os.Handler
import android.os.Message
import android.util.Log

class HandlerMessage: Handler() {

    companion object {
        val TASK_A = 1
        val TASK_B = 2
    }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        when(msg.what){
            TASK_A -> Log.d("MyLog", "Task A executed")
            TASK_B -> Log.d("MyLog", "Task B executed")
        }

    }

}