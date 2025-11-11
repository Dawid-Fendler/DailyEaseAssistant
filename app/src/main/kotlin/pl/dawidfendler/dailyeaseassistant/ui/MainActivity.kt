package pl.dawidfendler.dailyeaseassistant.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.dawidfendler.dailyeaseassistant.navigation.authNavGraph
import pl.dawidfendler.dailyeaseassistant.navigation.mainNavGraph
import pl.dawidfendler.ui.theme.DailyEaseAssistant
import pl.dawidfendler.util.navigation.Destination

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.isStarting
            }
        }
        enableEdgeToEdge()
        viewModel.onStart()
        setContent {
            DailyEaseAssistant {
                if (viewModel.state.isStarting) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = viewModel.state.navigation
                    ) {
                        authNavGraph(
                            navController = navController,
                            startDestination = if (viewModel.state.isOnboardingToDisplay) {
                                Destination.Onboarding
                            } else {
                                Destination.Login
                            }
                        )

                        mainNavGraph(navController)
                    }
                }
            }
        }
    }
}
