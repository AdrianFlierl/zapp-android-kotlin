package com.example.othregensburg.zapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        textView = findViewById(R.id.tv_second_activity)

        val text = intent.extras?.get(MainActivity.PUT_EXTRA_STRING) as CharSequence?

        textView.text = text
    }
}
