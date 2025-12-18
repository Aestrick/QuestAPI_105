package com.example.pertemuan12.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class DataSiswa(
    val nama: String,
    val alamat: String,
    val telpon: String
)