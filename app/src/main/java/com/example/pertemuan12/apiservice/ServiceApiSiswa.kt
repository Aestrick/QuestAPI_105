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
    @GET("bacaTeman.php")
    suspend fun getDataSiswa(): List<DataSiswa>

    @POST("insertTM.php")
    suspend fun insertDataSiswa(@Body dataSiswa: DataSiswa)

    @GET("baca1Teman.php")
    suspend fun getDataSiswaById(@Query("id") id: Int): DataSiswa

    @PUT("editTM.php")
    suspend fun updateDataSiswa(@Query("id") id: Int, @Body dataSiswa: DataSiswa)

    @GET("deleteTM.php")
    suspend fun deleteDataSiswa(@Query("id") id: Int): Response<Void>
}