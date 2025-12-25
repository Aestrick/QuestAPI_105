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

sealed interface HomeUiState {
    data class Success(val siswa: List<DataSiswa>) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}

class HomeViewModel(private val repositoryDataSiswa: RepositoryDataSiswa) : ViewModel() {

    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getSiswa()
    }

    fun getSiswa() {
        viewModelScope.launch {
            homeUiState = HomeUiState.Loading
            homeUiState = try {
                HomeUiState.Success(repositoryDataSiswa.getDataSiswa())
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            } catch (e: Exception) {
                println("Error Lain: ${e.message}")
                HomeUiState.Error
            }
        }
    }

    fun deleteSiswa(id: Int) {
        viewModelScope.launch {
            try {
                repositoryDataSiswa.deleteDataSiswa(id)
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