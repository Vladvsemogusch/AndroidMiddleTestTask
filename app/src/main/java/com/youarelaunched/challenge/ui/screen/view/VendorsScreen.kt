package com.youarelaunched.challenge.ui.screen.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import com.youarelaunched.challenge.ui.screen.view.components.ChatsumerSnackbar
import com.youarelaunched.challenge.ui.screen.view.components.NoResult
import com.youarelaunched.challenge.ui.screen.view.components.SearchBar
import com.youarelaunched.challenge.ui.screen.view.components.VendorItem
import com.youarelaunched.challenge.ui.theme.VendorAppTheme

@Composable
fun VendorsRoute(
    viewModel: VendorsVM
) {
    val uiState by viewModel.uiState.collectAsState()

    VendorsScreen(
        uiState = uiState,
        onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
        onSearchClick = viewModel::onSearchClick
    )
}

@Composable
fun VendorsScreen(
    uiState: VendorsScreenUiState,
    onSearchQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = VendorAppTheme.colors.background,
        snackbarHost = { ChatsumerSnackbar(it) }
    ) { paddings ->
        Box(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize()
        ) {
            SearchBar(
                searchQuery = uiState.searchQuery,
                onSearchClick = onSearchClick,
                onValueChange = onSearchQueryChange,
                modifier = Modifier
                    .zIndex(1f)
                    .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                    .fillMaxWidth()
            )

            if (!uiState.vendors.isNullOrEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(paddings),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(
                        vertical = 24.dp,
                        horizontal = 16.dp
                    )
                ) {
                    item {
                        Spacer(modifier = Modifier.height(48.dp))
                    }
                    items(uiState.vendors) { vendor ->
                        VendorItem(
                            vendor = vendor
                        )
                    }

                }
            } else {
                NoResult()
            }


        }
    }
}