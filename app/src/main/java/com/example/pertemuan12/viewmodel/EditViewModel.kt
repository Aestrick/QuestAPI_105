package com.example.pertemuan12.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan12.repositori.RepositoryDataSiswa
import com.example.pertemuan12.uicontroller.route.DestinasiEdit
import kotlinx.coroutines.launch

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {

    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    private val _nama: String = checkNotNull(savedStateHandle[DestinasiEdit.nama])

    init {
        viewModelScope.launch {
            try {
                val siswa = repositoryDataSiswa.getDataSiswaById(_nama)
                uiStateSiswa = UIStateSiswa(detailSiswa = siswa.toDetailSiswa(), isEntryValid = true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }

    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    fun updateSiswa() {
        viewModelScope.launch {
            try {
                val siswa = uiStateSiswa.detailSiswa.toDataSiswa()
                // Panggil repository langsung urutan: nama, data
                repositoryDataSiswa.updateDataSiswa(_nama, siswa)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}