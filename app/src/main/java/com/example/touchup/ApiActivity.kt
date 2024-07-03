package com.example.touchup

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.touchup.databinding.ActivityApiBinding
import com.google.gson.GsonBuilder

class ApiActivity : AppCompatActivity() {

    private val binding : ActivityApiBinding by  lazy {
        ActivityApiBinding.inflate(layoutInflater)
    }
    val apiUrl = "https://api.github.com/users"
    private var userInfoItem = ArrayList<userInfoItem>()
    val userInfo = userInfo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = binding.recview

        //volley fetch Api
        val stringRequest = StringRequest(apiUrl, {

            //convert json to list
            val gsonBuilder = GsonBuilder()
            val gson = gsonBuilder.create()
            userInfoItem = gson.fromJson(it, Array<userInfoItem>::class.java).toList() as ArrayList<userInfoItem>

            //add user info to list
            userInfoItem.forEach {
                userInfo.add(it)
            }

            val rvAdapter = RvAdapter(this,userInfo)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = rvAdapter

//            Toast.makeText(this, userInfo.toString(), Toast.LENGTH_LONG).show()

        }, {
            Toast.makeText(this, "Somehting went wrong ${it.toString()}", Toast.LENGTH_SHORT).show()
        })

        val volleyQueue = Volley.newRequestQueue(this)
        volleyQueue.add(stringRequest)

    }
}