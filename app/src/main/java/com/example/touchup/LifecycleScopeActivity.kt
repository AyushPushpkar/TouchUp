package com.example.touchup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.touchup.databinding.ActivityLifecycleScopeBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LifecycleScopeActivity : AppCompatActivity() {

    val TAG = "LifecycleScopeActivity"

    private val binding : ActivityLifecycleScopeBinding by lazy {
        ActivityLifecycleScopeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.button3.setOnClickListener {

            lifecycleScope.launch {    // live as long as activity is alive
                while (true){
                    delay(1000L)
                    Log.d(TAG , "Still running ...")
                }
            }
            GlobalScope.launch {
                delay(5000L)
                Intent(this@LifecycleScopeActivity , AsyncAwaitActivity::class.java).also {
                    startActivity(it)
                    finish() // quit current activity
                }
            }
        }

    }
}