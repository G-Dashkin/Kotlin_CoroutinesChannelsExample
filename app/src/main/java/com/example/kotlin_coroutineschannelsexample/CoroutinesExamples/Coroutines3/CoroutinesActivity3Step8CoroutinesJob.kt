package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines3Step8CoroutinesJobBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutinesActivity3Step8CoroutinesJob : AppCompatActivity() {
    private lateinit var binding: ActivityCoroutines3Step8CoroutinesJobBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines3Step8CoroutinesJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLoad.setOnClickListener {
            // 4) Сделаем выполнение этих операций в разных корутинах, вместо вызова функции loadData()
            // и перед запуском корутин сделаем видимым прогресс бар и недоступной кнопку
//            lifecycleScope.launch { loadData() }

            binding.progress.isVisible = true
            binding.buttonLoad.isEnabled = false

            // 5) Теперь нам нужно реализовать возврат доступности кнопки и скрытие прогресс бара
            // после завершения работы корутин

            // 6) при запуске корутин мы сохраним объекты Job для управления корутиной в отдельные переменные
            val jobCity = lifecycleScope.launch {
                val city = loadCity()
                binding.tvLocation.text = city
            }

            val jobTemp = lifecycleScope.launch {
                val temp = loadTemperature()
                binding.tvTemperature.text = temp.toString()
            }

            // 7) Создадим еще одну корутину в которой вызовим методы .join() у этих джобов
            // Метод .join() остановит корутину в которой был вызван метод до тех пока работа
            // объекта jobCity не будет выполнена. После завершения ее работы, мы перейдем к
            // следующиму объекту - jobTemp. Здесь корутина снова остановится и будет ждать окончания
            // работы объекта jobTemp. Когда ее работа завершится. код продолжит выполняться дальше
            // и в этом месте мы сделаем кнопку снова активной и скроем прогресс бара.
            lifecycleScope.launch {
                jobCity.join()
                jobTemp.join()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
            }
            // 8) Теперь при запуске приложения функции работают одновременно, а не последовательно
            // В конце кнопку становится активной и прогресс бар скрывается.
            // в итоге общее время выполнения снижается до 5 секунд вместо 10

            // 9) Важно!!! Корутина запускается на выполнение сразу и метод .join() никак не влияет на это
            // этот метод позволяет только ожидать завершения данной работы и если его не вызвать, то
            // корутина так же будет исполняться

            // 10) Итак мы уяснили - если мы хотим выполнить две опирации одновременно, то их нужно
            // запускать в двух разных корутинах при помощи корутин билдера.
            // А для отслеживания состояния и управления корутин, мы используем экземпляр класса Job,
            // который возвращает метод .launch{} и вызываем у него соответствующие методы
            // При помощи объекта Job мы можем получить стостояние .isActive()-активна она или нет
            // былали она отменена-.isCancelled или завершена-.isCompleted или вызывать метод .join()
            // чтобы дожидаться ее окончания.
            // Сакже можно отменять отменять корутину через метод .cancel()

        }

    }

    private suspend fun loadData() {
        Log.d("MyLog", "Load started: $this")
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false
        val city = loadCity()
        binding.tvLocation.text = city
        // 2) Убираем передачу параметра city отсюда
        // теперь при запуске приложения все будет работать так же
        val temp = loadTemperature()
        binding.tvTemperature.text = temp.toString()
        binding.progress.isVisible = false
        binding.buttonLoad.isEnabled = true
        Log.d("MyLog", "Load finished: $this")
        // 3) Теперь загрузка температуры никак не зависит от загрузки города и мы могли бы стартовать их
        // одновременно, а сейчас эти функции выполнялись последовательно и вся операция занимает 10 секунд,
        // хотя могла бы занимать 5 секунд если бы мы выполняли их одновременно
    }


    private suspend fun loadCity(): String {
        delay(5000)
        return "Moscow"
    }

    // 1) Теперь уберем вывод тоста из метода loadTemperature() параметры в конструкторе, чтобы метод
    // не зависил от этих параметров. Сделаем просто задержку в 5 секунд и вернет значение температуры
    // точно так же как в метод loadCity()
    private suspend fun loadTemperature(): Int {
        delay(5000)
        return 17
    }
}