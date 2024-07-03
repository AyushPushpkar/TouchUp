package com.example.touchup

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.touchup.databinding.ActivityApplevelBinding
import com.google.gson.Gson

class ApplevelActivity : AppCompatActivity() {

    private val binding : ActivityApplevelBinding by lazy {
        ActivityApplevelBinding.inflate(layoutInflater)
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

        val editor = getSharedPreferences("MY_SETTINGS", MODE_PRIVATE)
        val user = Gson().fromJson(editor.getString("UserData",null),User::class.java)


        binding.textView.text = "Email : ${user?.Email}" +
                "\n\nPassword : ${user?.Password}"

//        binding.textView.text = buildString {
//            append("Email : ${editor.getString("Email", null)}")
//            append(" , Password : ${editor.getString("password", null)}")
//        }

//        ("Email : ${editor.getString("Email",null)}" +
//                " , Password : ${editor.getString("password",null)}").also { binding.textView.text = it }

    }
}