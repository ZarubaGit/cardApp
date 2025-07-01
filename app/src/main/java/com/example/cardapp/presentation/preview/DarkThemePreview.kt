package com.example.cardapp.presentation.preview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cardapp.presentation.search.BinInfoCard
import com.example.cardapp.presentation.history.HistoryItem
import com.example.cardapp.domain.model.Bank
import com.example.cardapp.domain.model.BinInfo
import com.example.cardapp.domain.model.CardNumber
import com.example.cardapp.domain.model.Country
import com.example.cardapp.ui.theme.CardAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, heightDp = 800, name = "Dark Theme - Search with Result")
@Composable
fun BinSearchScreenDarkPreview() {
    CardAppTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TopAppBar(
                    title = { Text("BIN Поиск") },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.List, contentDescription = "История")
                        }
                    }
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Введите BIN номер банковской карты",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        OutlinedTextField(
                            value = "532130",
                            onValueChange = {},
                            label = { Text("BIN (первые 6-8 цифр карты)") },
                            placeholder = { Text("457173") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            supportingText = {
                                Text("Введите первые 6-8 цифр номера карты")
                            }
                        )

                        Button(
                            onClick = {},
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Search, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Поиск")
                        }
                    }
                }

                BinInfoCard(
                    binInfo = getSampleMastercardBinInfo(),
                    onUrlClick = {},
                    onPhoneClick = {},
                    onLocationClick = { _, _ -> }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, heightDp = 800, name = "Dark Theme - History")
@Composable
fun HistoryScreenDarkPreview() {
    CardAppTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
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
                    items(getSampleBinHistoryDark()) { binInfo ->
                        HistoryItem(binInfo = binInfo, onDelete = {})
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Dark Theme - Error State")
@Composable
fun BinSearchErrorDarkPreview() {
    CardAppTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Карта с таким BIN номером не найдена. Проверьте правильность ввода.",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Dark Theme - Empty History")
@Composable
fun HistoryEmptyStateDarkPreview() {
    CardAppTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Dark Theme - Loading State")
@Composable
fun BinSearchLoadingDarkPreview() {
    CardAppTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Введите BIN номер банковской карты",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        OutlinedTextField(
                            value = "424631",
                            onValueChange = {},
                            label = { Text("BIN (первые 6-8 цифр карты)") },
                            placeholder = { Text("457173") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            supportingText = {
                                Text("Введите первые 6-8 цифр номера карты")
                            }
                        )

                        Button(
                            onClick = {},
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Поиск")
                        }
                    }
                }
            }
        }
    }
}

// Sample data for dark theme previews
private fun getSampleMastercardBinInfo(): BinInfo {
    return BinInfo(
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
}

private fun getSampleBinHistoryDark(): List<BinInfo> {
    return listOf(
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
        ),
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
        )
    )
} 