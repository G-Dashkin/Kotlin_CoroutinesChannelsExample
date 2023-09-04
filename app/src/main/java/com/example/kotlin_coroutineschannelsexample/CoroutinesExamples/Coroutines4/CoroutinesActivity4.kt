package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines4Binding

class CoroutinesActivity4 : AppCompatActivity() {
    private lateinit var binding: ActivityCoroutines4Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            btnStep1.setOnClickListener {
                startActivity(Intent(this@CoroutinesActivity4, CoroutinesActivity4Step1::class.java))
            }

            btnStep2.setOnClickListener {
                startActivity(Intent(this@CoroutinesActivity4, CoroutinesActivity4Step2::class.java))
            }

            btnStep3.setOnClickListener {
                startActivity(Intent(this@CoroutinesActivity4, CoroutinesActivity4Step3::class.java))
            }

            btnStep4.setOnClickListener {
                startActivity(Intent(this@CoroutinesActivity4, CoroutinesActivity4Step4::class.java))
            }

            btnStep5.setOnClickListener {
                startActivity(Intent(this@CoroutinesActivity4, CoroutinesActivity4Step5::class.java))
            }

            btnStep6.setOnClickListener {
                startActivity(Intent(this@CoroutinesActivity4, CoroutinesActivity4Step6::class.java))
            }
        }
    }
}