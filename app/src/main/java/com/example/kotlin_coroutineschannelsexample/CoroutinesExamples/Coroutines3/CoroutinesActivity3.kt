package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines3Binding

class CoroutinesActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityCoroutines3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            btnStep1.setOnClickListener {
                startActivity(Intent(this@CoroutinesActivity3, CoroutinesActivity3Step1::class.java))
            }

            btnStep2Callbacks.setOnClickListener {
                startActivity(Intent(this@CoroutinesActivity3, CoroutinesActivity3Step2Callbacks::class.java))
            }

            btnStep3Handler.setOnClickListener {
                startActivity(Intent(this@CoroutinesActivity3, CoroutinesActivity3Step3Handler::class.java))
            }

            btnStep4Looper.setOnClickListener {
                startActivity(Intent(this@CoroutinesActivity3, CoroutinesActivity3Step4Looper::class.java))
            }

            btnStep5RunOnUiThread.setOnClickListener {
                startActivity(Intent(this@CoroutinesActivity3, CoroutinesActivity3Step5RunOnUiThread::class.java))
            }

            btnStep6Coroutines.setOnClickListener {
                startActivity(Intent(this@CoroutinesActivity3, CoroutinesActivity3Step6Coroutines::class.java))
            }

            btnStep7StateMachine.setOnClickListener {
                startActivity(Intent(this@CoroutinesActivity3, CoroutinesActivity3Step7StateMachine::class.java))
            }

            btnStep8CoroutinesJob.setOnClickListener {
                startActivity(Intent(this@CoroutinesActivity3, CoroutinesActivity3Step8CoroutinesJob::class.java))
            }

            btnStep9AsyncDeferred.setOnClickListener {
                startActivity(Intent(this@CoroutinesActivity3, CoroutinesActivity3Step9CoroutinesAsyncDeferred::class.java))
            }

            btnStep10AsyncDeferredSimple.setOnClickListener {
                startActivity(Intent(this@CoroutinesActivity3, CoroutinesActivity3Step10CoroutinesAsyncDeferredSimple::class.java))
            }


        }
    }
}