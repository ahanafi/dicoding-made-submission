package com.ahanafi.id.myfavoritemovieapp.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.ahanafi.id.myfavoritemovieapp.R

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val changeLanguage = findPreference<Preference>("change_language")
        changeLanguage?.intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when(key) {
            "daily_reminder" -> {
                when(sharedPreferences?.getBoolean(key, false)) {
                    true -> Toast.makeText(activity, "TRUE DAILY", Toast.LENGTH_SHORT).show()
                    false -> Toast.makeText(activity, "FALSE DAILY", Toast.LENGTH_SHORT).show()
                }
            }
            "release_reminder" -> {
                when(sharedPreferences?.getBoolean(key, false)) {
                    true -> Toast.makeText(activity, "TRUE RELEASE", Toast.LENGTH_SHORT).show()
                    false -> Toast.makeText(activity, "FALSE RELEASE", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}