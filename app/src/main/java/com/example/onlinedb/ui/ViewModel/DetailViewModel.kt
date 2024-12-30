package com.example.onlinedb.ui.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinedb.model.Mahasiswa
import com.example.onlinedb.repository.MahasiswaRepository
import kotlinx.coroutines.launch


sealed class DetailUiState{
    data class Success(val mahasiswa: Mahasiswa): DetailUiState()
    object Error: DetailUiState()
    object Loading: DetailUiState()
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val mhs: MahasiswaRepository
): ViewModel() {
    private val nim: String = checkNotNull(savedStateHandle["nim"])
    var detailMhsUiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    init {
        getMhsById()
    }

    fun getMhsById() {
        viewModelScope.launch {
            detailMhsUiState = DetailUiState.Loading
            detailMhsUiState = try {
                DetailUiState.Success(mhs.getMahasiswaByNIM(nim))
            } catch (e: Exception) {
                DetailUiState.Error
            }
        }
    }
}
