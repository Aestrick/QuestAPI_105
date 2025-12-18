package com.example.pertemuan12.uicontroller.route

import com.example.pertemuan12.R

object DestinasiEdit : DestinasiNavigasi {
    override val route = "edit"
    override val titleRes = R.string.edit_siswa
    // GANTI 'nim' JADI 'nama'
    const val nama = "nama"
    val routeWithArgs = "$route/{$nama}"
}