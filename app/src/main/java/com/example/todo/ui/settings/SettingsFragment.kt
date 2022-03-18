package com.example.todo.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.todo.R

class SettingsFragment : PreferenceFragmentCompat() {



    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var mlist=preferenceManager.findPreference<EditTextPreference>("name")
        mlist!!.setOnBindEditTextListener {

        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}