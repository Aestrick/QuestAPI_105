package com.example.pertemuan12.repositori

import android.app.Application

class AplikasiDataSiswa : Application() {
    // 1. Ganti tipe datanya jadi AppContainer (Sesuai interface yang kita buat)
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        // 2. Inisialisasi menggunakan ContainerDataSiswa (Sesuai class yang kita buat)
        container = ContainerDataSiswa()
    }
}