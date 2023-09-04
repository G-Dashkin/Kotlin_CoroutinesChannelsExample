package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.kotlin_coroutineschannelsexample.R
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines3Step7StateMachineBinding
import kotlinx.coroutines.delay
import kotlin.concurrent.thread

class CoroutinesActivity3Step7StateMachine : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutines3Step7StateMachineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines3Step7StateMachineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLoad.setOnClickListener {
        // 13) Вместо lifecycleScope.launch вызываем нашу функцию используюшую методы без потоков
        // запускаем наше приложение, поток ни где не блокируется и все успешно отображается

//        lifecycleScope.launch {
//            loadData()
//        }
            loadDateWithoutThreadCoroutines()
        }
    }

    // 1) Теперь, что значит функция "приостанавливаемая" suspend - это означает, чтр при вызове
    // этой функции мы выйдим из методы в котором вызывается эта функция, до тех пор, пока он не
    // завершится. В нашем случае при вызове метода loadCity() мы выходим из loadData(), пока
    // он не завершится. А когда метод loadCity() завершит работу, то мы вернемся в loadData()
    // со следующей строки после вызова loadCity().
    // т.е. "приостанавливается" не работа функции, приостанавливается вызов метода на том потоке
    // (или в функции) на котором мы ее вызываем(так как мы выходим из метода в котором функция
    // вызывается) и идет ожидание этой функции, а саома функция работает.

    // суть - на Java все эти suspend-функции станут обычными функциями с коллбэками
    // по сути под капотом программирование с использованием корутин это программирование
    // с коллбэками


    // 2) Разберем метод loadData() по блокам, в этом методе есть 3 блока.
    private suspend fun loadData() {
        Log.d("MyLog", "Load started: $this")
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false
        val city = loadCity()  // [1 блок] - когда мы дойдем до вызова метода loadCity(), то нужно выйти из метода

        binding.tvLocation.text = city // [2 блок] - начнется когда мы выйдем из метода loadCity() и дойдем до этой строки
        val temp = loadTemperature(city) // далее будет блок с вызовом метода loadTemperature()


        binding.tvTemperature.text = temp.toString() // [3 блок] - является последним, здесь нет никаких долгих операций
        binding.progress.isVisible = false           // до окончания метода loadData() мы не будем из него выходить
        binding.buttonLoad.isEnabled = true
        Log.d("MyLog", "Load finished: $this")
    }

    // 3) Реализуем тот же метод loadData(), но без использованя корутин (а с коллбэками)
    // Так как метод выполняется за три шага передадим в конструктором переменную - step
    // Вторым параметром передадим объект типа Any, чтобы в него можно былло передавать все что угодно
    // изначально у него будет значение null. Далее прописываем шаги.

    private fun loadDateWithoutCoroutine(step: Int = 0, obj: Any? = null) {
        when(step) {
            0 -> {
                Log.d("MyLog", "Load started: $this")
                binding.progress.isVisible = true
                binding.buttonLoad.isEnabled = false
                loadCityWithoutCoroutine {    // 5) На шаге 0 мы начнем выполнение и вызовем метод с колбэком
                    loadDateWithoutCoroutine(1, it) // а внутри коллбэка мы вызовем ту же саму функцию, но шагом 1
                }                                   // а в качестве объектоа передади то, что вернул метод loadCity без коллбэка
            }
            1 -> {
                val city = obj as String                   // 6) На шаге 1 мы создаем переменную city
                binding.tvLocation.text = city
                loadTemperatureWithoutCoroutine(city){// и так же вызываем метод loadTemperature() без корутины
                    loadDateWithoutCoroutine(2, it)       // внутри коллбэка опять вызывем ту же саму функцию, но шагом 2
                }
            }
            2 -> {                                           // 7) на последнем шаге мы получаем температуру и делаем дальнейшие действия
                val temp = obj as Int
                binding.tvTemperature.text = temp.toString()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
                Log.d("MyLog", "Load finished: $this")
            }

        }

        // 8) В итоге мы реализовали такую же логику как в корутинах, но без использования непосредственно корутин
        // Понятно, что в корутинах все сложнее, но суть примерно такая же!
        // !!! Мы создали нашу State-машину !!! - метод с конструкцией when() и в зависимости от шага
        // Будут выполняться различные дейсвтия
    }

    // 4) Создадим обычные методы loadCity() и loadTemperature() без корутины, но с коллбэком, как мы делали раньше
    private fun loadCityWithoutCoroutine(callback: (String) -> Unit) {
        thread {
            Thread.sleep(5000)
            runOnUiThread {
                callback.invoke("Moscow")
            }
        }
    }

    private fun loadTemperatureWithoutCoroutine(city: String, callback: (Int) -> Unit) {
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

    // 9) При этом функция delay() работает еще проще, в ней не будет создан новый поток, а сразу
    // используется класс Handler и его метод .postDelayed(). Первым параметром этот метод принимает
    // тип Runnable, а вторым задержку в миллесекундаж
    private fun loadCityWithHandler(callback: (String) -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            callback.invoke("Moscow")
        }, 5000)
    }

    // 10) Тоже самое у функции loadTemperature()
    private fun loadTemperatureWithHandler(city: String, callback: (Int) -> Unit) {
        Toast.makeText(this, getString(R.string.loading_temperature_toast, city), Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({
            callback.invoke(17)
        }, 5000)
    }

    // 11) Нашу state-машину мы также переделаем на работу с этими функциями, которые исопльзуют
    // Handler и Looper вместо потоков
    private fun loadDateWithoutThreadCoroutines(step: Int = 0, obj: Any? = null) {
        when(step) {
            0 -> {
                Log.d("MyLog", "Load started: $this")
                binding.progress.isVisible = true
                binding.buttonLoad.isEnabled = false
                loadCityWithHandler {    // 5) На шаге 0 мы начнем выполнение и вызовем метод с колбэком
                    loadDateWithoutThreadCoroutines(1, it) // а внутри коллбэка мы вызовем ту же саму функцию, но шагом 1
                }                                   // а в качестве объектоа передади то, что вернул метод loadCity без коллбэка
            }
            1 -> {
                val city = obj as String                   // 6) На шаге 1 мы создаем переменную city
                binding.tvLocation.text = city
                loadTemperatureWithHandler(city){// и так же вызываем метод loadTemperature() без корутины
                    loadDateWithoutThreadCoroutines(2, it)       // внутри коллбэка опять вызывем ту же саму функцию, но шагом 2
                }
            }
            2 -> {                                           // 7) на последнем шаге мы получаем температуру и делаем дальнейшие действия
                val temp = obj as Int
                binding.tvTemperature.text = temp.toString()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
                Log.d("MyLog", "Load finished: $this")
            }

        }
    }

    // 12) Такая реализация паксимальна похожа на ту, что мы исползуем в корутинах.
    // - Новые потоки ни где не создаются
    // - Главный поток ни где не блокируется
    // - Под капотом мы исопльзуем state-машину

    private suspend fun loadCity(): String {
        delay(5000)
        return "Moscow"
    }

    private suspend fun loadTemperature(city: String): Int {
        Toast.makeText(this, getString(R.string.loading_temperature_toast, city), Toast.LENGTH_SHORT).show()
        delay(5000)
        return 17
    }


    // 14) Теперь должно быть понятно, почему suspend-функции можно вызывать только из других suspend-функций
    // или из корутины (создал loadData2() просто для пример)
    private fun loadData2() { // Если мы уберем слово suspend
        Log.d("MyLog", "Load started: $this")
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false
//        val city = loadCity()   // <- То не понятно что делает данная строчка, так как метод loadCity()
                                  // это метод с коллбэком, сам по себе он ничего не возвращает, а метод
                                  // loadData2() это просто последовательный код, поэтому непонятно, что делать на
                                  // на данной строке. По этой причине suspend-функции нельзя вызывать из обычной функции

//        binding.tvLocation.text = city
//        val temp = loadTemperature(city)


//        binding.tvTemperature.text = temp.toString()
        binding.progress.isVisible = false
        binding.buttonLoad.isEnabled = true
        Log.d("MyLog", "Load finished: $this")
    }

    // 15) При этом важное. suspend-функции никогда не должны блокировать поток!!!
    // при использованнии suspend из библиотеки Room или Retrofit этот принцип поддерживается
    // в этих библиотеках не нцжно явно создавать новые потоки, при вызове таких suspend-функций на
    // любом потоке, он не будет заблокирован.
    // Но при написаннии своей suspend-функции (как например loadDateWithoutThreadCoroutines()), мы сами
    // должны заботиться, чтобы она не блокировала поток. Просто модификатор suspend не сделаем это за нас

    // Например, если мы напишем таким образом (Thread.sleep() вместо delay()), то несмотря на то,
    // что здесь есть ключевое слово suspend данный метод заблокирует поток.
    // можно попробовать использовать его, вместо нашей реализации
    private suspend fun loadCity2(): String {
        Thread.sleep(5000)
        return "Moscow"
    }
    // Таким образом при написании своей suspend-функции, нужно обеспечить, чтобы она не блокировала
    // поток

}