package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.kotlin_coroutineschannelsexample.R
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines3Step2CallbacksBinding
import kotlin.concurrent.thread

class CoroutinesActivity3Step2Callbacks : AppCompatActivity() {
    private lateinit var binding: ActivityCoroutines3Step2CallbacksBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines3Step2CallbacksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLoad.setOnClickListener {
            loadData()
        }
    }

    private fun loadData() {
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false
        // 2) Теперь мы стартуем метод loadCity() и в качестве параметра передаем коллбэк
        // т.к. он является единственным и последним параметра, можно сразу испльзовать фигурные скобки
        // Далее после завершеня загрузки будет выполнен код внутри фигурных скобок
        loadCity{
            binding.tvLocation.text = it
            //4) Здесь так же выполняем код внутри фигруных скобок, но приэтом передаем значение города в метод
            loadTemperature(it) {
                binding.tvTemperature.text = it.toString()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
            }

        }

        // 5) В итоге мы все сдедали так, что главный поток не будет ждать, когда завершаться все эти действия
        // Можно запустить приложение и проверить его работу, оно уподет, но мы смогли реализовать получение
        // значение из других потоков при помощи коллбэков. Но в больших программах у нас может
        // возникнуть Callback Hall, так как их может быть десятки

    }

    // 1) Такие тяжеловесные операции нужно запускать в других потоках
    // Cоздадим отдельный поток в методе loadCity(), но просто так мы не сможем вернуть значнеие из
    // другого потока. Для этого нужно использовать коллбэки
    // Логика коллбэков следующая - после окончания загрузки (усыпления потока) мы вызываем какой то
    // метод, который принимает строку в качестве параметра. Это можно сделать с помощью лямбды
    // метод loadCity() будет принимать коллбэк. Этот коллбэк является функцией, которая принимает в
    // качестве параметра строку и ничего не возвращает. Сам метод loadCity() больше ничего не будет
    // возвращать. А в теле функции мы вызовем метод коллбэка .invoke() и передадим сюда строку

    private fun loadCity(callback: (String) -> Unit) {
        thread {
            Thread.sleep(5000)
            callback.invoke("Moscow")
        }
    }

    // 3) Тоже самое нужно делать при загрузке температуры. Также создаем отдельный поток, переносим
    // в него код и вызываем метод, который принимает Int в качестве параметра (его передадим вторым параметром)
    private fun loadTemperature(city: String, callback: (Int) -> Unit) {
        thread {
            Toast.makeText(
                this,
                getString(R.string.loading_temperature_toast, city),
                Toast.LENGTH_SHORT
            ).show()
            Thread.sleep(5000)
            callback.invoke(17)
        }
    }
}