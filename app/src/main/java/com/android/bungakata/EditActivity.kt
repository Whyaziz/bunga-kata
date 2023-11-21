package com.android.bungakata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.bungakata.databinding.ActivityEditBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class EditActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditBinding
    private lateinit var puisis: PuisiDao
    private lateinit var executorService: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor()
        val db = PuisiDatabase.getDatabase(this@EditActivity)
        puisis = db!!.puisiDao()!!

        val updateId = intent.getIntExtra("UPDATE_ID", 0)
        val judul = intent.getStringExtra("TITLE").toString()
        val writer = intent.getStringExtra("WRITER").toString()
        val content = intent.getStringExtra("CONTENT").toString()

        with(binding){
            txtTitle.setText(judul)
            txtWriter.setText(writer)
            txtPuisi.setText(content)

            btnSimpan.setOnClickListener {
                update(Puisi(
                    id = updateId,
                    title = txtTitle.text.toString(),
                    writer = txtWriter.text.toString(),
                    content = txtPuisi.text.toString()
                ))
                finish()
            }
        }
    }

    private fun update(puisi: Puisi){
        executorService.execute{
            puisis.update(puisi)
        }
    }
}