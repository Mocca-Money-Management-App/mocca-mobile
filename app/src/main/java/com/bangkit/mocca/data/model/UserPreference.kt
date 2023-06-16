package com.bangkit.mocca.data.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[ID_KEY] ?: 0 ,
                preferences[NAME_KEY] ?:"",
                preferences[EMAIL_KEY] ?:""
            )
        }
    }

    suspend fun saveUser(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = user.idUser
            preferences[NAME_KEY] = user.name
            preferences[EMAIL_KEY] = user.email
        }
    }

    suspend fun deleteUser() {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = 0
            preferences[NAME_KEY] = ""
            preferences[EMAIL_KEY] = ""
        }
    }

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map {preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val ID_KEY = intPreferencesKey("idUser")
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val THEME_KEY = booleanPreferencesKey("theme")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}