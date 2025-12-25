package com.example.pertemuan12.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan12.modeldata.DataSiswa
import com.example.pertemuan12.modeldata.DetailSiswa
import com.example.pertemuan12.modeldata.toDataSiswa
import com.example.pertemuan12.modeldata.toDetailSiswa
import com.example.pertemuan12.modeldata.UIStateSiswa
import com.example.pertemuan12.repositori.RepositoryDataSiswa
import com.example.pertemuan12.uicontroller.route.DestinasiEdit
import kotlinx.coroutines.launch

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {

    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    // Perbaikan: Ambil ID (Int)
    private val _idSiswa: Int = checkNotNull(savedStateHandle[DestinasiEdit.itemIdArg])

    init {
        viewModelScope.launch {
            try {
                val siswa = repositoryDataSiswa.getDataSiswaById(_idSiswa)
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
                repositoryDataSiswa.updateDataSiswa(_idSiswa, uiStateSiswa.detailSiswa.toDataSiswa())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}