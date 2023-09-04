package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines5ViewModel

import android.util.Log
import kotlinx.coroutines.*

class Repository {

    suspend fun returnSomeItems(result: (human: List<Human>, animal: List<Animal>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val humansResponse = async {
                Log.d("MyLog", "humans")
                getHumans()
            }
            val animalsResponse = async {
                Log.d("MyLog", "animals")
                getAnimals()
            }
            val humans = humansResponse.await()
            val animals = animalsResponse.await()

            result(humans, animals)

        }


    }

    private fun async(function: () -> List<Human>): Any {
        TODO("Not yet implemented")
    }

    suspend fun getHumans(): List<Human> {

        val list = mutableListOf<Human>()
        for (i in 1..10) {

            list.add(
                Human(
                    "Putin",
                    "Russian"
                )
            )
        }
        delay(1000)
        Log.d("MyLog ", "get humans")
        return list
    }

    suspend fun getAnimals(): List<Animal> {

        val list = mutableListOf<Animal>()
        for (i in 1..10) {

            list.add(
                Animal(
                    "Bear",
                    "Vodka"
                )
            )
        }
        delay(3000)
        Log.d("MyLog ", "get animals")
        return list
    }
}