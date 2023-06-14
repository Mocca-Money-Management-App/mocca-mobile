package com.bangkit.mocca.ui.transaction.ocr

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.mocca.data.remote.response.OcrListResponse
import com.bangkit.mocca.data.remote.retrofit.ApiConfig
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.bangkit.mocca.data.Result

class OcrViewModel : ViewModel() {

    private val _scanImageResult = MutableLiveData<Result<OcrListResponse>>()
    val scanImageResult: LiveData<Result<OcrListResponse>> = _scanImageResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun uploadImage(photo: MultipartBody.Part) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().postImage(photo)
        client.enqueue(object : Callback<OcrListResponse> {
            override fun onResponse(
                call: Call<OcrListResponse>,
                response: Response<OcrListResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _scanImageResult.value = Result.Success(responseBody)
                    }
                } else {
                    _scanImageResult.value = Result.Error(response.message())
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<OcrListResponse>, t: Throwable) {
                _isLoading.value = false
                _scanImageResult.value = Result.Error(t.message.toString())
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "OCRViewModel"
    }
}