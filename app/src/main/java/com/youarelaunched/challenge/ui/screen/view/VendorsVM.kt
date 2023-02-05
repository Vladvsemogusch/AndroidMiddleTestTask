package com.youarelaunched.challenge.ui.screen.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youarelaunched.challenge.data.repository.VendorsRepository
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VendorsVM @Inject constructor(
    private val repository: VendorsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        VendorsScreenUiState(
            vendors = null,
            searchQuery = ""
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        getVendors()
        viewModelScope.launch {
            uiState
                .map { it.searchQuery }
                .filter { it.length > 2 }
                .debounce(500)
                .collect {
                    onSearchClick()
                }
        }

    }

    fun getVendors(searchQuery: String = "") {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    vendors = repository.getVendors(searchQuery)
                )
            }
        }
    }

    fun onSearchQueryChange(newSearchQuery: String) {
        _uiState.update { it.copy(searchQuery = newSearchQuery) }
    }

    fun onSearchClick() {
        getVendors(uiState.value.searchQuery)
    }

}