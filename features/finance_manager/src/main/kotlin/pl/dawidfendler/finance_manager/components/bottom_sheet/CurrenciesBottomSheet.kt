package pl.dawidfendler.finance_manager.components.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import pl.dawidfendler.ui.theme.dp_1
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_4
import pl.dawidfendler.ui.theme.dp_8
import pl.dawidfendler.ui.theme.sp_14

@Composable
internal fun CurrenciesBottomSheet(
    modifier: Modifier = Modifier,
    currencies: List<String>,
    selectedCurrency: String,
    onSelectCurrency: (String) -> Unit
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(currencies) { currency ->
            CurrencyItem(
                currency = currency,
                selectedCurrency = selectedCurrency,
                onSelectCurrency = onSelectCurrency
            )
        }
    }
}

@Composable
fun CurrencyItem(
    modifier: Modifier = Modifier,
    currency: String,
    selectedCurrency: String,
    onSelectCurrency: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = dp_16,
                vertical = dp_4
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(dp_16)
            )
            .border(
                width = dp_1,
                color = if (currency == selectedCurrency) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Black
                },
                shape = RoundedCornerShape(dp_16)
            )
            .clickable {
                onSelectCurrency(currency)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = currency,
            color = if (currency == selectedCurrency) {
                MaterialTheme.colorScheme.primary
            } else {
                Color.Black
            },
            style = TextStyle(
                fontSize = sp_14,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(start = dp_8)
                .weight(1f)
        )

        RadioButton(
            selected = currency == selectedCurrency,
            onClick = {
                onSelectCurrency(currency)
            },
            colors = RadioButtonDefaults.colors().copy(
                selectedColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}