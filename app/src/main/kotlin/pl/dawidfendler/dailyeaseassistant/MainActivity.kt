package pl.dawidfendler.dailyeaseassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.dawidfendler.dailyeaseassistant.navigation.SetupNavGraph
import pl.dawidfendler.onboarding.OnboardingViewModel
import pl.dawidfendler.ui.theme.DailyEaseAssistant

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: OnboardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        viewModel.getOnboardingDisplayed()
        setContent {
            DailyEaseAssistant {
                val navController = rememberNavController()
                SetupNavGraph(
                    startDestination = viewModel.startDestination,
                    navController = navController
                )
            }
        }
    }
}
