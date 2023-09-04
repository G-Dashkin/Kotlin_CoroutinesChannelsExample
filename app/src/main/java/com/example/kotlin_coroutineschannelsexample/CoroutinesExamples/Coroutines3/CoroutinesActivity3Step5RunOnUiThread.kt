package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.kotlin_coroutineschannelsexample.R
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines3Step5RunOnUiThreadBinding
import kotlin.concurrent.thread

class CoroutinesActivity3Step5RunOnUiThread : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutines3Step5RunOnUiThreadBinding

    // 4) Далее, чтобы обрабатывать это сообщение, необходимо унаследоваться от этого сообщения и
    // переопределить handleMessage(). Для этого нужна не инстанция объекта Handler(), а его реализация,
    // через анонимный класс - object. Далее в консоль выводим, что это за сообщение.
    private val handler = object : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d("MyLog", "HANDLE_MSG $msg")
        }
    }

    // 5) Теперь при запуске приложения мы увидим сообщенеи в консоли с параметром 0 и значением 17 в объекте

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines3Step5RunOnUiThreadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLoad.setOnClickListener {
            loadData()
        }
        // 3) Также хэндлер можем принимать не только объект  есть Runnable, но и объект Message
        // т.е. если нам нужно не просто вызвать метод run() а делать какие то другие действия
        // в зависимости от ереданного сообщения, то мы вызываем метод .sendMessage() и в нем создаем
        // какое то сообщение при помощи Message.obtain(). Сюда мы передаем объект handler, тип
        // сообщения - просто какая то константа, на которую мы будем как то реагировать, передадим -0,
        // и в качестве объекта передадим какое то значение, например - 17
        // получается мы передаем сюда какую-то "константу", на которую мы будем реагировать
        // и любой "объект" (в данно случае это число 17)
        handler.sendMessage(Message.obtain(handler, 0, 17))
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
        thread {
            // 2) Тоже самое заменим здесь
            Thread.sleep(5000)
            runOnUiThread {
                callback.invoke("Moscow")
            }
        }
    }

    private fun loadTemperature(city: String, callback: (Int) -> Unit) {
        // 1) Конструкцию Handler(Looper.getMainLooper()).post{} можно заменить на runOnUiThread{}
        // и будет все тоже самое. Метод runOnUiThread{} вызывает какой то код на главном потоке, но под капотом
        // используется все тот же Handler
        thread {
            runOnUiThread {
                Toast.makeText(this, getString(R.string.loading_temperature_toast, city), Toast.LENGTH_SHORT).show()
            }
            Thread.sleep(5000)
            runOnUiThread {
                callback.invoke(17)
            }
        }
    }
}