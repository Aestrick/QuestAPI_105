package com.example.pertemuan12.repositori

import com.example.pertemuan12.apiservice.ServiceApiSiswa
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val repositoryDataSiswa: RepositoryDataSiswa
}

class ContainerDataSiswa : AppContainer {
    private val baseUrl = "http://10.0.2.2/umyTI/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val serviceApiSiswa: ServiceApiSiswa by lazy {
        retrofit.create(ServiceApiSiswa::class.java)
    }

    override val repositoryDataSiswa: RepositoryDataSiswa by lazy {
        NetworkRepositoryDataSiswa(serviceApiSiswa)
    }
}