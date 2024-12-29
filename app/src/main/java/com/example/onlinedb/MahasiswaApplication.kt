package com.example.onlinedb

import android.app.Application
import com.example.onlinedb.dependeciesinjection.AppContainer
import com.example.onlinedb.dependeciesinjection.MahasiswaContainer

class MahasiswaApplication: Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = MahasiswaContainer()
    }
}
