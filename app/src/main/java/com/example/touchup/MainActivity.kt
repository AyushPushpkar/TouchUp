package com.example.touchup

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import com.example.touchup.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private  val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ui change
        val manager = PreferenceManager.getDefaultSharedPreferences(this)
        listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key.equals("change_ui")){
                if (manager.getBoolean("change_ui", false))
                    binding.main.setBackgroundResource(R.drawable.layered_back)
                else{
                    binding.main.setBackgroundColor(Color.BLACK)
                }
            }
        }
        manager.registerOnSharedPreferenceChangeListener(listener)

        //shared preferences
        val editor = getSharedPreferences("MY_SETTINGS", MODE_PRIVATE)
//        binding.etemail.setText(editor.getString("Email",null))
//        binding.etpass.setText(editor.getString("Password",null))

        val user = Gson().fromJson(editor.getString("UserData",null),User::class.java)
        binding.etemail.setText(user?.Email)
        binding.etpass.setText(user?.Password)


        binding.btnlogin.setOnClickListener {
            val editor = getSharedPreferences("MY_SETTINGS", MODE_PRIVATE).edit()
//            editor.putString("Email", binding.etemail.text.toString())
//            editor.putString("Password", binding.etpass.text.toString())

            val user  = User(binding.etemail.text.toString() , binding.etpass.text.toString())
            val gson = Gson()
            val Data= gson.toJson(user , User::class.java)
            editor.putString("UserData", Data)
            editor.apply()

            intent = Intent(this , ApplevelActivity::class.java)
            startActivity(intent)
        }

        binding.btnsetting.setOnClickListener {
            intent = Intent(this , SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.imageButton.setOnClickListener {
            intent = Intent(this , ActlevelActivity::class.java)
            startActivity(intent)
        }
        binding.btnmap.setOnClickListener {
            intent = Intent(this , GoogleMapsActivity::class.java)
            startActivity(intent)
        }
        binding.btnlocation.setOnClickListener {
            intent = Intent(this , CurrentLocationActivity::class.java)
            startActivity(intent)
        }
        binding.btnvolley.setOnClickListener {
            intent = Intent(this , ApiActivity::class.java)
            startActivity(intent)
        }
        binding.btnretrofit.setOnClickListener {
            intent = Intent(this , RetrofitActivity::class.java)
            startActivity(intent)
        }

        binding.imageButton2.setOnClickListener {
            val sharedPreferences = getSharedPreferences("MY_SETTINGS", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
//            editor.remove() // to remove one idd

            // Check if the shared preferences are empty
            if (sharedPreferences.all.isEmpty()) {
                Toast.makeText(this, "No data to clear", Toast.LENGTH_SHORT).show()
            } else {
                editor.clear().commit()
                editor.apply()

                // Clear EditText fields after clearing SharedPreferences
                binding.etemail.setText("")
                binding.etpass.setText("")
                Toast.makeText(this, "Data cleared", Toast.LENGTH_SHORT).show()
            }
        }

    }
}