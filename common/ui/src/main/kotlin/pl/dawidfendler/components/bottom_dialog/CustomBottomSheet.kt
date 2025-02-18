package pl.dawidfendler.components.bottom_dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_200
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.dp_40
import pl.dawidfendler.ui.theme.dp_80
import pl.dawidfendler.ui.theme.sp_20
import pl.dawidfendler.ui.theme.sp_24
import pl.dawidfendler.ui.theme.sp_36
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    icon: Int,
    iconTint: Color,
    onDismissAction: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    var ticks by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while(ticks < 5) {
            delay(TICK_VALUE.seconds)
            ticks++
            if (ticks == 5) {
                sheetState.hide()
                onDismissAction.invoke()
                break
            }
        }
    }


    ModalBottomSheet(
        sheetState = sheetState,
        dragHandle = null,
        onDismissRequest = {}
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .height(dp_200)
                .background(
                    color = MaterialTheme.colorScheme.onTertiary
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            CustomText(
                text = title,
                modifier = Modifier
                    .padding(dp_16),
                color = Color.Black,
                fontSize = sp_20,
                textAlign = TextAlign.Center
            )

            Image(
                painter = painterResource(icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(iconTint),
                modifier = Modifier
                    .size(dp_80)
            )

            CustomText(
                fontSize = sp_20,
                text = description,
                modifier = Modifier
                    .padding(top = dp_16, bottom = dp_24),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

const val TIME_TO_DISMISS_BOTTOM_DIALOG = 5
const val TICK_VALUE = 1