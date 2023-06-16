package com.bangkit.mocca.ui.report

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.mocca.data.model.UserModel
import com.bangkit.mocca.data.model.UserPreference
import com.bangkit.mocca.data.remote.retrofit.ApiConfig
import com.bangkit.mocca.data.remote.response.transaction.ListTransactionItem
import com.bangkit.mocca.data.remote.response.report.ReportItem
import com.bangkit.mocca.data.remote.response.report.ReportResponse
import com.bangkit.mocca.data.remote.response.transaction.TransactionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportViewModel(
    private val pref : UserPreference
): ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listTransaction = MutableLiveData<List<ListTransactionItem>>()
    val listTransaction: LiveData<List<ListTransactionItem>> = _listTransaction

    private val _dashboard = MutableLiveData<List<ReportItem>>()
    val dashboard: LiveData<List<ReportItem>> = _dashboard

    fun getDashboard(userId: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getReport(userId)
        client.enqueue(object : Callback<ReportResponse> {
            override fun onResponse(
                call: Call<ReportResponse>,
                response: Response<ReportResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _dashboard.value = responseBody.report
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ReportResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getTransaction(userId: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getTransactions(userId)
        client.enqueue(object : Callback<TransactionResponse> {
            override fun onResponse(
                call: Call<TransactionResponse>,
                response: Response<TransactionResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listTransaction.value = response.body()?.transaction
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "ReportViewModel"
    }
}