package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.kotlin_coroutineschannelsexample.R
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines3Step4LooperBinding
import kotlin.concurrent.thread

class CoroutinesActivity3Step4Looper : AppCompatActivity() {

    // 1) Класс Handler является устаревшим, по причинего того, что он использует Looper для
    // текущего потока. т.е. если вызвать это конструктор в главном потоке (как мы и сделали)
    // то будет использован Looper главного потока
    // Просто Handler нам больше не нужен, его можно удалить.
    //    private val handler = Handler()

    // 2) Класс Looper представляем собой очередь сообщений из объектов Rubbable. Эта очередь работает
    // на каком то потоке. Мы создаем Handler на главном потоке. Этот Handler использует очередь
    // сообщений из главного потока и ждет из него сообщения.
    // Мы можем отправить сообщение из любого потока в этот Looper, как делали с хэндлером напряму.
    // А Handler уже потом его обработает. Если мы отправляем объект Runnable, то Handler вызовим у
    // него метод run()

    // 3) Проблема лупера в том, что при его создании явно не указывается в каком потоке будет
    // происходить обработка сообщений и это может стать причиной крашей. От этого он и обявлен устаревшим
    private lateinit var binding: ActivityCoroutines3Step4LooperBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines3Step4LooperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLoad.setOnClickListener {
            loadData()
        }

    }

    private fun loadData() {
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false
        loadCity{
            binding.tvLocation.text = it
            loadTemperature(it) {
                binding.tvTemperature.text = it.toString()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
            }
        }
    }

    private fun loadCity(callback: (String) -> Unit) {
        // 4) Теперь создадим Handler внутри потока и чтобы он работал нужно вызвать метод .prepare()
        // у лeпера. - Looper.prepare()
        //------------------------------------------------------------------------------------------
        // т.е. можно было предварительно иницилизировать хэндлер до ментода onCreate()
        // private val handler = Handler()
        // и вместо кода ниже
//        Handler(Looper.getMainLooper()).post{
//            callback.invoke("Moscow")
//        }
        // можно было усделать так
//        Handler()
//        Looper.prepare()
//        handler.post{
//            callback.invoke("Moscow")
//        }
        // Но при таком способе явно не указываетс в каком потоке будет происходить обработака
        // очереди сообщений и из за этого приложение может падать
        //------------------------------------------------------------------------------------------
        // Вместо вызова просто хэндлера создадим хэндлер, с передачей в его конструктор нужного
        // лупера. Если нам нужен лупер с главного потока, то указываем Looper.getMainLooper()
        thread {
            Thread.sleep(5000)
            Handler(Looper.getMainLooper()).post{
                callback.invoke("Moscow")
            }
        }
    }

    private fun loadTemperature(city: String, callback: (Int) -> Unit) {

        thread {
            // 5) Используем хэндлер с лупером здесь и здесь
            Handler(Looper.myLooper()!!).post {
                Toast.makeText(this, getString(R.string.loading_temperature_toast, city), Toast.LENGTH_SHORT).show()
            }
            Thread.sleep(5000)
            Handler(Looper.getMainLooper()).post {
                callback.invoke(17)
            }
        }
    }

    // 6) Если нам нужно будет использовать лупер какого то другого потока, то мы используем
    // Looper.myLooper()!! (так как это нуллабельный объект испльзуем 2 восклицательных знака)
}
