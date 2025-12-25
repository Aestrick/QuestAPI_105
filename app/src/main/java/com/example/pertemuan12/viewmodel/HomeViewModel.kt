package com.example.pertemuan12.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan12.modeldata.DataSiswa
import com.example.pertemuan12.repositori.RepositoryDataSiswa
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Status UI untuk Halaman Home
sealed interface HomeUiState {
    data class Success(val siswa: List<DataSiswa>) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}

class HomeViewModel(private val repositoryDataSiswa: RepositoryDataSiswa) : ViewModel() {

    // Variabel state yang dipantau oleh UI
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getSiswa()
    }

    // Fungsi untuk mengambil data siswa (Read)
    fun getSiswa() {
        viewModelScope.launch {
            homeUiState = HomeUiState.Loading
            homeUiState = try {
                // Mencoba ambil data dari repository
                HomeUiState.Success(repositoryDataSiswa.getDataSiswa())
            } catch (e: IOException) {
                // Error koneksi (misal: internet mati, server down)
                HomeUiState.Error
            } catch (e: HttpException) {
                // Error respon server (misal: 404 Not Found, 500 Internal Server Error)
                HomeUiState.Error
            } catch (e: Exception) {
                // --- JARING PENGAMAN ANTI FORCE CLOSE ---
                // Menangkap error lain (misal: format JSON salah, data null, dll)
                println("Error Lain: ${e.message}") // Cek Logcat bagian System.out untuk lihat pesan ini
                HomeUiState.Error
            }
        }
    }

    // Fungsi untuk menghapus data siswa (Delete)
    fun deleteSiswa(id: Int) {
        viewModelScope.launch {
            try {
                // Hapus data
                repositoryDataSiswa.deleteDataSiswa(id)
                // Jika berhasil, refresh data (panggil getSiswa lagi) agar list terupdate
                getSiswa()
            } catch (e: IOException) {
                homeUiState = HomeUiState.Error
            } catch (e: HttpException) {
                homeUiState = HomeUiState.Error
            } catch (e: Exception) {
                println("Error Delete: ${e.message}")
                homeUiState = HomeUiState.Error
            }
        }
    }
}