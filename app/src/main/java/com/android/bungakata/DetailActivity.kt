package com.android.bungakata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.bungakata.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val updateId = intent.getIntExtra("UPDATE_ID", 0)
        val judul = intent.getStringExtra("TITLE").toString()
        val writer = intent.getStringExtra("WRITER").toString()
        val content = intent.getStringExtra("CONTENT").toString()

        with(binding){
            title.text = judul
            penulis.text = writer
            puisi.text = content
        }

    }
}