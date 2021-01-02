package com.aman.ottdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity() {

    val list: ArrayList<OTTProperties> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val progressBar:ProgressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        var string = intent.getStringExtra("string")
        val type = intent.getIntExtra("type",0)
        var sendString =""
        if(type == 1){
            sendString = "https://ott-details.p.rapidapi.com/advancedsearch?genre=$string&min_imdb=6&max_imdb=9.9&sort=highestrated&page=1"
        } else if (type == 2) {
            string = string?.toLowerCase(Locale.ROOT)
            string = string?.replace(" ","+")
            Toast.makeText(this,string,Toast.LENGTH_LONG).show()
           sendString = "https://ott-details.p.rapidapi.com/search?title=$string&page=1"
        }
        search(sendString)
    }

    private fun search(string:String){
        val client = OkHttpClient()

        val request = Request.Builder()
                .url(string)
                .get()
                .addHeader("x-rapidapi-key", "bc7a9875aemsh1e7ce3d15f1379cp12cbbejsn241163ee7138")
                .addHeader("x-rapidapi-host", "ott-details.p.rapidapi.com")
                .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {

                val jsonObject = JSONObject(response.body?.string()?:"{}")
                val jsonArray = jsonObject.getJSONArray("results")
                for (i in 0 until jsonArray.length()){
                    val jObject = jsonArray.getJSONObject(i)

                    val title = jObject.get("title")
                    var imdbrating:Any
                    imdbrating = try {
                        jObject.get("imdbrating")
                    }catch (e : JSONException){
                        "0"
                    }

                    var imageUrl = ""
                    try{
                        val jArray = jObject.getJSONArray("imageurl")
                        imageUrl  = jArray.get(0).toString()
                    }catch (e:Exception){
                        imageUrl = "https://i2.wp.com/lifemadesimplebakes.com/wp-content/uploads/2016/01/Black-Pepper-Parmesan-Popcorn-5.jpg"
                    }

                    val ottProperties = OTTProperties(imageUrl,
                            title.toString(),imdbrating.toString())
                    list.add(ottProperties)
                    runOnUiThread {
                        val progressBar:ProgressBar = findViewById(R.id.progressBar)
                        progressBar.visibility = View.GONE
                        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
                        recyclerView.adapter = RVAdapter(list)
                        recyclerView.hasFixedSize()
                        val linearLayoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
                        recyclerView.layoutManager = linearLayoutManager
                    }
                }
            }
        })

    }


}