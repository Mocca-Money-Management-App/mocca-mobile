package com.bangkit.mocca.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.mocca.data.model.UserModel
import com.bangkit.mocca.data.model.UserPreference
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val pref: UserPreference
) : ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.deleteUser()
        }
    }
}