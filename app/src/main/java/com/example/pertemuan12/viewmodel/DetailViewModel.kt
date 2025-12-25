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

    // Perbaikan: Ambil ID (Int) bukan Nama
    private val _idSiswa: Int = checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])

    var detailUiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    init {
        getSiswaById()
    }

    // Perbaikan: Fungsi get by ID
    fun getSiswaById() {
        viewModelScope.launch {
            detailUiState = DetailUiState.Loading
            detailUiState = try {
                val siswa = repositoryDataSiswa.getDataSiswaById(_idSiswa)
                DetailUiState.Success(siswa)
            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: Exception) {
                DetailUiState.Error
            }
        }
    }

    // Perbaikan: Delete by ID
    fun deleteSiswa() {
        viewModelScope.launch {
            try {
                repositoryDataSiswa.deleteDataSiswa(_idSiswa)
            } catch (e: IOException) {
                detailUiState = DetailUiState.Error
            } catch (e: Exception) {
                detailUiState = DetailUiState.Error
            }
        }
    }
}