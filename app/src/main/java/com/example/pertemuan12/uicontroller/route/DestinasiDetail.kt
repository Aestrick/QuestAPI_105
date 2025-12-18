package com.example.pertemuan12.uicontroller.route

import com.example.pertemuan12.R

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = R.string.detail_siswa
    // GANTI 'nim' JADI 'nama'
    const val nama = "nama"
    val routeWithArgs = "$route/{$nama}"
}