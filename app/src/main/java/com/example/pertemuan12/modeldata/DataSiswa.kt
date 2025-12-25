@file:OptIn(kotlinx.serialization.InternalSerializationApi::class)
package com.example.pertemuan12.modeldata

import kotlinx.serialization.Serializable
import kotlinx.serialization.InternalSerializationApi

@Serializable
data class DataSiswa(
    val id: Int,
    val nama: String,
    val alamat: String,
    val telpon: String
)

data class DetailSiswa(
    val id: Int = 0,
    val nama: String = "",
    val alamat: String = "",
    val telpon: String = ""
)

data class UIStateSiswa(
    val detailSiswa: DetailSiswa = DetailSiswa(),
    val isEntryValid: Boolean = false
)

fun DetailSiswa.toDataSiswa(): DataSiswa = DataSiswa(
    id = id, // Bagian ini yang tadi error "No value passed for parameter id"
    nama = nama,
    alamat = alamat,
    telpon = telpon
)

fun DataSiswa.toDetailSiswa(): DetailSiswa = DetailSiswa(
    id = id,
    nama = nama,
    alamat = alamat,
    telpon = telpon
)

fun DataSiswa.toUIStateSiswa(isEntryValid: Boolean = false): UIStateSiswa = UIStateSiswa(
    detailSiswa = this.toDetailSiswa(),
    isEntryValid = isEntryValid
)