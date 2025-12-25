package com.example.pertemuan12.repositori

import com.example.pertemuan12.apiservice.ServiceApiSiswa
import com.example.pertemuan12.modeldata.DataSiswa
import java.io.IOException

interface RepositoryDataSiswa {
    suspend fun getDataSiswa(): List<DataSiswa>
    suspend fun insertDataSiswa(dataSiswa: DataSiswa)
    // Perbaikan: Ubah String jadi Int
    suspend fun updateDataSiswa(id: Int, dataSiswa: DataSiswa)
    suspend fun deleteDataSiswa(id: Int)
    suspend fun getDataSiswaById(id: Int): DataSiswa
}

class NetworkRepositoryDataSiswa(
    private val serviceApiSiswa: ServiceApiSiswa
) : RepositoryDataSiswa {
    override suspend fun getDataSiswa(): List<DataSiswa> = serviceApiSiswa.getDataSiswa()

    override suspend fun insertDataSiswa(dataSiswa: DataSiswa) {
        serviceApiSiswa.insertDataSiswa(dataSiswa)
    }

    override suspend fun updateDataSiswa(id: Int, dataSiswa: DataSiswa) {
        serviceApiSiswa.updateDataSiswa(id, dataSiswa)
    }

    override suspend fun deleteDataSiswa(id: Int) {
        try {
            val response = serviceApiSiswa.deleteDataSiswa(id)
            // Cek jika response code bukan 200 (OK)
            if (!response.isSuccessful) {
                throw IOException("Gagal menghapus data. Kode: ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getDataSiswaById(id: Int): DataSiswa {
        return serviceApiSiswa.getDataSiswaById(id)
    }
}