package com.example.touchup

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

class Coroutine2Activity : AppCompatActivity() {

    val TAG = "Coroutine2Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_coroutine2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        runBlocking {  // block the main thread (only)
            delay(1000L)    // if want to call suspend func in main thread
            launch(Dispatchers.IO) {
                //won't be blocked
            }
        }
        Thread.sleep(1000L)  // block the main thread

        //coroutine returns a job
        val job = GlobalScope.launch(Dispatchers.Default) {
            repeat(5){
                Log.d(TAG , "Coroutine is still working ...")
                delay(1000L)
            }
        }
        runBlocking {
            job.join()    // block main thread  until coroutine is finished
            Log.d(TAG , "Main thread is continuing ...")
        }

        //cancellation is co-operative
        //enough time is req to tell coroutine to it has been cancelled

        val job2 = GlobalScope.launch(Dispatchers.Default) {
            Log.d(TAG , "Starting long running calculation ...")
            for(i in 30..40) {                                      // so busy with calc that it won't check for cancellation
                if (isActive) {      // check for  cancellation
                    Log.d(TAG, "Result for i = $i : ${fib(i)}")
                }
            }
            Log.d(TAG , "Ending long running calculation ...")
        }
        runBlocking {
            delay(2000L)
            job2.cancel()
            Log.d(TAG , "Canceled job !")
        }

        val job3 = GlobalScope.launch(Dispatchers.Default) {
            Log.d(TAG, "Starting long running calculation ...")
            withTimeout(4000L){   // delay and then automatically cancels job
                for (i in 30..40) {
                    if (isActive) {
                        Log.d(TAG, "Result for i = $i : ${fib(i)}")
                    }
                }
            }
            Log.d(TAG, "Ending long running calculation ...")
        }
    }

    fun fib(n : Int) : Long{
        return if (n == 0) 0
        else if (n == 1) 1
        else fib(n-1) + fib(n-2)
    }
}