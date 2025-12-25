package com.example.pertemuan12.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan12.modeldata.DetailSiswa
import com.example.pertemuan12.modeldata.UIStateSiswa
import com.example.pertemuan12.modeldata.toDataSiswa
import com.example.pertemuan12.repositori.RepositoryDataSiswa
import kotlinx.coroutines.launch

class EntryViewModel(private val repositoryDataSiswa: RepositoryDataSiswa) : ViewModel() {
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(
            detailSiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
    }

    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    fun addSiswa() {
        viewModelScope.launch {
            if (validasiInput()) {
                try {
                    repositoryDataSiswa.insertDataSiswa(uiStateSiswa.detailSiswa.toDataSiswa())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}