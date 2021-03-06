package com.ahanafi.id.myfavoritemovieapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ahanafi.id.myfavoritemovieapp.R
import com.ahanafi.id.myfavoritemovieapp.fragments.SettingsFragment

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}