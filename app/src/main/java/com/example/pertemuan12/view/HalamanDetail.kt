package com.example.pertemuan12.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan12.R
import com.example.pertemuan12.modeldata.DataSiswa
import com.example.pertemuan12.uicontroller.route.DestinasiDetail
import com.example.pertemuan12.viewmodel.DetailUiState
import com.example.pertemuan12.viewmodel.DetailViewModel
import com.example.pertemuan12.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navigateBack: () -> Unit,
    onEditClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.detailUiState

    Scaffold(
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiDetail.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            if (uiState is DetailUiState.Success) {
                FloatingActionButton(
                    onClick = { onEditClick(uiState.siswa.id) },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(18.dp)
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Siswa")
                }
            }
        }
    ) { innerPadding ->
        DetailStatus(
            detailUiState = uiState,
            retryAction = { viewModel.getSiswaById() },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun DetailStatus(
    detailUiState: DetailUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (detailUiState) {
        is DetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailUiState.Success -> DetailLayout(
            siswa = detailUiState.siswa,
            modifier = modifier.fillMaxWidth()
        )
        is DetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun DetailLayout(
    siswa: DataSiswa,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ItemDetail(judul = "Nama", isi = siswa.nama)
        ItemDetail(judul = "Alamat", isi = siswa.alamat)
        ItemDetail(judul = "Telepon", isi = siswa.telpon)
    }
}

@Composable
fun ItemDetail(judul: String, isi: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = judul, style = MaterialTheme.typography.labelLarge)
        Text(text = isi, style = MaterialTheme.typography.titleLarge)
    }
}