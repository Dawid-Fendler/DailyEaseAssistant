package pl.dawidfendler.currency_converter.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import pl.dawidfendler.currency_converter.CurrencyConverterAction
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.finance_manager.R
import pl.dawidfendler.ui.theme.BLUE_BRUSH
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_SURFACE_TINT
import pl.dawidfendler.ui.theme.SEED
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.dp_4
import pl.dawidfendler.ui.theme.dp_8
import pl.dawidfendler.ui.theme.dp_80
import pl.dawidfendler.ui.theme.sp_18

@Composable
fun CurrencyPicker(
    modifier: Modifier = Modifier,
    onCurrencySelected: (String) -> Unit,
    query: String,
    filteredCurrencies: List<ExchangeRateTable>,
    onAction: (CurrencyConverterAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = BLUE_BRUSH)
    ) {
        CurrencyPickerHeader(
            query = query,
            onAction = onAction,
            currencies = filteredCurrencies,
            onCurrencySelected = onCurrencySelected
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyPickerHeader(
    query: String,
    currencies: List<ExchangeRateTable>,
    onCurrencySelected: (String) -> Unit,
    onAction: (CurrencyConverterAction) -> Unit
) {
    var isExpanded by rememberSaveable { mutableStateOf(true) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dp_4)
            .clip(shape = RoundedCornerShape(dp_24))
    ) {

        SearchBar(
            modifier = Modifier
                .background(brush = BLUE_BRUSH)
                .weight(1f),
            expanded = isExpanded,
            inputField = {
                TextField(
                    value = query,
                    onValueChange = { onAction(CurrencyConverterAction.QueryChange(it)) },
                    singleLine = true,
                    placeholder = { Text(text = stringResource(R.string.search_bar_placeholder)) },
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(dp_24)),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
            },
            content = {
                LazyColumn(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(dp_24))
                        .background(brush = BLUE_BRUSH)
                ) {
                    items(currencies) { currency ->
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = "${currency.currencyName} (${currency.currencyCode})",
                                    fontSize = sp_18,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onCurrencySelected(currency.currencyCode) }
                                .clip(shape = RoundedCornerShape(dp_80))
                                .padding(dp_8)
                        )
                    }
                }
            },
            onExpandedChange = { isExpanded = it },
            colors = SearchBarDefaults.colors(
                containerColor = SEED
            )
        )
    }
}