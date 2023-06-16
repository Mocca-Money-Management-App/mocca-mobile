package com.bangkit.mocca.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.mocca.data.model.UserModel
import com.bangkit.mocca.data.model.UserPreference
import kotlinx.coroutines.launch

class SplashViewModel(
    private val pref : UserPreference
): ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun getTheme(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
}