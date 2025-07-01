package com.example.cardapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardapp.domain.model.BinInfo
import com.example.cardapp.domain.repository.BinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BinDetailViewModel @Inject constructor(
    private val repository: BinRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(BinDetailUiState())
    val uiState: StateFlow<BinDetailUiState> = _uiState.asStateFlow()

    fun loadBinInfo(bin: String) {
        viewModelScope.launch {
            val binInfo = repository.getCachedBinInfo(bin)
            _uiState.value = _uiState.value.copy(binInfo = binInfo)
        }
    }
}

data class BinDetailUiState(
    val binInfo: BinInfo? = null
) 