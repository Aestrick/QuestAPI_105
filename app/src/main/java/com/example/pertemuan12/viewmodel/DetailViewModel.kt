package com.example.pertemuan12.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan12.modeldata.DataSiswa
import com.example.pertemuan12.repositori.RepositoryDataSiswa
import com.example.pertemuan12.uicontroller.route.DestinasiDetail
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface DetailUiState {
    data class Success(val siswa: DataSiswa) : DetailUiState
    object Error : DetailUiState
    object Loading : DetailUiState
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {

    // INI AMAN SEKARANG (Karena DestinasiDetail udah diganti jadi 'nama')
    private val _nama: String = checkNotNull(savedStateHandle[DestinasiDetail.nama])

    var detailUiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    init {
        getSiswaByName()
    }

    fun getSiswaByName() {
        viewModelScope.launch {
            detailUiState = DetailUiState.Loading
            detailUiState = try {
                val siswa = repositoryDataSiswa.getDataSiswaById(_nama)
                DetailUiState.Success(siswa)
            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: Exception) {
                DetailUiState.Error
            }
        }
    }

    fun deleteSiswa() {
        viewModelScope.launch {
            try {
                repositoryDataSiswa.deleteDataSiswa(_nama)
            } catch (e: IOException) {
                detailUiState = DetailUiState.Error
            } catch (e: Exception) {
                detailUiState = DetailUiState.Error
            }
        }
    }
}