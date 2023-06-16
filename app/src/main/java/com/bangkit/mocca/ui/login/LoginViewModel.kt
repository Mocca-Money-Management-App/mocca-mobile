package com.bangkit.mocca.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.mocca.data.model.UserModel
import com.bangkit.mocca.data.model.UserPreference
import com.bangkit.mocca.data.online.api.ApiConfig
import com.bangkit.mocca.data.online.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(
    private val pref: UserPreference
) : ViewModel() {
    private val _success = MutableLiveData<String>()
    val success: LiveData<String> = _success

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _success.value = responseBody.message
                        if (responseBody.message == "Login Success") {
                            val loginResult = responseBody.data
                            val idUser = loginResult.userId
                            val username = loginResult.username
                            val email = loginResult.email
                            viewModelScope.launch {
                                pref.saveUser(UserModel(idUser, username, email))
                            }
                        }
                    }
                } else {
                    _success.value = response.body()?.message
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _success.value = t.message
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}