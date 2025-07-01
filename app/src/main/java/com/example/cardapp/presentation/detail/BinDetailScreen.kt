package com.example.cardapp.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cardapp.R
import com.example.cardapp.presentation.search.BinInfoCard
import com.example.cardapp.presentation.detail.BinDetailViewModel
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BinDetailScreen(
    bin: String,
    onBack: () -> Unit,
    viewModel: BinDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Загружаем данные при первом запуске
    androidx.compose.runtime.LaunchedEffect(bin) {
        viewModel.loadBinInfo(bin)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(stringResource(R.string.card_info)) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        uiState.binInfo?.let { binInfo ->
            BinInfoCard(
                binInfo = binInfo,
                onUrlClick = {},
                onPhoneClick = {},
                onLocationClick = { _, _ -> }
            )
        } ?: run {
            Text(
                text = stringResource(R.string.search),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
} 