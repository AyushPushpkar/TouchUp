package com.example.touchup

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class AsyncAwaitActivity : AppCompatActivity() {

    val TAG = "AsyncAwaitActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_async_await)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        GlobalScope.launch(Dispatchers.IO) {

            val time = measureTimeMillis {         //to know time code needs to be executed
                val answer1 = doNetworkCall()
                val answer2 = doNetworkCall2()
                Log.d(TAG , "Answer1 is $answer1")
                Log.d(TAG , "Answer2 is $answer2")
            }
            Log.d(TAG , "Requests took $time ms.")
        }

        GlobalScope.launch(Dispatchers.IO) {

            val time = measureTimeMillis {
                var answer1 = async {  // whenever we want to do somethings asynchronously and get a result
                    doNetworkCall()
                }
                var answer2 = async { // deferred<return value>
                    doNetworkCall()
                }
                Log.d(TAG , "Answer1 is ${answer1.await()}")  // block current coroutine until answer is available
                Log.d(TAG , "Answer2 is ${answer2.await()}")
            }
            Log.d(TAG , "Requests took $time ms.")   // now only 3 sec
        }
    }

    suspend fun doNetworkCall() : String{
        delay(3000L)
        return "Answer 1"
    }
    suspend fun doNetworkCall2() : String{
        delay(3000L)
        return "Answer 2"
    }
}