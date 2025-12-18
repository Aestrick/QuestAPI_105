package com.example.pertemuan12.repositori

import com.example.pertemuan12.apiservice.ServiceApiSiswa
import com.example.pertemuan12.modeldata.DataSiswa
import java.io.IOException

interface RepositoryDataSiswa {
    suspend fun getDataSiswa(): List<DataSiswa>
    suspend fun insertDataSiswa(dataSiswa: DataSiswa)
    suspend fun updateDataSiswa(nama: String, dataSiswa: DataSiswa)
    suspend fun deleteDataSiswa(nama: String)
    suspend fun getDataSiswaById(nama: String): DataSiswa
}

class NetworkRepositoryDataSiswa(
    private val serviceApiSiswa: ServiceApiSiswa
) : RepositoryDataSiswa {
    override suspend fun getDataSiswa(): List<DataSiswa> = serviceApiSiswa.getDataSiswa()

    override suspend fun insertDataSiswa(dataSiswa: DataSiswa) {
        serviceApiSiswa.insertDataSiswa(dataSiswa)
    }

    override suspend fun updateDataSiswa(nama: String, dataSiswa: DataSiswa) {
        serviceApiSiswa.updateDataSiswa(nama, dataSiswa)
    }

    override suspend fun deleteDataSiswa(nama: String) {
        val response = serviceApiSiswa.deleteDataSiswa(nama)
        if (!response.isSuccessful) {
            throw IOException("Gagal menghapus data. Kode: ${response.code()}")
        }
    }

    override suspend fun getDataSiswaById(nama: String): DataSiswa {
        return serviceApiSiswa.getDataSiswaById(nama)
    }
}


//ya