package com.example.cardapp.presentation.search

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
fun BinSearchScreen(
    onNavigateToHistory: () -> Unit,
    viewModel: BinSearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var isNavigating by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TopAppBar(
            title = { Text("BIN Поиск") },
            actions = {
                IconButton(
                    onClick = { 
                        if (!isNavigating) {
                            isNavigating = true
                            onNavigateToHistory()
                        }
                    },
                    enabled = !isNavigating
                ) {
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
                    value = uiState.bin,
                    onValueChange = viewModel::onBinChanged,
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
                    onClick = viewModel::searchBin,
                    enabled = !uiState.isLoading && uiState.bin.length >= 6,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    } else {
                        Icon(Icons.Default.Search, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text("Поиск")
                }
            }
        }

        // Error message
        uiState.error?.let { error ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = error,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Result
        uiState.binInfo?.let { binInfo ->
            BinInfoCard(
                binInfo = binInfo,
                onUrlClick = { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://$url"))
                    context.startActivity(intent)
                },
                onPhoneClick = { phone ->
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                    context.startActivity(intent)
                },
                onLocationClick = { lat, lon ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:$lat,$lon"))
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun BinInfoCard(
    binInfo: BinInfo,
    onUrlClick: (String) -> Unit,
    onPhoneClick: (String) -> Unit,
    onLocationClick: (Int, Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Информация о карте",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            HorizontalDivider()

            InfoRow("BIN", binInfo.bin)
            binInfo.scheme?.let { InfoRow("Схема", it.uppercase()) }
            binInfo.type?.let { InfoRow("Тип", it.replaceFirstChar { char -> char.uppercase() }) }
            binInfo.brand?.let { InfoRow("Бренд", it) }
            binInfo.prepaid?.let { InfoRow("Предоплаченная", if (it) "Да" else "Нет") }

            binInfo.number?.let { number ->
                Text(
                    text = "Номер карты",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                number.length?.let { InfoRow("  Длина", it.toString()) }
                number.luhn?.let { InfoRow("  Luhn проверка", if (it) "Да" else "Нет") }
            }

            binInfo.country?.let { country ->
                Text(
                    text = "Страна",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                country.name?.let { InfoRow("  Название", "${country.emoji ?: ""} $it") }
                country.alpha2?.let { InfoRow("  Код", it) }
                country.currency?.let { InfoRow("  Валюта", it) }
                
                if (country.latitude != null && country.longitude != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onLocationClick(country.latitude, country.longitude) }
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "  Координаты",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "${country.latitude}, ${country.longitude}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            binInfo.bank?.let { bank ->
                Text(
                    text = "Банк",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                bank.name?.let { InfoRow("  Название", it) }
                bank.city?.let { InfoRow("  Город", it) }
                
                bank.url?.let { url ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onUrlClick(url) }
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "  Сайт",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = url,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                
                bank.phone?.let { phone ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onPhoneClick(phone) }
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "  Телефон",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = phone,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

// Preview functions
@Preview(showBackground = true)
@Composable
fun BinInfoCardPreview() {
    CardAppTheme {
        BinInfoCard(
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
            ),
            onUrlClick = {},
            onPhoneClick = {},
            onLocationClick = { _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BinInfoCardLoadingPreview() {
    CardAppTheme {
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

@Preview(showBackground = true)
@Composable
fun BinSearchErrorPreview() {
    CardAppTheme {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Произошла ошибка при поиске BIN номера",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center
            )
        }
    }
} 