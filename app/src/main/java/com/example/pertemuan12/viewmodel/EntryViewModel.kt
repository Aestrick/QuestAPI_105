package com.example.pertemuan12.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan12.modeldata.DataSiswa
import com.example.pertemuan12.repositori.RepositoryDataSiswa
import kotlinx.coroutines.launch

class EntryViewModel(private val repositoryDataSiswa: RepositoryDataSiswa) : ViewModel() {
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }

    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    fun insertSiswa() {
        viewModelScope.launch {
            try {
                val siswa = uiStateSiswa.detailSiswa.toDataSiswa()
                repositoryDataSiswa.insertDataSiswa(siswa)
                uiStateSiswa = UIStateSiswa() // Reset form
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class UIStateSiswa(
    val detailSiswa: DetailSiswa = DetailSiswa(),
    val isEntryValid: Boolean = false
)

data class DetailSiswa(
    val nama: String = "",
    val alamat: String = "",
    val telpon: String = ""
)

// Extension functions
fun DetailSiswa.toDataSiswa(): DataSiswa = DataSiswa(
    nama = nama,
    alamat = alamat,
    telpon = telpon
)

fun DataSiswa.toDetailSiswa(): DetailSiswa = DetailSiswa(
    nama = nama,
    alamat = alamat,
    telpon = telpon
)