package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines4Step3Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CoroutinesActivity4Step3 : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutines4Step3Binding

    // Нам нужна ссылка на базу данных. Чтобы не плодить код для ссылки, делать ее отдельно, через
    // ленивую инициализацию.
    val catDatabaseSuspend by lazy {
        Room.databaseBuilder(
            applicationContext,
            CatDatabaseSuspend::class.java,
            "database_suspend_cat"
        ).build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines4Step3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        addNewCat()
        checkCatCount() // Вызываем эту функцию в onCreate()

        // Запускаем приложение и у нас выводится количество объектов. При этом при перезапуске
        // количество объектов увеличивается.

        // Таким образом добавление и получение объектов с помощью корутины работает.

        // Также в логах видим, что корутина запустилась в главном потоке, так как мы не указали
        // ничего другого, а по умолчанию она запускается в главном потоке и в нем она обратилась к
        // базе данных, но андроид это пропустил. Падений в этом месте не вызывается, так как внутри
        // корутины (внутри suspend-функции) не будет блокировки нашего главного потока. Даже если
        // там будет много даннах, то он все равно будет переключаться между задачами.
        // НО!!! Так делать не нужно!
    }

    private fun addNewCat() {
        val newCat = Cat(name = "Vasya", breed = "Siberian", age = 3)
        lifecycleScope.launch {
            // В методе addNewCat() мы не указали поток в котором хотим запускать launch.
            // Выведем название потока в лог.
            Log.d("MyLog","Thread: ${Thread.currentThread().name}")
            catDatabaseSuspend.catDaoSuspend().insert(newCat)
        }
    }

    // Создадим метод, который будет проверять сколько объектов в базе и выводить это на экран.
    private fun checkCatCount() {
        // И здесь нам нужен async{} так как мы не просто обратимся к базе данных, но нам нужно еще
        // дождаться ответа т.е. получить массив этих котов, которые будут храниться в базе данных.
        // Здесь мы также используем lifecycleScope и вызываем у него .async {}

        // Помещаем результат выполнения корутины в отдельную переменную и массив с полученными
        // данными так же помещаем в отдельную переменную.
        val result = lifecycleScope.async {
            val cats = catDatabaseSuspend.catDaoSuspend().getCats()

            // Теперь мы получаем данные в каком то потоке и хотим установить их в главном потоке.
            // Для этого внутри async{} запустим launch с диспатчером main таким образом это будет
            // вызвано в главном потоке и устанавливаем количество объектов в базе (размер массива).
            launch(Dispatchers.Main) {
                binding.catCountTextView.text = "Cat count is ${cats.size}"
            }
        }

    }
}