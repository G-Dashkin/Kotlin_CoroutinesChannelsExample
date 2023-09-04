package com.example.kotlin_coroutineschannelsexample.LooperHandlerMessageQueue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.util.Log
import com.example.kotlin_coroutineschannelsexample.LooperHandlerMessageQueue.HandlerMessage.Companion.TASK_A
import com.example.kotlin_coroutineschannelsexample.LooperHandlerMessageQueue.HandlerMessage.Companion.TASK_B
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityLgmqactivityBinding

class LGMQActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLgmqactivityBinding
//    private lateinit var threadHandler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLgmqactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val thread = SimpleThread()
        val looperThread = LooperThread()


        binding.apply {

            // Сначала запускаем поток при помощи метода .start() в потоке перебираются значения
            // через метод for() нажимаем кнопку Start два раза и приложение упадет
            // [START THREAD]
            buttonStartThread.setOnClickListener {
                thread.start()
            }

            // Теперь запустим поток для нашего хэндлера. Вызовим у него так же метод .start()
            // т.е. в данном случае мы содаем хэндлер в отдельном потоке
            // [START HANDLER]
            buttonStartHandler.setOnClickListener {
                looperThread.start()
            }

            // И после запуска хэндлена, вызовим у него метод post() и сделаем туже самую итерацию чисел
            // как при создании потока. Метод.post() отпраляет объекты Runnable хэндлер, который
            // вызван у нас в отдельном потоке
            // Теперь мы можем запустить эту итерацию несколько раз, так как
            // она запускается в отдельном потоке у хэндлера
            // Главный поток при этом поток блокируется и можно нажимать на другие кнопки
            // [TASK A]
            buttonTaskA.setOnClickListener {
                looperThread.handler.post {
                    run {
                        for (i in 0..5) {
                            Log.d("MyLog", "run: $i")
                            SystemClock.sleep(1000)
                        }
                    }
                }
            }


            // Нажав кнопку [TASK B] мы запускаем отдельный хэнделер в конструктор которого мы передали
            // лупер, таким образом мы можем управлять этим хэндлером. У него сразу вызывается метод .post()
            // [TASK B]
            buttonTaskB.setOnClickListener {
                // Для управления хэндоером и возможности его остановки создадим отдельных экземпляр
                // хэндлера в конструктор которого мы передадим лупер
                val threadHandler = Handler(looperThread.looper)
                threadHandler.post {
                    run {
                        for (i in 0..5) {
                            Log.d("MyLog", "run: $i")
                            SystemClock.sleep(1000)
                        }
                    }
                }
            }

            // При нажатии на кнопку Stop, мы вызываем метод .quit() у лупера, в новом хэндлере, тем
            // самым останавливаем его работу, но очередь сообщений не прекратится.
            // хэндлер остановит свою работу, только после звершения очереди
            // [STOP]
            buttonStop.setOnClickListener {
                looperThread.looper.quit()
            }
            // И если после этого мы попробуем снова отправить очередь сообщений в хэндлер, нажав кнопку
            // [TASK B], то у нас это не получится, так как этот хэндлер будет мертв



            // Сообщения хэндлер, можно отправлять при помощи  .sendMessage(msg)
            // нажав [TASK C] и [TASK B].
            buttonTaskC.setOnClickListener {
                val msg = Message.obtain()
                msg.what = TASK_A
                looperThread.handler.sendMessage(msg)
            }
            buttonTaskD.setOnClickListener {
                val msg = Message.obtain()
                msg.what = TASK_B
                looperThread.handler.sendMessage(msg)
            }
            // Для управления .sendMessage() нужно раскомментировать отдельных хэндлер для этого


        }
    }
}