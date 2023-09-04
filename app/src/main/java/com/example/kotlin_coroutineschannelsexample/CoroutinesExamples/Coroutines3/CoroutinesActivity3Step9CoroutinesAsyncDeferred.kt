package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines3Step9CoroutinesAsyncDeferredBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutinesActivity3Step9CoroutinesAsyncDeferred : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutines3Step9CoroutinesAsyncDeferredBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines3Step9CoroutinesAsyncDeferredBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1) Теперь задача следующая - после нажатия на кнопку и установки значений в поля TextView
        // нам нужно вывести в тосте полученные значения. т.е. нам нужно как то получить значения из
        // корутин и передать в тост.

        // 2) Для этого нам нужно каким-то образом получить значение города и температуры из корутины
        // Чтобы получить данные из корутины, нужно использовать корутин-билдер .async{}, который
        // возвращает объект Deferred, а не Job. Но так как объект Deferred, наследуется от Job
        // у него есть все те же методы, что и у Job + дополнительные метдоы
        binding.buttonLoad.setOnClickListener {

            binding.progress.isVisible = true
            binding.buttonLoad.isEnabled = false

            // 3) Меняем билдер и можно явно указать возвращаемый тип Deferred, параметризированный строкой и числом
            val deferredCity: Deferred<String> = lifecycleScope.async {
                val city = loadCity()
                binding.tvLocation.text = city
                // 4) и теперь последнее значение, которе было указано в корутине будет тем объектом,
                // который вернется в объекте Deferred
                // в данном случае просто указываем city
                city
            }

            val deferredTemp: Deferred<Int> = lifecycleScope.async {
                val temp = loadTemperature()
                binding.tvTemperature.text = temp.toString()
                // 5) А здесь temp
                temp
            }

            lifecycleScope.launch {
                // 6) теперь вызываем у этих объектов не .join(), а .awit()- данный метод делает тоже
                // самое, что .join() - останавливает корутину и ждет показавершится работа, но при этом
                // данный методт вернет объект который лежит в этом объекте Deferred
                val city = deferredCity.await()
                val temp = deferredTemp.await()

                // 7) И передаем эти значения в Тост
                Toast.makeText(
                    this@CoroutinesActivity3Step9CoroutinesAsyncDeferred,
                    "City: $city Temp: $temp",
                    Toast.LENGTH_SHORT)
                    .show()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true

                // 8) Запускаем приложение, нажимаем на кнопку и нус все работает
            }
        }
    }

    private suspend fun loadData() {
        Log.d("MyLog", "Load started: $this")
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false
        val city = loadCity()
        binding.tvLocation.text = city
        val temp = loadTemperature()
        binding.tvTemperature.text = temp.toString()
        binding.progress.isVisible = false
        binding.buttonLoad.isEnabled = true
        Log.d("MyLog", "Load finished: $this")
    }

    private suspend fun loadCity(): String {
        delay(5000)
        return "Moscow"
    }

    private suspend fun loadTemperature(): Int {
        delay(5000)
        return 17
    }
}