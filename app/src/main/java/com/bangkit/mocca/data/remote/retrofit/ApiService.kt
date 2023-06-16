package com.bangkit.mocca.data.remote.retrofit

import com.bangkit.mocca.data.remote.response.ocr.InsertTransactionListResponse
import com.bangkit.mocca.data.remote.response.ocr.OcrListResponse
import com.bangkit.mocca.data.remote.response.ocr.OcrResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

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
}