package com.example.kotlin_coroutineschannelsexample.LooperHandlerSplashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.kotlin_coroutineschannelsexample.databinding.ActivitySplashScreenBinding
import kotlin.concurrent.thread

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LooperHandlerActivity::class.java))
        }, 3000)

        Handler()

//        thread {
//            Thread.sleep(3000)
//            startActivity(Intent(this, LooperHandlerActivity::class.java))
//        }

    }
}