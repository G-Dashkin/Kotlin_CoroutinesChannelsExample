package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines1Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoroutinesActivity1 : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutines1Binding

    // Создаем 2 переменные с результатом, который мы будем получать
    private val RESULT_1 = "Result #1"
    private val RESULT_2 = "Result #2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            button.setOnClickListener {
                // при клике на кнопку вызываем общую функцию fakeApiRequest() для имитации получения
                // данные через Api и установки значения в TextView
                // IO, Main, Default
                CoroutineScope(IO).launch {
                    fakeApiRequest()
                }
            }

        }

    }

    // Функция установки значения в элемент TextView
    private fun setNewText(input: String) {
        val newText = binding.textView.text.toString() + "\n$input"
        binding.textView.text = newText
    }

    // Функция смены потока в корутине на главный поток и вызов функции setNewText()
    // suspend-функция
    private suspend fun setTextOnMainThread(input: String) {
        withContext(Main) {
            setNewText(input)
        }
    }

    // Функция логирования имени потока
    private fun logThread(methodName: String){
        Log.d("MyLog", "debug: $methodName")
    }

    // Функция логирования имени потока и приостановки на 1 секунду без блокировки
    // в конце возвращает глобальную переменную переменную с результатом запроса
    // т.е. функция имитиурет получение данные через кокой-то Api
    // suspend-функция
    private suspend fun getResult1FromApi(): String {
        logThread("getResult1FromApi")
        delay(1000)
        return RESULT_1
    }

    // Вторая функция для получения второй переменной
    private suspend fun getResult2FromApi(): String {
        logThread("getResult2FromApi")
        delay(1000)
        return RESULT_2
    }

    // Общая функция получения результата глобальной переменной из обеих функция, вывод в лог
    // полученного результата и установки занчения в TextView, через функцию setTextOnMainThread(result1)
    private suspend fun fakeApiRequest() {
        val result1 = getResult1FromApi()
        Log.d("MyLog", "debug: $result1")
        setTextOnMainThread(result1)

        val result2 = getResult2FromApi()
        Log.d("MyLog", "debug: $result2")
        setTextOnMainThread(result2)
    }

}