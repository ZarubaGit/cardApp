package com.example.cardapp.presentation.preview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cardapp.presentation.search.BinInfoCard
import com.example.cardapp.domain.model.Bank
import com.example.cardapp.domain.model.BinInfo
import com.example.cardapp.domain.model.CardNumber
import com.example.cardapp.domain.model.Country
import com.example.cardapp.ui.theme.CardAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, heightDp = 800)
@Composable
fun BinSearchScreenPreview() {
    CardAppTheme {
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
                            value = "457173",
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
                    binInfo = getSampleBinInfo(),
                    onUrlClick = {},
                    onPhoneClick = {},
                    onLocationClick = { _, _ -> }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, heightDp = 600)
@Composable
fun BinSearchEmptyPreview() {
    CardAppTheme {
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
                            value = "",
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
                            enabled = false,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Search, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Поиск")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, widthDp = 320, heightDp = 600)
@Composable
fun BinSearchPhonePreview() {
    CardAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
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
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Введите BIN номер банковской карты",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        OutlinedTextField(
                            value = "532130",
                            onValueChange = {},
                            label = { Text("BIN") },
                            placeholder = { Text("457173") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
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
            }
        }
    }
}

// Sample data
private fun getSampleBinInfo(): BinInfo {
    return BinInfo(
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
} 