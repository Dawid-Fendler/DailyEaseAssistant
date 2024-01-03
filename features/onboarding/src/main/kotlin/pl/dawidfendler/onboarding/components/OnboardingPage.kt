package pl.dawidfendler.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import pl.dawidfendler.onboarding.util.Page
import pl.dawidfendler.ui.theme.dp_16

@Composable
internal fun OnboardingPage(
    page: Page
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            painter = painterResource(page.image),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(dp_16))
        Text(
            modifier = Modifier.padding(horizontal = dp_16),
            text = stringResource(id = page.titleId),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dp_16))
        Text(
            modifier = Modifier.padding(horizontal = dp_16),
            text = stringResource(id = page.descriptionId),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(dp_16))
    }
}
