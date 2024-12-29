package com.example.onlinedb.ui.ViewModel

import androidx.lifecycle.ViewModel
import com.example.onlinedb.model.Mahasiswa
import com.example.onlinedb.repository.MahasiswaRepository

sealed class HomeUiState{
    data class Success(val mahasiswa: List<Mahasiswa>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomeViewModel(private val mhs: MahasiswaRepository): ViewModel(){

}