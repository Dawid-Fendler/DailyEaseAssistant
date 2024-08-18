package pl.dawidfendler.dailyeaseassistant.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.dawidfendler.dailyeaseassistant.navigation.SetupNavGraph
import pl.dawidfendler.ui.theme.DailyEaseAssistant

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
        viewModel.onStart()
        setContent {
            DailyEaseAssistant {
                if (viewModel.state.isStarting) {
                    val navController = rememberNavController()
                    SetupNavGraph(
                        startDestination = viewModel.state.navigation,
                        navController = navController
                    )
                }
            }
        }
    }
}
