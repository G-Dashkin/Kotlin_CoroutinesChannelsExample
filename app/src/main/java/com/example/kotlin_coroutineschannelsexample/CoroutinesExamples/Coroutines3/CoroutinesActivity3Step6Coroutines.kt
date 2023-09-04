package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.kotlin_coroutineschannelsexample.R
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines3Step6CoroutinesBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutinesActivity3Step6Coroutines : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutines3Step6CoroutinesBinding

    // 1) Итак, при стандартном подходе к асинхронному программированию у нас есть следующие проблемы:
    // - Стандартный подход к асинхронному прогроммированию - callback
    // - Использование callback-ов может привести к callback-Hell
    // - Работать с View можно только из главно потока, поэтому необходимо использовать Handler
    // - Одно из главных!!! У потоков нет жизненного цикла, из-за этого возможны различные баги и краши из-за утечки памяти
    // - AsyncTask и Loader - deprecated

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines3Step6CoroutinesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLoad.setOnClickListener {
            // 4) Теперь сделаем так, чтобы когда активити умирало, отменялись все запросы. Для этого
            // мы используем lifecycleScope и у него вызываем метод.launch {}. Перенесем сюда вызов
            // метода loadData(). Таким образом мы запустили метод внутри скоупа, который имеет такой же
            // жизненный цикл как и активти, поэтому если активити умрет, то наши запросы отменятся
            lifecycleScope.launch {
                loadData()
            }
            // 12) Таким образом мы переписали наше приложение с использование корутин
            // И так как мы используем lifecycleScope, то теперь при перевороте экрана, загрузка у
            // нас будет стртовать каждый раз в новой активити, а завершаться она будеи только один
            // раз в последней активти. Это Можно увидеть в логах
        }
    }

    // 5) Далее мы хотим, чтобы все долгие операции выполнялись "асинхронно", а главный поток не
    // блокировался. Сейчас поток блокируется на 5 секунд в методах loadCity() и loadTemperature()
    // Нам нужно, чтобы поток не блокировался, а метод loadData() приостанавливался на 5 секунд
    private suspend fun loadData() { // 11) Здесь тоже указываем suspend. Так как suspend-функция может быть вызвана только из корутины или из другой suspend-функции
        // 3) И вернем возвращаемый тип как было, если мы запустим код в таком виде, то приложение
        // зависнет так как мы усыпляем поток
        Log.d("MyLog", "Load started: $this")
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false
        val city = loadCity()
        binding.tvLocation.text = city
        val temp = loadTemperature(city)
        binding.tvTemperature.text = temp.toString()
        binding.progress.isVisible = false
        binding.buttonLoad.isEnabled = true
        Log.d("MyLog", "Load finished: $this")
    }
    // 2) Уберем создание фоновых потоков, метод runOnUiThread{} и коллбэки, верем возвращаемый тип
    private suspend fun loadCity(): String { // 8) здесь тоже помечаем словом suspend
        // 6) Для этого нужно, чтобы программа дошла до этой строчки и поняла - "мне нужно сейчас перестать
        // выполнять эту функцию (loadCity()), чтобы поток не был заблокирован, а через 5 секунд снова
        // вернуться в этот метод начиная со сточки return "Moscow" т.е. нужно, чтобы данных метод стал
        // прерываемым, чтобы он мог прервать свое выполнение на какой то строке и вернуться к нему
        // со строчки return "Moscow"
        delay(5000) //9) И вместо Thread.sleep(5000) мы вызываем suspend-функцию delay()
        return "Moscow"
    }

    // 7) Для этого нам нужно, чтобы этот метод можно было "приостанавливать", при этом не блокируя
    // поток для этого помечаем функцию ключевым словом suspend
    private suspend fun loadTemperature(city: String): Int {
        Toast.makeText(this, getString(R.string.loading_temperature_toast, city), Toast.LENGTH_SHORT).show()
        delay(5000) // 10) Здесь делаем тоже самое
        return 17
    }
}