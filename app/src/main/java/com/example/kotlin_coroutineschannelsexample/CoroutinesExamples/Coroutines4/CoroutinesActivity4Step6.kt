package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines4Step6Binding
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class CoroutinesActivity4Step6 : AppCompatActivity() {
    private lateinit var binding: ActivityCoroutines4Step6Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines4Step6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Теперь рассмотрим функцию .join()

        // Бывают ситуации, когда нам нужно отправить два запроса к серверу. Например, запросить
        // “котиков” и “владельцев котиков”, чтобы показать их на одном экране.

        // Но нам нужно показать всю информацию только когда будут обработаны оба запроса. Это
        // достаточно частый кейс.

        // Сделаем имитацию двух запроса в корутине из lifecycleScope с получение их джоб.
        lifecycleScope.launch {
            val job1 = launch {
                for (i in 0..10) {
                    Log.d("MyLog", "a")
                    delay(300)
                }
            }
            val job2 = launch {
                for (i in 0..10) {
                    Log.d("MyLog", "b")
                    delay(300)
                }
            }

            // Теперь мы хотим узнать когда они оба завершатся. Для этого есть операция joinAll() мы
            // перечисляем в ней ссылки на те корутины, которые хотим дождаться. И выведем сообщение после joinAll()
            joinAll(job1, job2)
            Log.d("MyLog", "AllCompleted")

            // Запускаем и функция joinAll() (она тоже является suspend-функцией) означает, что здесь
            // будет приостановка выполнения, пока обе корутины не завершат свое выполнение.

            // У нас поочередно выводятся буквы a и b

            // И по окончанию выводится сообщение AllCompleted при этом ничего не заблокировалось.
            // Таким образом можно обработать несколько зпросов, если мы хотим дождаться когда они все произойдут.
        }
    }
}