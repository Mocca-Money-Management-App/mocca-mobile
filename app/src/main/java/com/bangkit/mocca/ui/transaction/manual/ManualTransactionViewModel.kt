package com.bangkit.mocca.ui.transaction.manual

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.mocca.data.model.UserModel
import com.bangkit.mocca.data.model.UserPreference
import com.bangkit.mocca.data.online.api.ApiConfig
import com.bangkit.mocca.data.online.response.EditResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManualTransactionViewModel(
    private val pref : UserPreference
): ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _success = MutableLiveData<String>()
    val success: LiveData<String> = _success

    fun addIncome(idUser: Int, product: String, price: Float) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().insertIncome(idUser, product, price)
        client.enqueue(object : Callback<EditResponse> {
            override fun onResponse(
                call: Call<EditResponse>,
                response: Response<EditResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _success.value = responseBody.message
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EditResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


    fun addOutcome(idUser: Int, product: String, category: Int, price: Float) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().insertOutcome(idUser, product, category, price)
        client.enqueue(object : Callback<EditResponse> {
            override fun onResponse(
                call: Call<EditResponse>,
                response: Response<EditResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _success.value = responseBody.message
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EditResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        const val TAG = "ManualAddViewModel"
    }
}