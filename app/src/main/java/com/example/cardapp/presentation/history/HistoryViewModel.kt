package com.example.cardapp.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardapp.domain.model.BinInfo
import com.example.cardapp.domain.usecase.GetBinHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getBinHistoryUseCase: GetBinHistoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            getBinHistoryUseCase().collect { binInfoList ->
                _uiState.value = _uiState.value.copy(
                    binHistory = binInfoList,
                    isLoading = false
                )
            }
        }
    }
}

data class HistoryUiState(
    val binHistory: List<BinInfo> = emptyList(),
    val isLoading: Boolean = true
) 