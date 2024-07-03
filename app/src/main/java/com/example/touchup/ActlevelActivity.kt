package com.example.touchup

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.touchup.databinding.ActivityActlevelBinding

class ActlevelActivity : AppCompatActivity() {

    private val binding : ActivityActlevelBinding by lazy {
        ActivityActlevelBinding.inflate(layoutInflater)
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
        val editor = getPreferences(MODE_PRIVATE)
        binding.editTextText.setText(editor.getString("Name",null))
        binding.checkBox.isChecked = editor.getBoolean("Checked", false)

        binding.button.setOnClickListener {
            val editor = getPreferences(MODE_PRIVATE).edit()
            editor.putString("Name", binding.editTextText.text.toString())
            editor.putBoolean("Checked", binding.checkBox.isChecked)
            editor.apply()
        }
    }
}