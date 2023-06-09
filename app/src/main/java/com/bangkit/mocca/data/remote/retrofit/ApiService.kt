package com.bangkit.mocca.data.remote.retrofit

import com.bangkit.mocca.data.remote.response.budget.BudgetResponse
import com.bangkit.mocca.data.remote.response.category.CategoryResponse
import com.bangkit.mocca.data.remote.response.transaction.EditResponse
import com.bangkit.mocca.data.remote.response.auth.LoginResponse
import com.bangkit.mocca.data.remote.response.auth.RegisterResponse
import com.bangkit.mocca.data.remote.response.report.ReportResponse
import com.bangkit.mocca.data.remote.response.transaction.TransactionResponse
import com.bangkit.mocca.data.remote.response.ocr.InsertTransactionListResponse
import com.bangkit.mocca.data.remote.response.ocr.OcrListResponse
import com.bangkit.mocca.data.remote.response.ocr.OcrResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

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

    // Automatic Input
    @Multipart
    @POST("upload")
    fun postImage(
        @Part photo: MultipartBody.Part
    ) : Call<OcrListResponse>

    @POST("insertoutcome/{idUser}")
    fun postListTransaction(
        @Path("idUser") userId: Int,
        @Body transactions: List<OcrResponse>
    ) : Call<InsertTransactionListResponse>

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