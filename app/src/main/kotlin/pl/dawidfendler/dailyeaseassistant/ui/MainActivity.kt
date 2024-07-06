package pl.dawidfendler.dailyeaseassistant.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pl.dawidfendler.dailyeaseassistant.navigation.SetupNavGraph
import pl.dawidfendler.ui.theme.DailyEaseAssistant
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.navigation.Navigation
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getStartDestination
                    .onEach { result ->
                        when (result) {
                            is DataResult.Success -> initialNavGraph(result.data)
                            is DataResult.Error -> {
                                Timber.e(t = result.throwable, message = "MainActivity")
                                initialNavGraph(Navigation.OnboardingNavigation)
                            }

                            else -> Unit
                        }

                    }.catch { err ->
                        Timber.e(t = err, message = "MainActivity")
                        initialNavGraph(Navigation.OnboardingNavigation)
                    }.collect()

            }
        }
    }

    private fun initialNavGraph(startDestination: Navigation) {
        setContent {
            DailyEaseAssistant {
                val navController = rememberNavController()
                SetupNavGraph(
                    startDestination = startDestination,
                    navController = navController
                )
            }
        }
    }
}
