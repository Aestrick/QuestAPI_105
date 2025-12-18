package com.example.pertemuan12.apiservice

import com.example.pertemuan12.modeldata.DataSiswa
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ServiceApiSiswa {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("bacaTeman.php")
    suspend fun getDataSiswa(): List<DataSiswa>

    @GET("baca1Teman.php")
    suspend fun getDataSiswaById(@Query("nama") nama: String): DataSiswa

    @POST("insertTM.php")
    suspend fun insertDataSiswa(@Body dataSiswa: DataSiswa): Response<Void>

    @PUT("editTM.php")
    suspend fun updateDataSiswa(@Query("nama") nama: String, @Body dataSiswa: DataSiswa): Response<Void>

    @DELETE("deleteTM.php")
    suspend fun deleteDataSiswa(@Query("nama") nama: String): Response<Void>
}