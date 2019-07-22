package com.trunghoang.generalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.trunghoang.generalapp.ui.home.HomeMovieFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, HomeMovieFragment.newInstance(), null)
            .commit()
    }
}
