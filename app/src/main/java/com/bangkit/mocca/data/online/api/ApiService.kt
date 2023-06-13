package com.bangkit.mocca.data.online.api

import com.bangkit.mocca.data.online.response.CategoryResponse
import com.bangkit.mocca.data.online.response.EditResponse
import com.bangkit.mocca.data.online.response.ReportResponse
import com.bangkit.mocca.data.online.response.TotalResponse
import com.bangkit.mocca.data.online.response.TransactionResponse
import com.bangkit.mocca.data.online.response.UploadResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @POST("upload")
    fun upload(
        @Part photo: MultipartBody.Part
    ): Call<UploadResponse>

    @GET("dashboard/income/{idUser}")
    fun getTotalIncome(
        @Path("idUser") idUser: Int
    ): Call<TotalResponse>

    @GET("dashboard/outcome/{idUser}")
    fun getTotalOutcome(
        @Path("idUser") idUser: Int
    ): Call<TotalResponse>

    @GET("getlast10transaction/{idUser}")
    fun getLast10Transactions(
        @Path("idUser") idUser: Int
    ): Call<TransactionResponse>

    @GET("gettrasaction/{idUser}")
    fun getTransactions(
        @Path("idUser") idUser: Int
    ): Call<TransactionResponse>

    @GET("reportmonth/{idUser}")
    fun getReportMonth(
        @Path("idUser") idUser: Int
    ): Call<ReportResponse>

    @FormUrlEncoded
    @POST("insertincome/{idUser}")
    fun insertIncome(
        @Path("idUser") idUser: Int,
        @Field("name_product") name_product: String,
        @Field("categoryid ") categoryid: Int,
        @Field("price") price : Float
    ): Call<EditResponse>

    @FormUrlEncoded
    @POST("insertoutcome/{idUser}")
    fun insertOutcome(
        @Path("idUser") idUser: Int,
        @Field("name_product") name_product: String,
        @Field("categoryid ") categoryid: Int,
        @Field("price") price : Float
    ): Call<EditResponse>

    @DELETE("deleteincome/{idIncome}")
    fun deleteIncome(
        @Path("idIncome") idIncome: Int
    )

    @DELETE("deleteincome/{idOutcome}")
    fun deleteOutcome(
        @Path("idOutcome") idOutcome: Int
    )

    @GET("getcategory/:idUser")
    fun getCategory(
        @Path("idUser") idUser: Int
    ): Call<CategoryResponse>

    @POST("insertcategory/{idUser}")
    fun insertCategory(
        @Path("idUser") idUser: Int,
    ): Call<EditResponse>
}