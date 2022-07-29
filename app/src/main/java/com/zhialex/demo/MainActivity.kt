package com.zhialex.demo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zhialex.demo.models.Film
import java.io.IOException

class MainActivity : AppCompatActivity(), FilmAdapter.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.rcView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FilmAdapter(generateFakeValues(), this)
    }

    private fun generateFakeValues(): List<Film> {
        val jsonFileString = getJsonDataFromAsset(applicationContext, "films_data.json")
        val listPersonType = object : TypeToken<List<Film>>() {}.type
        return Gson().fromJson(jsonFileString, listPersonType)
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }

    override fun onItemClick(filmHolder: FilmAdapter.FilmHolder, film: Film) {

        filmHolder.itemView.setBackgroundColor(
            ContextCompat.getColor(
                filmHolder.context,
                androidx.appcompat.R.color.material_grey_100
            )
        )

        launcher.launch(
            Intent(this, FilmActivity::class.java)
                .putExtra("imgUrl", film.imgUrl)
                .putExtra("title", film.title)
                .putExtra("description", film.description)
        )
    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        result ?: return@registerForActivityResult
        if (result.resultCode == Activity.RESULT_OK){
            Log.d("TAG", result.data?.getStringExtra("comment").toString())
            Log.d("TAG", "${
                result.data?.getBooleanExtra("isLiked", false)
            }")
        }
    }
}