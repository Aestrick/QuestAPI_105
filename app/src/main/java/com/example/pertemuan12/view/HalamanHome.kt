package com.example.pertemuan12.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan12.modeldata.DataSiswa
// Pastikan import ini benar (sesuaikan nama package kamu)
import com.example.pertemuan12.uicontroller.route.DestinasiHome
import com.example.pertemuan12.viewmodel.HomeUiState
import com.example.pertemuan12.viewmodel.HomeViewModel
import com.example.pertemuan12.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    onDetailClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiHome.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Siswa")
            }
        },
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.homeUiState, // Sudah sesuai dengan ViewModel baru
            retryAction = viewModel::getSiswa,
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteSiswa(it.id)
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (DataSiswa) -> Unit = {},
    onDetailClick: (Int) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success -> {
            if (homeUiState.siswa.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data")
                }
            } else {
                SiswaLayout(
                    siswa = homeUiState.siswa,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator() // Tambahan loading indicator biar cantik
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Gagal memuat data")
        Button(onClick = retryAction) {
            Text("Coba Lagi")
        }
    }
}

@Composable
fun SiswaLayout(
    siswa: List<DataSiswa>,
    modifier: Modifier = Modifier,
    onDetailClick: (DataSiswa) -> Unit,
    onDeleteClick: (DataSiswa) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(siswa) { data ->
            SiswaCard(
                siswa = data,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(data) },
                onDeleteClick = { onDeleteClick(data) }
            )
        }
    }
}

@Composable
fun SiswaCard(
    siswa: DataSiswa,
    modifier: Modifier = Modifier,
    onDeleteClick: (DataSiswa) -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = siswa.nama, style = MaterialTheme.typography.titleLarge)
                IconButton(onClick = { onDeleteClick(siswa) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }
            Text(text = siswa.alamat, style = MaterialTheme.typography.titleMedium)
            Text(text = siswa.telpon, style = MaterialTheme.typography.titleMedium)
        }
    }
}