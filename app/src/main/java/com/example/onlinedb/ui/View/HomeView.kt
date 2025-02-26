package com.example.onlinedb.ui.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onlinedb.R
import com.example.onlinedb.model.Mahasiswa
import com.example.onlinedb.ui.CustomWidget.CustomTopAppBar
import com.example.onlinedb.ui.Navigation.DestinasiNavigasi
import com.example.onlinedb.ui.ViewModel.HomeUiState
import com.example.onlinedb.ui.ViewModel.HomeViewModel
import com.example.onlinedb.ui.ViewModel.PenyediaViewModel


object DestinasiHome: DestinasiNavigasi {
    override val route = "home"
    override val titleRes= "Home Mhs"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
 val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
 Scaffold (
     modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
     topBar = {
         CustomTopAppBar(
             title = DestinasiHome.titleRes,
             canNavigateBack = false,
             scrollBehavior = scrollBehavior,
             onRefresh = {
                 viewModel.getMhs()
             }

         )
     },
     floatingActionButton = {
         FloatingActionButton(
             onClick = navigateToItemEntry,
             shape = MaterialTheme.shapes.medium,
             modifier = Modifier.padding(18.dp)
         ){
             Icon(
                 imageVector = Icons.Default.Add,
                 contentDescription = "Add Mahasiswa"
             )
         }
     }
 ){
     innerPadding ->
     HomeStatus(
         homeUiState = viewModel.mhsUiState,
         retryAction = { viewModel.getMhs() },
         modifier = Modifier
             .padding(innerPadding),
         onDetailClick = onDetailClick,
         onDeleteClick = {
             viewModel.deleteMhs(it.nim)
             viewModel.getMhs()
         }
     )
 }
}


@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Mahasiswa) -> Unit = {},
    onDetailClick: (String) -> Unit = {}
){
    when(homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = Modifier.fillMaxSize())

        is HomeUiState.Success ->
            if (homeUiState.mahasiswa.isEmpty()) {
                return Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data mahasiswa")
                }
            }else{
                MhsLayout(
                    mahasiswa = homeUiState.mahasiswa,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = {
                        onDeleteClick(it)
                    },
                    onDetailClick = {
                        onDetailClick(it.nim)
                    }
                )
            }
        is HomeUiState.Error -> onErr(retryAction, modifier = Modifier.fillMaxSize())
    }
}


@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        painter = painterResource(id = R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading),
        modifier = modifier.size(200.dp)
    )
}

@Composable
fun onErr(
    retryAction: () -> Unit,modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = null
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}


@Composable
fun MhsLayout(
    mahasiswa: List<Mahasiswa>,
    modifier: Modifier = Modifier,
    onDeleteClick: (Mahasiswa) -> Unit = {},
    onDetailClick: (Mahasiswa) -> Unit
){
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(mahasiswa){mahasiswa ->
            MhsCard(
                mahasiswa = mahasiswa,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(mahasiswa) },
                onDeleteClick = {
                    onDeleteClick(mahasiswa)
                }
            )
        }
    }
}


@Composable
fun MhsCard(
    mahasiswa: Mahasiswa,
    modifier: Modifier = Modifier,
    onDeleteClick: (Mahasiswa) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = mahasiswa.nama,
                    style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(mahasiswa) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
                Text(
                    text = mahasiswa.nim,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = mahasiswa.kelas,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = mahasiswa.alamat,
                style = MaterialTheme.typography.titleMedium
            )
        }

    }
}