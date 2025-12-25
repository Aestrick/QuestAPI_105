package com.example.pertemuan12.apiservice

import com.example.pertemuan12.modeldata.DataSiswa
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT // Atau POST jika backend tidak support PUT
import retrofit2.http.Query

interface ServiceApiSiswa {
    @GET("bacateman.php")
    suspend fun getDataSiswa(): List<DataSiswa>

    @POST("insertTM.php")
    suspend fun insertDataSiswa(@Body dataSiswa: DataSiswa)

    // Perhatikan: Menggunakan id: Int
    @GET("baca1teman.php")
    suspend fun getDataSiswaById(@Query("id") id: Int): DataSiswa

    // Perhatikan: Menggunakan id: Int
    @POST("editTM.php")
    suspend fun updateDataSiswa(@Query("id") id: Int, @Body dataSiswa: DataSiswa)

    @GET("deleteTM.php")
    suspend fun deleteDataSiswa(@Query("id") id: Int): Response<Void>
}