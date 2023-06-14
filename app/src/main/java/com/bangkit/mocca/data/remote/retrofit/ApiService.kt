package com.bangkit.mocca.data.remote.retrofit

import com.bangkit.mocca.data.remote.response.OcrListResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("upload")
    fun postImage(
        @Part photo: MultipartBody.Part
    ) : Call<OcrListResponse>
}