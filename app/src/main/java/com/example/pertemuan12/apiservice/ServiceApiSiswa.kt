package com.example.pertemuan12.apiservice

import com.example.pertemuan12.modeldata.DataSiswa
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ServiceApiSiswa {
    // Membaca semua data (Sesuai nama file bacaTeman.php)
    @GET("bacaTeman.php")
    suspend fun getDataSiswa(): List<DataSiswa>

    // Menambah data (Sesuai nama file insertTM.php)
    @POST("insertTM.php")
    suspend fun insertDataSiswa(@Body dataSiswa: DataSiswa)

    // Membaca 1 data berdasarkan ID (Sesuai nama file baca1Teman.php)
    @GET("baca1Teman.php")
    suspend fun getDataSiswaById(@Query("id") id: Int): DataSiswa

    // Mengedit data (Sesuai nama file editTM.php)
    @PUT("editTM.php")
    suspend fun updateDataSiswa(@Query("id") id: Int, @Body dataSiswa: DataSiswa)

    // Menghapus data (Sesuai nama file deleteTM.php)
    // Menggunakan @GET agar lebih aman untuk script PHP sederhana yang pakai $_GET
    @GET("deleteTM.php")
    suspend fun deleteDataSiswa(@Query("id") id: Int): Response<Void>
}