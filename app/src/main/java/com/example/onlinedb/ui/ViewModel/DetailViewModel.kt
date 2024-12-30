package com.example.onlinedb.ui.ViewModel

import com.example.onlinedb.model.Mahasiswa

sealed class DetailUiState{
    data class Success(val mahasiswa: Mahasiswa): DetailUiState()
    object Error: DetailUiState()
    object Loading: DetailUiState()
}
