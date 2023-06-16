package com.bangkit.mocca.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.mocca.data.model.UserModel
import com.bangkit.mocca.data.model.UserPreference

class SplashViewModel(
    private val pref : UserPreference
): ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
}