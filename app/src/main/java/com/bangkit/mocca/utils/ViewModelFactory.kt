package com.bangkit.mocca.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.mocca.data.model.UserPreference
import com.bangkit.mocca.ui.budget.BudgetViewModel
import com.bangkit.mocca.ui.home.HomeViewModel
import com.bangkit.mocca.ui.login.LoginViewModel
import com.bangkit.mocca.ui.profile.ProfileViewModel
import com.bangkit.mocca.ui.register.RegisterViewModel
import com.bangkit.mocca.ui.report.ReportViewModel
import com.bangkit.mocca.ui.splash.SplashViewModel
import com.bangkit.mocca.ui.transaction.manual.ManualTransactionViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val pref: UserPreference
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            LoginViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            RegisterViewModel() as T
        } else if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            SplashViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(BudgetViewModel::class.java)) {
            BudgetViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(ReportViewModel::class.java)) {
            ReportViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(ManualTransactionViewModel::class.java)) {
            ManualTransactionViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            ProfileViewModel(pref) as T
        } else throw IllegalArgumentException("Unknown ViewModel class")
    }
}