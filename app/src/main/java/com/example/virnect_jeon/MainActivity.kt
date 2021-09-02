package com.example.virnect_jeon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstFragment = BookSearchListFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, firstFragment).commit()

    }
}