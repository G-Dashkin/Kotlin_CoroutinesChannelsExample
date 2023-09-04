package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.kotlin_coroutineschannelsexample.R
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines3Step3HandlerBinding
import kotlin.concurrent.thread

class CoroutinesActivity3Step3Handler : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutines3Step3HandlerBinding

    // 2) Для передачи данных между разными потоками, в Android создан класс.
    // Объект класса Handler можно создать на "главном потоке" и затем ему можно передавать объекты
    // runnable из любого потока и тогда метод run() будет вызван на главном потоке.

    // Создадим объект Handler и теперь ему можно передать объекты Runnable
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines3Step3HandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 5) Теперь можно запустить приложение и нажать кнопку загурузки, теперь прилоежение не зависнет
        binding.buttonLoad.setOnClickListener {
            loadData()
        }

    }

    // 1) Теперь разберем причину падения приложения - "Только оригинальный поток, который создал
    // вью, может взаимодействовать с этой вью" т.е. работать с вью можно только из главного потока.
    // Мы же создаем одельный поток и вызываем метод коллбэка не в главном потоке.
    // Внутри коббэкаов мы работаем с вью элементами, поэтому возникает кращ
    private fun loadData() {
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false
        loadCity{
            binding.tvLocation.text = it
            //4) Здесь так же выполняем код внутри фигруных скобок, но приэтом передаем значение города в метод
            loadTemperature(it) {
                binding.tvTemperature.text = it.toString()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
            }
        }
    }

    private fun loadCity(callback: (String) -> Unit) {
        // 3) Передавать объекты хэндлеру мы будем в других потоках
        thread {
            Thread.sleep(5000)
            // когда загрузк выполнена, то метод коббэка нам нужно вызвать на главном потоке
            // для этого передаем хэндлеру новый объект типа Runnable через метод .post{}
            handler.post{
                // таким образом метод коллбэка будет вызван не в потоке, в кльлолм выполнялась
                // операция, а в потоке хэндлера. Хэндлер мы создали на главном потоке, поэтому и
                // методы run (данное лямбда выражение будет вызвано на главном потоке)
                callback.invoke("Moscow")
            }
        }
    }

    // 4) Тоже самое сдалаем в методе загрузки температуры
    private fun loadTemperature(city: String, callback: (Int) -> Unit) {
        thread {
            handler.post {
                Toast.makeText(this, getString(R.string.loading_temperature_toast, city), Toast.LENGTH_SHORT).show()
            }
            Thread.sleep(5000)
            handler.post {
                callback.invoke(17)
            }
        }
    }
}