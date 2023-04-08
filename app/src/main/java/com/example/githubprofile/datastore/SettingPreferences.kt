package com.example.githubprofile.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.githubprofile.config.datastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences {

    private val THEME = booleanPreferencesKey("theme")

    fun getThemeSetting(context: Context): Flow<Boolean> {
        return context.datastore.data.map { preferences ->
            preferences[THEME] ?: false
        }
    }

    suspend fun setTheme(context: Context, theme: Boolean){
        context.datastore.edit { preference ->
            preference[THEME] = theme
        }
    }

    companion object {
        @Volatile
        var INSTANCE: SettingPreferences? = null

        fun getInstance(): SettingPreferences {
            return INSTANCE ?: synchronized(this){
                val instance = SettingPreferences()
                INSTANCE = instance
                instance
            }
        }
    }
}