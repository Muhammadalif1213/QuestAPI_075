package com.example.onlinedb.ui.View

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onlinedb.ui.CustomWidget.CustomTopAppBar
import com.example.onlinedb.ui.Navigation.DestinasiNavigasi
import com.example.onlinedb.ui.ViewModel.PenyediaViewModel
import com.example.onlinedb.ui.ViewModel.UpdateViewModel
import kotlinx.coroutines.launch


object DestinasiUpdate: DestinasiNavigasi {
    override val route = "item_update"
    override val titleRes= "Update Mhs"
    const val NIM = "nim"
    val routeWithArgs = "$route/{$NIM}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    updateViewModel: UpdateViewModel = viewModel(factory = PenyediaViewModel.Factory))
{
        val coroutineScope = rememberCoroutineScope()
        Scaffold(
            topBar = {
                CustomTopAppBar(
                    title = DestinasiUpdate.titleRes,
                    canNavigateBack = true,
                    navigateUp = navigateBack
                )
            },
            modifier = modifier
        ) {innerPadding ->
            EntryBody(
                insertUiState = updateViewModel.updateUiState,
                onSiswaValueChange = updateViewModel::updateMahasiswaState,
                onSaveClick = {
                    coroutineScope.launch {
                        updateViewModel.updateMahasiswa()
                        navigateBack()
                    }
                },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }