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
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext

class CoroutineScopeActivity : AppCompatActivity() {

    val TAG = "CoroutineScopeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_coroutine_scope)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //executed within thread
        //are suspendable  (can be paused)
        //can change context (thread)

        GlobalScope.launch {    // live as long as app
            delay(1000L)   // can be called only within a coroutine / another suspend func
            Log.d (TAG ,"Coroutine says hello from thread ${Thread.currentThread().name}")  // coroutines also cancelled when main thread finishes
            val networkCallAnswer = doNetworkCall()
            val networkCallAnswer2 = doNetworkCall2()
            Log.d(TAG , networkCallAnswer) //delay will add up
            Log.d(TAG , networkCallAnswer2)

        }
        Log.d (TAG ,"Hello from thread ${Thread.currentThread().name}")

        GlobalScope.launch(Dispatchers.IO) {
            //for beta operations (networking , writing to database , reading & writing to files )

            val answer = doNetworkCall()
            withContext(Dispatchers.Main){
                //for ui operations within coroutines (as ui can be update through main thread)
            }
        }
        GlobalScope.launch(Dispatchers.Default) {
            //complex and long running operations
        }
        GlobalScope.launch(Dispatchers.Unconfined) {
            //not confined to a thread
        }
        GlobalScope.launch(newSingleThreadContext("myThread")) {
            //new thread
        }
    }

    suspend fun doNetworkCall() : String{
        delay(3000L)
        return "This is the answer"
    }
    suspend fun doNetworkCall2() : String{
        delay(3000L)
        return "This is the answer"
    }
}