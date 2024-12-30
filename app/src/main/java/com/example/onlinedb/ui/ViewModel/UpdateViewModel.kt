package com.example.onlinedb.ui.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinedb.repository.MahasiswaRepository
import kotlinx.coroutines.launch

class UpdateViewModel (
    savedStateHandle: SavedStateHandle,
    private val mhs: MahasiswaRepository
): ViewModel() {
    var updateUiState by mutableStateOf(InsertUiState())
        private set
    val nim: String = checkNotNull(savedStateHandle["nim"])

    init {
        viewModelScope.launch {
            updateUiState = mhs.getMahasiswaByNIM(nim).toUiStateMhs()
        }
    }

    fun updateMahasiswaState(insertUiEvent: InsertUiEvent) {
        updateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateMahasiswa() {
        viewModelScope.launch {
            try {
                mhs.updateMahasiswa(nim, updateUiState.insertUiEvent.toMhs())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}