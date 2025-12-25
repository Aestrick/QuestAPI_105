package com.example.pertemuan12.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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

    // State untuk mengontrol dialog konfirmasi hapus
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            DetailStatus(
                detailUiState = uiState,
                retryAction = { viewModel.getSiswaById() },
                onDeleteClick = {
                    deleteConfirmationRequired = true
                }
            )
        }

        // Logika Dialog Konfirmasi
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    viewModel.deleteSiswa()
                    navigateBack()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun DetailStatus(
    detailUiState: DetailUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    when (detailUiState) {
        is DetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailUiState.Success -> DetailLayout(
            siswa = detailUiState.siswa,
            modifier = modifier.fillMaxWidth(),
            onDeleteClick = onDeleteClick
        )
        is DetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun DetailLayout(
    siswa: DataSiswa,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ItemDetail(judul = "Nama", isi = siswa.nama)
        ItemDetail(judul = "Alamat", isi = siswa.alamat)
        ItemDetail(judul = "Telepon", isi = siswa.telpon)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onDeleteClick,
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Hapus Data")
        }
    }
}

@Composable
fun ItemDetail(judul: String, isi: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = judul, style = MaterialTheme.typography.labelLarge)
        Text(text = isi, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Tidak melakukan apa-apa */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(text = stringResource(R.string.delete)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        }
    )
}