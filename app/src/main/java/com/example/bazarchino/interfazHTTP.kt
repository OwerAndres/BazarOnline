package com.example.bazarchino

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("Android.php") // Aquí pones la ruta relativa en tu servidor
    fun sendFormData(@Body formData: FormData): Call<ApiResponse>
}
