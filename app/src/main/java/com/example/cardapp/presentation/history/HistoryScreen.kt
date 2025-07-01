package com.example.cardapp.presentation.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cardapp.domain.model.Bank
import com.example.cardapp.domain.model.BinInfo
import com.example.cardapp.domain.model.CardNumber
import com.example.cardapp.domain.model.Country
import com.example.cardapp.ui.theme.CardAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onNavigateBack: () -> Unit,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var isNavigating by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("История запросов") },
            navigationIcon = {
                IconButton(
                    onClick = { 
                        if (!isNavigating) {
                            isNavigating = true
                            onNavigateBack()
                        }
                    },
                    enabled = !isNavigating
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                }
            }
        )

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.binHistory.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "История пуста",
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Выполните поиск BIN номера для добавления в историю",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.binHistory) { binInfo ->
                        HistoryItem(binInfo = binInfo)
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryItem(binInfo: BinInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Header with BIN
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "BIN: ${binInfo.bin}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                binInfo.scheme?.let { scheme ->
                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = scheme.uppercase(),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            HorizontalDivider()

            // Card info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    binInfo.brand?.let { brand ->
                        Text(
                            text = "Бренд: $brand",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    binInfo.type?.let { type ->
                        Text(
                            text = "Тип: ${type.replaceFirstChar { it.uppercase() }}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                
                Column(modifier = Modifier.weight(1f)) {
                    binInfo.country?.let { country ->
                        Text(
                            text = "${country.emoji ?: ""} ${country.name ?: ""}".trim(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    binInfo.bank?.name?.let { bankName ->
                        Text(
                            text = bankName,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

// Preview functions
@Preview(showBackground = true)
@Composable
fun HistoryItemPreview() {
    CardAppTheme {
        HistoryItem(
            binInfo = BinInfo(
                bin = "457173",
                scheme = "visa",
                type = "debit",
                brand = "Visa/Dankort",
                prepaid = false,
                number = CardNumber(
                    length = 16,
                    luhn = true
                ),
                country = Country(
                    numeric = "208",
                    alpha2 = "DK",
                    name = "Denmark",
                    emoji = "🇩🇰",
                    currency = "DKK",
                    latitude = 56,
                    longitude = 10
                ),
                bank = Bank(
                    name = "Jyske Bank",
                    url = "www.jyskebank.dk",
                    phone = "+4589893300",
                    city = "Hjørring"
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryItemMastercardPreview() {
    CardAppTheme {
        HistoryItem(
            binInfo = BinInfo(
                bin = "532130",
                scheme = "mastercard",
                type = "credit",
                brand = "Mastercard",
                prepaid = true,
                number = CardNumber(
                    length = 16,
                    luhn = true
                ),
                country = Country(
                    numeric = "840",
                    alpha2 = "US",
                    name = "United States",
                    emoji = "🇺🇸",
                    currency = "USD",
                    latitude = 37,
                    longitude = -95
                ),
                bank = Bank(
                    name = "Chase Bank",
                    url = "www.chase.com",
                    phone = "+1-800-935-9935",
                    city = "New York"
                )
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HistoryEmptyStatePreview() {
    CardAppTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = { Text("История запросов") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
            
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "История пуста",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Выполните поиск BIN номера для добавления в историю",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HistoryWithDataPreview() {
    CardAppTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = { Text("История запросов") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
            
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(getSampleBinHistory()) { binInfo ->
                    HistoryItem(binInfo = binInfo)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HistoryLoadingPreview() {
    CardAppTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = { Text("История запросов") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
            
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

// Sample data for preview
private fun getSampleBinHistory(): List<BinInfo> {
    return listOf(
        BinInfo(
            bin = "457173",
            scheme = "visa",
            type = "debit",
            brand = "Visa/Dankort",
            prepaid = false,
            number = CardNumber(length = 16, luhn = true),
            country = Country(
                numeric = "208", alpha2 = "DK", name = "Denmark", 
                emoji = "🇩🇰", currency = "DKK", latitude = 56, longitude = 10
            ),
            bank = Bank(
                name = "Jyske Bank", url = "www.jyskebank.dk", 
                phone = "+4589893300", city = "Hjørring"
            )
        ),
        BinInfo(
            bin = "532130",
            scheme = "mastercard",
            type = "credit",
            brand = "Mastercard",
            prepaid = true,
            number = CardNumber(length = 16, luhn = true),
            country = Country(
                numeric = "840", alpha2 = "US", name = "United States", 
                emoji = "🇺🇸", currency = "USD", latitude = 37, longitude = -95
            ),
            bank = Bank(
                name = "Chase Bank", url = "www.chase.com", 
                phone = "+1-800-935-9935", city = "New York"
            )
        ),
        BinInfo(
            bin = "424631",
            scheme = "visa",
            type = "credit",
            brand = "Visa",
            prepaid = false,
            number = CardNumber(length = 16, luhn = true),
            country = Country(
                numeric = "643", alpha2 = "RU", name = "Russia", 
                emoji = "🇷🇺", currency = "RUB", latitude = 60, longitude = 100
            ),
            bank = Bank(
                name = "Sberbank", url = "www.sberbank.ru", 
                phone = "+7-495-500-5550", city = "Moscow"
            )
        )
    )
} 