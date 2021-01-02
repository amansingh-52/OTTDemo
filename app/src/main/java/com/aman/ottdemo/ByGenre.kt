package com.aman.ottdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import okhttp3.internal.immutableListOf
import org.json.JSONObject
import java.io.IOException

class ByGenre : AppCompatActivity() , GenreAdaptor.OnItemClickListener {

    val list: ArrayList<GenreDataClass> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_by_genre)
        val client = OkHttpClient()

        val goTextView = findViewById<TextView>(R.id.goTextView)
        goTextView.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            val searchEditText = findViewById<EditText>(R.id.searchEditText)
            intent.putExtra("string",searchEditText.text.toString())
            intent.putExtra("type",2)
            startActivity(intent)
        }

        val request = Request.Builder()
            .url("https://ott-details.p.rapidapi.com/getParams?param=genre")
            .get()
            .addHeader("x-rapidapi-key", "bc7a9875aemsh1e7ce3d15f1379cp12cbbejsn241163ee7138")
            .addHeader("x-rapidapi-host", "ott-details.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                var string = response.body?.string()
                string = "{\"info\":$string}"
                val jsonObject = JSONObject(string)
                val jsonArray = jsonObject.getJSONArray("info")
                for (i in 0 until jsonArray.length()){
                    list.add(GenreDataClass(jsonArray[i].toString()))
                }
                runOnUiThread{
                    val recyclerView: RecyclerView = findViewById(R.id.genreRV)
                    recyclerView.adapter = GenreAdaptor(list,this@ByGenre)
                    recyclerView.hasFixedSize()
                    recyclerView.layoutManager = GridLayoutManager(this@ByGenre,6,GridLayoutManager.HORIZONTAL,false)
                }
            }

        })
    }

    override fun onItemCliCk(string: String) {
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("string",string)
        intent.putExtra("type",1)
        startActivity(intent)
    }
}