package com.zhialex.demo

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class FilmActivity : AppCompatActivity() {

    private var comment: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film)

        Glide
            .with(this)
            .load(intent.getStringExtra("imgUrl"))
            .into(findViewById(R.id.filmImgView))
        findViewById<TextView>(R.id.filmTitleView).text = intent.getStringExtra("title")
        findViewById<TextView>(R.id.filmDescriptionView).text = intent.getStringExtra("description")
        findViewById<ImageButton>(R.id.shareButton).setOnClickListener {

            val sendMessageIntent: Intent = Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, "Watch Movie \"${intent.getStringExtra("title")}\"")

            startActivity(Intent.createChooser(sendMessageIntent, null))
        }

        findViewById<ImageButton>(R.id.sendButton).setOnClickListener {
            val editText = findViewById<EditText>(R.id.commentETView)
            if (editText.text.isNotEmpty()) {
                comment = editText.text.toString()
                editText.setText("")
                Toast.makeText(this, "Sent", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Field is empty", Toast.LENGTH_SHORT).show()
            }

            val result = Intent()
                .putExtra("comment", comment)
                .putExtra("isLiked", findViewById<CheckBox>(R.id.likeCheckbox).isChecked)


            setResult(RESULT_OK, result)
        }

        findViewById<CheckBox>(R.id.likeCheckbox).setOnClickListener{
            val result = Intent()
                .putExtra("isLiked", findViewById<CheckBox>(R.id.likeCheckbox).isChecked)
                .putExtra("comment", comment)
            setResult(RESULT_OK, result)
        }
    }
}