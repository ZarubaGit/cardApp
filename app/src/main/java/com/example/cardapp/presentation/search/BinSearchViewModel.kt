package com.example.cardapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardapp.domain.model.BinInfo
import com.example.cardapp.domain.usecase.GetBinInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BinSearchViewModel @Inject constructor(
    private val getBinInfoUseCase: GetBinInfoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BinSearchUiState())
    val uiState: StateFlow<BinSearchUiState> = _uiState.asStateFlow()

    fun onBinChanged(bin: String) {
        _uiState.value = _uiState.value.copy(
            bin = bin.filter { it.isDigit() }.take(8),
            error = null
        )
    }

    fun searchBin() {
        val currentBin = _uiState.value.bin
        if (currentBin.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Введите BIN номер")
            return
        }

        _uiState.value = _uiState.value.copy(
            isLoading = true,
            error = null,
            binInfo = null
        )

        viewModelScope.launch {
            getBinInfoUseCase(currentBin)
                .onSuccess { binInfo ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        binInfo = binInfo,
                        error = null
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Произошла ошибка"
                    )
                }
        }
    }

    fun clearResult() {
        _uiState.value = _uiState.value.copy(
            binInfo = null,
            error = null
        )
    }
}

data class BinSearchUiState(
    val bin: String = "",
    val isLoading: Boolean = false,
    val binInfo: BinInfo? = null,
    val error: String? = null
) 