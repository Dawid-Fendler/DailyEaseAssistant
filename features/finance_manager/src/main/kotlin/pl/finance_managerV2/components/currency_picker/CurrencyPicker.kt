package pl.finance_managerV2.components.currency_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.finance_manager.R
import pl.dawidfendler.ui.theme.BLUE_BRUSH
import pl.dawidfendler.ui.theme.dp_12
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.dp_56
import pl.dawidfendler.ui.theme.dp_8
import pl.dawidfendler.ui.theme.dp_80
import pl.dawidfendler.ui.theme.sp_0
import pl.dawidfendler.ui.theme.sp_18

@Composable
fun CurrencyPicker(
    modifier: Modifier = Modifier,
    onCurrencySelected: (String) -> Unit,
    currencies: List<ExchangeRateTable>,
) {

    val viewModel: CurrencyPickerViewModel = hiltViewModel()
    viewModel.setCurrencies(currencies)
    val query by viewModel.query.collectAsState()
    val filteredCurrencies by viewModel.filteredCurrencies.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(brush = BLUE_BRUSH)
    ) {
        CurrencyPickerHeader(
            query = query,
            currencies = filteredCurrencies,
            onCurrencySelected = onCurrencySelected,
            viewModel::onQueryChange
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyPickerHeader(
    query: String,
    currencies: List<CurrencyUiModel>,
    onCurrencySelected: (String) -> Unit,
    changeValue: (String) -> Unit
) {
    var isExpanded by rememberSaveable { mutableStateOf(true) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        SearchBar(
            modifier = Modifier
                .background(color = Color.White)
                .weight(1f),
            expanded = isExpanded,
            inputField = {
                TextField(
                    value = query,
                    onValueChange = { changeValue.invoke(it) },
                    singleLine = true,
                    placeholder = { Text(text = stringResource(R.string.search_bar_placeholder)) },
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = dp_12)
                        .clip(shape = RoundedCornerShape(dp_24)),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFF2F3F5),
                        focusedContainerColor = Color(0xFFF2F3F5),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
            },
            content = {
                LazyColumn(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(dp_24))
                        .background(color = Color.White)
                ) {
                    items(currencies) { currency ->
                        ListItem(
                            headlineContent = {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            vertical = dp_8
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(dp_56)
                                            .clip(RoundedCornerShape(dp_8))
                                            .background(Color(0xFFF2F3F5)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CustomText(
                                            text = currency.currencySign,
                                            fontSize = sp_18,
                                            color = Color.Black,
                                            letterSpacing = sp_0
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(dp_24))

                                    Column {
                                        CustomText(
                                            text = currency.currencyCode,
                                            fontSize = sp_18,
                                            color = Color.Black,
                                            letterSpacing = sp_0
                                        )
                                        CustomText(
                                            text = currency.currencyName,
                                            fontSize = sp_18,
                                            color = Color.Gray,
                                            letterSpacing = sp_0
                                        )
                                    }
                                }
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
                containerColor = Color.White
            )
        )
    }
}