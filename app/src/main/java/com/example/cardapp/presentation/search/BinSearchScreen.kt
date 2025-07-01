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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cardapp.R
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
            title = { Text(stringResource(R.string.bin_search)) },
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
                    Icon(Icons.Default.List, contentDescription = stringResource(R.string.history))
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
                    text = stringResource(R.string.enter_bin),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = uiState.bin,
                    onValueChange = viewModel::onBinChanged,
                    label = { Text(stringResource(R.string.bin_hint)) },
                    placeholder = { Text(stringResource(R.string.bin_placeholder)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = {
                        Text(stringResource(R.string.bin_supporting_text))
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
                    Text(stringResource(R.string.search))
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
                text = stringResource(R.string.card_info),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            HorizontalDivider()

            InfoRow(stringResource(R.string.bin_label), binInfo.bin)
            binInfo.scheme?.let { InfoRow(stringResource(R.string.scheme), it.uppercase()) }
            binInfo.type?.let { InfoRow(stringResource(R.string.type), it.replaceFirstChar { char -> char.uppercase() }) }
            binInfo.brand?.let { InfoRow(stringResource(R.string.brand), it) }
            binInfo.prepaid?.let { InfoRow(stringResource(R.string.prepaid), if (it) stringResource(R.string.yes) else stringResource(R.string.no)) }

            binInfo.number?.let { number ->
                Text(
                    text = stringResource(R.string.card_number),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                number.length?.let { InfoRow("  " + stringResource(R.string.length), it.toString()) }
                number.luhn?.let { InfoRow("  " + stringResource(R.string.luhn_check), if (it) stringResource(R.string.yes) else stringResource(R.string.no)) }
            }

            binInfo.country?.let { country ->
                Text(
                    text = stringResource(R.string.country),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                country.name?.let { InfoRow("  " + stringResource(R.string.name), "${country.emoji ?: ""} $it") }
                country.alpha2?.let { InfoRow("  " + stringResource(R.string.code), it) }
                country.currency?.let { InfoRow("  " + stringResource(R.string.currency), it) }
                
                if (country.latitude != null && country.longitude != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onLocationClick(country.latitude, country.longitude) }
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "  " + stringResource(R.string.coordinates),
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
                    text = stringResource(R.string.bank),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                bank.name?.let { InfoRow("  " + stringResource(R.string.name), it) }
                bank.city?.let { InfoRow("  " + stringResource(R.string.city), it) }
                
                bank.url?.let { url ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onUrlClick(url) }
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "  " + stringResource(R.string.website),
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
                            text = "  " + stringResource(R.string.phone),
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
                    text = stringResource(R.string.enter_bin),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = "457173",
                    onValueChange = {},
                    label = { Text(stringResource(R.string.bin_hint)) },
                    placeholder = { Text(stringResource(R.string.bin_placeholder)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = {
                        Text(stringResource(R.string.bin_supporting_text))
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
                    Text(stringResource(R.string.search))
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