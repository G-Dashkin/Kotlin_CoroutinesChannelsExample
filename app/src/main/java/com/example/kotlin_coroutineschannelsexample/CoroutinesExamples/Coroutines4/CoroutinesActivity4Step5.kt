package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines4Step5Binding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutinesActivity4Step5 : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutines4Step5Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines4Step5Binding.inflate(layoutInflater)
        setContentView(binding.root)
        // Теперь рассмотрим преимущество lifecycleScope – корутины, запущенные в нем умирают вместе
        // со смертью активити. Создадим еще одну активити для примера.

        // Теперь у нас есть активити в которой мы будем запускать корутину. Добавим ее.
        // Внутри корутины мы будем выводить числа с увеличением на 1. Эта корутина никогда не завершится.
        lifecycleScope.launch {
            var i = 0
            while (true) {
                Log.d("MyLog", "Number $i")
                delay(300) // Добавим delay() для упрощения кода.
                i++
            }
        }
    }

    // И при старте второй активити для наглядности добавим onDestroy() вывод в лог.
    override fun onDestroy() {
        Log.d("MyLog", "The end of activity")
        super.onDestroy()
    }

    // Запускаем смотрим лог. У нас пошел вывод большого количества чисел. Т.е. бесконечный цикл выводи чисел.

    // Далее, Закрываем приложение, нажимаем назад.
    // И в лог выводится сообщение - "The end of activity" из метода onDestroy(), что активити уничтожено.
    // Корутина больше не работает.

    // В итоге у нас нет проблем с утечкой памяти, как это было с потоками или asyncTask()
}