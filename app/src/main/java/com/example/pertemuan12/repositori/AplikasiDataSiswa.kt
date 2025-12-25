package com.example.pertemuan12.repositori

import android.app.Application

class AplikasiDataSiswa : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = ContainerDataSiswa()
    }
}