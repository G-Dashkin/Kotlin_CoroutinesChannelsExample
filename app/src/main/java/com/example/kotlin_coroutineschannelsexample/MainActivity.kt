package com.example.kotlin_coroutineschannelsexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines1.CoroutinesActivity1
import com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines2.CoroutinesActivity2
import com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines3.CoroutinesActivity3
import com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines4.CoroutinesActivity4
import com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines5ViewModel.CoroutinesActivity5
import com.example.kotlin_coroutineschannelsexample.LooperHandlerMessageQueue.LGMQActivity
import com.example.kotlin_coroutineschannelsexample.LooperHandlerSplashscreen.SplashScreenActivity
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            coroutinesExample1.setOnClickListener {
                startActivity(Intent(this@MainActivity, CoroutinesActivity1::class.java))
            }
            coroutinesExample2.setOnClickListener {
                startActivity(Intent(this@MainActivity, CoroutinesActivity2::class.java))
            }
            coroutinesExample3.setOnClickListener {
                startActivity(Intent(this@MainActivity, CoroutinesActivity3::class.java))
            }
            coroutinesExample4.setOnClickListener {
                startActivity(Intent(this@MainActivity, CoroutinesActivity4::class.java))
            }
            coroutinesExample5.setOnClickListener {
                startActivity(Intent(this@MainActivity, CoroutinesActivity5::class.java))
            }

            looperHandlerMessageQueue.setOnClickListener {
                startActivity(Intent(this@MainActivity, LGMQActivity::class.java))
            }
            looperHandlerSplashscreen.setOnClickListener {
                startActivity(Intent(this@MainActivity, SplashScreenActivity::class.java))
            }

        }
    }
}