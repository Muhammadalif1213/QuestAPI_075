package com.example.onlinedb.ui.ViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.onlinedb.MahasiswaApplication

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(mhsApp().container.mahasiswaRepository)
        }
        initializer {
            InsertViewModel(mhsApp().container.mahasiswaRepository)
        }
    }
}

fun CreationExtras.mhsApp(): MahasiswaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as MahasiswaApplication)