package com.bangkit.mocca.ui.budget

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.mocca.data.model.UserModel
import com.bangkit.mocca.data.model.UserPreference
import com.bangkit.mocca.data.remote.retrofit.ApiConfig
import com.bangkit.mocca.data.remote.response.budget.BudgetItem
import com.bangkit.mocca.data.remote.response.budget.BudgetResponse
import com.bangkit.mocca.data.remote.response.category.CategoryResponse
import com.bangkit.mocca.data.remote.response.transaction.EditResponse
import com.bangkit.mocca.data.remote.response.category.ListCategoryItem
import com.bangkit.mocca.ui.transaction.manual.ManualTransactionViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BudgetViewModel(
    private val pref : UserPreference
): ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listCategory = MutableLiveData<List<ListCategoryItem>>()
    val listCategory: LiveData<List<ListCategoryItem>> = _listCategory

    private val _success = MutableLiveData<String>()
    val success: LiveData<String> = _success

    private val _dashboard = MutableLiveData<List<BudgetItem>>()
    val dashboard: LiveData<List<BudgetItem>> = _dashboard

    fun getDashboard(userId: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getTotalBudget(userId)
        client.enqueue(object : Callback<BudgetResponse> {
            override fun onResponse(
                call: Call<BudgetResponse>,
                response: Response<BudgetResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _dashboard.value = responseBody.totalBudget
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BudgetResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getCategory(userId: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getCategory(userId)
        client.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseData = response.body()?.listCategory
                    if (responseData != null) {
                        val listData = ArrayList<ListCategoryItem>()
                        for (data in responseData) {
                            if (data.budget != 0) {
                                listData.add(data)
                            }
                        }
                        _listCategory.value = listData
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun addCategory(userId: Int, categoryId: Int, budget: Float) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().editCategory(userId, categoryId, budget)
        client.enqueue(object : Callback<EditResponse> {
            override fun onResponse(
                call: Call<EditResponse>,
                response: Response<EditResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _success.value = responseBody.message
                    }
                } else {
                    Log.e(ManualTransactionViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EditResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        const val TAG = "BudgetViewModel"
    }
}