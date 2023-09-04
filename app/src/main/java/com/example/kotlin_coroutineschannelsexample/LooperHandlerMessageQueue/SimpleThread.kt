package com.example.kotlin_coroutineschannelsexample.LooperHandlerMessageQueue

import android.os.SystemClock
import android.util.Log

class SimpleThread: Thread() {

    override fun run() {
        for (i in 0..5) {
            Log.d("MyLog", "run: $i")
            SystemClock.sleep(1000)
        }
        Log.d("MyLog", "End of run()")
    }

}