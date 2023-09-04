package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines2Binding
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class CoroutinesActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityCoroutines2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
//--------------------------------------------------------------------------------------------------
            // ---[main Thread]
            // Если мы запустим тост таким образом на главном потоке, то экран зависнет
            btnMainThread.setOnClickListener {
                for(i in 0..100000){
                    Toast.makeText(this@CoroutinesActivity2, "i=$i", Toast.LENGTH_SHORT).show()
                }
            }
            // ---[Background Thread]
            // Селаем тоже самое, но в другом потоке и выведем в лог получаемые значения в цикле
            // при  этом нажмем несколько раз кнопку, мы получим несколько потоков с циклами
            btnBackgroundThread.setOnClickListener {
                val thread = object  : Thread() {
                    override fun run() {
                        super.run()
                        for(i in 0..100000){
                            Log.d("MyLog", i.toString())
//                            Toast.makeText(this@CoroutinesActivity2, "i=$i", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                thread.start()
            }
            // ---[Toast]
            // Теперь запустим пару потокоу прошлым способом и попробуем вывести тост
            // Таким образом мы сожем запускать какие слжные операции в отдельных потоках и работать на
            // на главном потоке (показывать тост)
            btnToast.setOnClickListener {
                Toast.makeText(this@CoroutinesActivity2, "Hi Toast! ", Toast.LENGTH_SHORT).show()
            }
            // ---[Thread TextView]
            // Теперь в отдельном потоке установим значение в элемент TextView
            // Получается мы делаем сложную операцию, которая не блокирует главный поток в отдельном потоке
            // а значение в TextView элемент устанавливаем в главном потоке (из фонового потока как бы)
            // Так же мы все еще можем запускать тост на главном потоке
            btnThreadTextView.setOnClickListener {
                val thread = object  : Thread() {
                    override fun run() {
                        super.run()
                        for(i in 0..100000){
                            tvCount.text = i.toString()
                        }
                    }
                }
                thread.start()
            }
//--------------------------------------------------------------------------------------------------
            // ---[Coroutine Example 1]
            // теперь сделать тоже самое в корутине через глобальный скоуп
            // выведем в лог две запсили, одну в корутине, другую на главном потоке
            // и порядок такой, что сначала выводится в лог на главном потоке, а уже потом в корутине
            // Также добавим названия потоков
            btnCoroutine1.setOnClickListener {
                GlobalScope.launch {
                    Log.d("MyLog", "My Coroutines-WorkerThread: ${Thread.currentThread().name}")
                }
                Log.d("MyLog", "My UI Thread: ${Thread.currentThread().name}")
            }

            // ---[Coroutine Example 2]
            // Запустим 3 корутины с задержкой в 3 секунды через функцию delay() и мы видим что были созданы 3 разные корутины с
            // с разными названиями
            // delay() является suspend-функцией, которая может приостанавливаться, чтобы не блокировать
            // основной поток. Таким образом примерняя delay() мы имитируем задержку так же как если бы
            // это было с любой доругой функцией выполняющую сложную операцию и ее нужно запускать не в
            // главном потоке.
            btnCoroutine2.setOnClickListener {
                GlobalScope.launch {
                    delay(3000L)
                    Log.d("MyLog", "My Coroutines-WorkerThread One: ${Thread.currentThread().name}")
                }

                GlobalScope.launch {
                    delay(3000L)
                    Log.d("MyLog", "My Coroutines-WorkerThread Two: ${Thread.currentThread().name}")
                }

                GlobalScope.launch {
                    delay(3000L)
                    Log.d("MyLog", "My Coroutines-WorkerThread Three: ${Thread.currentThread().name}")
                }
                Log.d("MyLog", "My UI Thread: ${Thread.currentThread().name}")
            }

            // ---[Coroutine Example 3]
            // Ниже пример запуска отдельной suspend-функции в отдной корутине, видно, по названию, что
            // у нас одна и та же корутина
            btnCoroutine3.setOnClickListener {
                GlobalScope.launch {
                    delay(2000L)
                    val result = loadImage()
                    Log.d("MyLog", "Fun load Image: ${Thread.currentThread().name} $result")
                    Log.d("MyLog", "Coroutine name: ${Thread.currentThread().name}")
                }
            }

//--------------------------------------------------------------------------------------------------
            // ---[Io Main Threads]
            // Теперь сделаем пример выполенения сложной операции в корутине, в фоновом потоке и
            // переведенм поток на главный, чтобы установить занчение в TextView
            btnIoMainThread.setOnClickListener {
                GlobalScope.launch(Dispatchers.IO) {
                    delay(2000)
                    Log.d("MyLog", "Coroutine IO: ${Thread.currentThread().name}")

                    withContext(Dispatchers.Main) {
                        Log.d("MyLog", "Change Dispatchers: ${Thread.currentThread().name}")
                        tvMianIO.text = loadImage()
                    }
                }
            }
            // ---[GlobalScope]
            // Выведем в лог 3 названия потока. В данном случаве сначала выводится все, что на главном потоке
            // только после этого отрабатывается корутина и выводится в лог
            btnGlobalScope.setOnClickListener {
                Log.d("MyLog", "Main Thread 1: ${Thread.currentThread().name}")
                GlobalScope.launch {
                    Log.d("MyLog", "My Coroutine: ${Thread.currentThread().name}")
                }
                Log.d("MyLog", "Main Thread 2: ${Thread.currentThread().name}")
            }

            // ---[RunBlocking]
            // Теперь запустим пример в runBlocking
            // Все созданные выводы в лог отработаются последовательно
            // Таким образом runBlocking хоть и не является областью корутины, но в ней можно запускать
            // suspend-функции как и в любом корутин скоупе
            btnRunBlocking.setOnClickListener {
                Log.d("MyLog", "Before runBlocking: ${Thread.currentThread().name}")
                runBlocking {
                    Log.d("MyLog", "Start of runBlocking: ${Thread.currentThread().name}")
                    delay(5000L)
                    Log.d("MyLog", "End of runBlocking: ${Thread.currentThread().name}")
                }
                Log.d("MyLog", "After runBlocking: ${Thread.currentThread().name}")
            }

            // ---[RunBlocking Launch]
            // Другой пример, запустим две корутины в runBlocking{} обе с задержкой в 3 секунды
            // Так как эти корутины работают асинхронно, 3 секунды идут одновременно в каждой
            // т.е. всего проходит 6 секунд
            // В результате,
            // 1) Сначала отрабатывается вывод в лог вне runBlocking {}
            // 2) Далее идет приостановка на 5 секунд в runBlocking {}
            // 3) после этого запускаются ве корутины с задержкой в секунды, они отрабатываются
            // сообщения в каждой выводятся в лог
            // 4) По прошествии 5 секунд выводится вывод в лог End of runBlocking
            // 5) И выйдя из области runBlocking выводится сообщение After runBlocking
            // получается такой синхронный стиль
            btnRunBlockingLaunch.setOnClickListener {
                Log.d("MyLog", "Before runBlocking: ${Thread.currentThread().name}")
                runBlocking {
                    launch(Dispatchers.IO) {
                        delay(3000L)
                        Log.d("MyLog", "Coroutines IO 1: ${Thread.currentThread().name}")
                    }
                    launch(Dispatchers.IO) {
                        delay(3000L)
                        Log.d("MyLog", "Coroutines IO 2: ${Thread.currentThread().name}")
                    }
                    Log.d("MyLog", "Start of runBlocking: ${Thread.currentThread().name}")
                    delay(5000L)
                    Log.d("MyLog", "End of runBlocking: ${Thread.currentThread().name}")
                }
                Log.d("MyLog", "After runBlocking: ${Thread.currentThread().name}")
            }
//--------------------------------------------------------------------------------------------------
            // ---[Job Example 1]
            // Теперь разберем работу объекта Job. С его помощью можно управлять работой корутины.
            // создав инстанцию корутины (инстанцию объекта Job) мы можем вызвать у нее метод .join()
            // данный метод приостаноивит выполнение дальнейших функций в потоке до тех пор, пока
            // работа корутины не будет завершени. т.е. этот метод заставит ждать завершения выполнения корутины
            // Так вывод в лог "Main Thread" произойдет только после завершения работы корутины.
            btnJobExample1.setOnClickListener {
                val myJob: Job = GlobalScope.launch(Dispatchers.Default) {
                    repeat(5) {
                        Log.d("MyLog", "My coroutines default still is working")
                        delay(1000L)
                    }
                }

                runBlocking {
                    delay(3000L)
                    myJob.join()
                    Log.d("MyLog", "Main Thread")
                }
                Log.d("MyLog", "MyJob is Finishing")
                Log.d("MyLog", "----------------------------------------------")
            }

            // ---[Job Example 2]
            // Если мы запустим тот же пример, без функции .join(), то дальнейших код будет выполняться
            // без ожидания завершения корутины (по пути выполнится сразу же )
            btnJobExample2.setOnClickListener {
                val myJob: Job = GlobalScope.launch(Dispatchers.Default) {
                    repeat(5) {
                        Log.d("MyLog", "My coroutines default still is working")
                        delay(1000L)
                    }
                }

                runBlocking {
//                    myJob.join()
                    Log.d("MyLog", "Main Thread")
                }
                Log.d("MyLog", "MyJob is Finishing")
            }

            // ---[Job Example 3]
            // Другой метод объекта Job это .cancel() - он позволяет отменить корутину когда это
            // необходимо. В данном примере работа корутины завершается через 2 секунды
            btnJobExample3.setOnClickListener {
                val myJob: Job = GlobalScope.launch(Dispatchers.Default) {
                    repeat(5) {
                        if (isActive) {
                            Log.d("MyLog", "My coroutines default still is working")
                            delay(1000L)
                        }
                    }
                }

                runBlocking {
                    delay(2000L)
                    myJob.cancel()
                    Log.d("MyLog", "Main Thread")
                }
                Log.d("MyLog", "MyJob is Finishing")
            }
//--------------------------------------------------------------------------------------------------
            // ---[Await Example 1]
            // Получим в корутине через suspend-функции две переменные с одинаковыми значениями
            // и попробуем сделать проверку. Если значение равны, выведем сообщение в лог
            // Зафиксируем время выполнения этих функций при помощи measureTimeMillis{}
            // Так как мы запускаепм две suspend-функции с задержкой в одной корутине, они выполняются
            // последовательно и их время выполнения суммируется. Всего 6 секунд
            btnAwait1.setOnClickListener {
                GlobalScope.launch(Dispatchers.IO) {
                    val time = measureTimeMillis {
                        val res1 = getNumber1()
                        val res2 = getNumber2()

                        if (res1 == res2) {
                            Log.d("MyLog", "$res1 equals $res2")
                        }
                    }
                    Log.d("MyLog", "request time: $time")
                }

            }

            // ---[Await Example 2]
            // Если мы хотим запустить две корутины асинхронно, то их нужно запускать в двух разных
            // корутинах, но тут возникает проблема - мы не можем просто так получить объекты создаваемые
            // в корутинах, так как это отдельная область (моя боль с этой проблемой)
            // вместо этого, можно попробовать предварительно создать инстанции переменных для
            // сравнения и присвоить им значения из suspend-функци в корутинах, после этого сравнить
            // если мы просто так сдалает, то значение будет присвоено в корутине, но у внешеней
            // переменной останется значение null
            btnAwait2.setOnClickListener {
                GlobalScope.launch(Dispatchers.IO) {
                    val time = measureTimeMillis {

                        var res1: Int? = null
                        var res2: Int? = null

                        launch {
                            res1 = getNumber1()
                        }

                        launch {
                            res2 = getNumber2()
                        }

                        if (res1 == res2) {
                            Log.d("MyLog", "$res1 equals $res2")
                        }
                    }
                    Log.d("MyLog", "request time: $time")
                }

            }

            // ---[Await Example 3]
            // на самом деле занчения глобальным переменным присваивается))
            // просто вывод в лог происходит на главном потоке быстрее, чем значение успеваем присвоиться
            // в корутине. Для решения этой проблему, можно воспользоваться методом .join() у объектов Job
            // чтобы дождаться пока в каждой корутине будет присвоино значнение переменным
            btnAwait3.setOnClickListener {
                GlobalScope.launch(Dispatchers.IO) {
                    val time = measureTimeMillis {

                        var res1: Int? = null
                        var res2: Int? = null

                        val job1 = launch {
                            res1 = getNumber1()
                        }

                        val job2 = launch {
                            res2 = getNumber2()
                        }

                        job1.join()
                        job2.join()

                        if (res1 == res2) {
                            Log.d("MyLog", "$res1 equals $res2")
                        }
                    }
                    Log.d("MyLog", "request time: $time")
                }
            }

            // ---[Async Example]
            // Теперь рассмотрим другой смособс Async. Вместо билдера launch{}, вызовим async{} и получим
            // результаты выволения корутин. Сравним их и выведим в лог
            // При использовании билдера async{} мы получаем объект Deferred, а не Job.
            // При сравнении результов выполнения работы корутины вызовим у них метод .await()
            // метод .await() - данный метод возвращает значнеие полученное через suspend-функцию в
            // корутине у билдера async{}
            btnAsync.setOnClickListener {
                GlobalScope.launch(Dispatchers.IO) {
                    val time = measureTimeMillis {

                        val res1 = async {
                            getNumber1()
                        }

                        val res2 = async {
                            getNumber2()
                        }

                        if (res1.await() == res2.await()) {
                            Log.d("MyLog", "${res1.await()} equals ${res1.await()}")
                        }
                    }
                    Log.d("MyLog", "request time: $time")
                }
            }
//--------------------------------------------------------------------------------------------------
            // ---[LifecycleScope 1]
            // Теперь рассмотрим запуска двух корутин в жизненном цикле активити
            // Например, у нас есть какое то выполнение задачи в одной корутине с использованием GlobalScope
            // А в другой корутине мы стартуем новую активити. После старта мы обнаружим, что задача все еще
            // будет выполняться в первой корутине, так как это глобальный скоуп уровня приложения и
            // даже находясь на другой активити задача все еще будет выполняться
            btnLifecycleScope1.setOnClickListener {

                GlobalScope.launch {
                    for (i in 0..100000) {
                        delay(1000L)
                        Log.d("MyLog", "count i: $i")
                    }

                }

                GlobalScope.launch {
                    delay(3000L)
                    startActivity(Intent(this@CoroutinesActivity2, SecondCoroutinesActivity2::class.java))
                    finish()
                }

            }
            // ---[LifecycleScope 1]
            // Чтобы приявызять жиненный цикл корутины к жизненному циклу экрана нужно использовать
            // lifecycleScope из библиотеки androidx.lifecycle:lifecycle-runtime-ktx:2.6.1
            // Теперь при переходе на другой экран, задча которая выполнялась в корутине из lifecycleScope
            // приостанавливается
            btnLifecycleScope2.setOnClickListener {

                lifecycleScope.launch {
                    for (i in 0..100000) {
                        delay(1000L)
                        Log.d("MyLog", "count i: $i")
                    }

                }

                GlobalScope.launch {
                    delay(3000L)
                    startActivity(Intent(this@CoroutinesActivity2, SecondCoroutinesActivity2::class.java))
                    finish()
                }

            }

        }



    }

    suspend fun loadImage(): String {
        return "Done!"
    }

    suspend fun getNumber1(): Int {
        delay(3000L)
        return 10
    }

    suspend fun getNumber2(): Int {
        delay(3000L)
        return 10
    }
}