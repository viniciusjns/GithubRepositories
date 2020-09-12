package com.example.githubrepositories.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubrepositories.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actMain_withPaging_bt.setOnClickListener {
            startActivity(Intent(this, WithPagingLibraryActivity::class.java))
        }

        actMain_withoutPaging_bt.setOnClickListener {
            startActivity(Intent(this, WithoutPagingLibraryActivity::class.java))
        }
    }
}