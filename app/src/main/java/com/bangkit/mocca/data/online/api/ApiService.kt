package com.bangkit.mocca.data.online.api

import com.bangkit.mocca.data.online.response.BudgetResponse
import com.bangkit.mocca.data.online.response.CategoryResponse
import com.bangkit.mocca.data.online.response.EditResponse
import com.bangkit.mocca.data.online.response.LoginResponse
import com.bangkit.mocca.data.online.response.RegisterResponse
import com.bangkit.mocca.data.online.response.ReportResponse
import com.bangkit.mocca.data.online.response.TransactionResponse
import com.bangkit.mocca.data.online.response.UploadResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @POST("upload")
    fun upload(
        @Part photo: MultipartBody.Part
    ): Call<UploadResponse>

    @GET("totalbudget/{idUser}")
    fun getTotalBudget(
        @Path("idUser") idUser: Int
    ): Call<BudgetResponse>

    @GET("getlast10transaction/{idUser}")
    fun getLast10Transactions(
        @Path("idUser") idUser: Int
    ): Call<TransactionResponse>

    @GET("gettrasaction/{idUser}")
    fun getTransactions(
        @Path("idUser") idUser: Int
    ): Call<TransactionResponse>

    @GET("reportmonth/{idUser}")
    fun getReport(
        @Path("idUser") idUser: Int
    ): Call<ReportResponse>

    @FormUrlEncoded
    @POST("insertincome/{idUser}")
    fun insertIncome(
        @Path("idUser") idUser: Int,
        @Field("name_product") name_product: String,
        @Field("price") price : Float
    ): Call<EditResponse>

    @FormUrlEncoded
    @POST("insertoutcome/{idUser}")
    fun insertOutcome(
        @Path("idUser") idUser: Int,
        @Field("name_product") name_product: String,
        @Field("categoryId") categoryId: Int,
        @Field("price") price : Float
    ): Call<EditResponse>

    @DELETE("deleteincome/{idIncome}")
    fun deleteIncome(
        @Path("idIncome") idIncome: Int
    )

    @DELETE("deleteoutcome/{idOutcome}")
    fun deleteOutcome(
        @Path("idOutcome") idOutcome: Int
    )

    @GET("getcategory/{idUser}")
    fun getCategory(
        @Path("idUser") idUser: Int
    ): Call<CategoryResponse>

    @FormUrlEncoded
    @PUT("editcategory/{idUser}/{categoryId}")
    fun editCategory(
        @Path("idUser") idUser: Int,
        @Path("categoryId") categoryId: Int,
        @Field("budget") budget: Float,
    ): Call<EditResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("passwordUser") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("phoneNumber") phoneNumber : String,
        @Field("passwordUser") passwordUser: String
    ): Call<RegisterResponse>
}