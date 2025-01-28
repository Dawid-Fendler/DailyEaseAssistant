package pl.dawidfendler.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import pl.dawidfendler.onboarding.components.OnboardingPage
import pl.dawidfendler.onboarding.components.WormIndicator
import pl.dawidfendler.onboarding.util.pages
import pl.dawidfendler.ui.theme.dp_12
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.util.ext.emptyString

@Composable
internal fun OnboardingScreen(
    onFinishButtonClick: () -> Unit,
    navigateToAuth: () -> Unit
) {
    Column(
        modifier =
        Modifier.fillMaxSize()
    ) {
        val pagerState = rememberPagerState(initialPage = ONBOARDING_FIRST_PAGE) {
            pages.size
        }
        val coroutineScope = rememberCoroutineScope()
        val buttonState = remember { mutableStateOf(emptyString()) }
        LaunchedEffect(key1 = pagerState.currentPage) {
            buttonState.value = when (pagerState.currentPage) {
                ONBOARDING_FIRST_PAGE, ONBOARDING_SECOND_PAGE -> BUTTON_NEXT_TITLE
                ONBOARDING_THIRD_PAGE -> BUTTON_FINISH_TITLE
                else -> emptyString()
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        HorizontalPager(state = pagerState) { index ->
            OnboardingPage(page = pages[index])
        }

        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dp_12)
                .padding(horizontal = dp_16),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            WormIndicator(
                modifier = Modifier,
                count = pages.size,
                pagerState = pagerState
            )
            Spacer(modifier = Modifier.weight(1f))

            Button(
                shape = RoundedCornerShape(dp_16),
                onClick = {
                if (pagerState.currentPage == ONBOARDING_THIRD_PAGE) {
                    onFinishButtonClick.invoke()
                    navigateToAuth.invoke()
                } else {
                    coroutineScope.launch {
                        pagerState.scrollToPage(pagerState.currentPage + 1)
                    }
                }
            }) {
                Text(text = buttonState.value)
            }
        }
    }
}

private const val ONBOARDING_FIRST_PAGE = 0
private const val ONBOARDING_SECOND_PAGE = 1
private const val ONBOARDING_THIRD_PAGE = 2
private const val BUTTON_NEXT_TITLE = "Next"
private const val BUTTON_FINISH_TITLE = "Finish"
