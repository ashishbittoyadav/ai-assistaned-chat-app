package com.ashish.ollama_chat_application

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.rememberLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ashish.ollama_chat_application.navigation.UiEvent
import com.ashish.ollama_chat_application.screen.ChatHistory
import com.ashish.ollama_chat_application.screen.ChatScreen
import com.ashish.ollama_chat_application.screen.DragonBallZDetailScreen
import com.ashish.ollama_chat_application.screen.DragonBallZScreen
import com.ashish.ollama_chat_application.ui.theme.Ollama_chat_applicationTheme
import com.ashish.ollama_chat_application.view_model.ChatViewModel
import com.ashish.ollama_chat_application.view_model.DragonBallZViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import okhttp3.Dispatcher
import kotlin.time.Duration.Companion.milliseconds

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ChatViewModel by viewModels()
            val dragonBallViewModel: DragonBallZViewModel by viewModels()

            Ollama_chat_applicationTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.onPrimaryFixedVariant),
                    topBar = {
                        Text(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.onPrimaryFixedVariant)
                                .statusBarsPadding()
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.inversePrimary
                            ),
                            text = "Chat App"
                        )
                    }
                ) { innerPadding ->
                    NavigationStack(innerPadding, viewModel, dragonBallViewModel)
                }
            }
        }
    }
}

@Composable
fun NavigationStack(
    innerPadding: PaddingValues,
    viewModel: ChatViewModel,
    dragonBallZViewModel: DragonBallZViewModel
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when(event){
                UiEvent.NavigationChat -> {
                    navController.navigate("chat")
                }
                UiEvent.NavigationChatHistory -> {
                    navController.navigate("chat_history")
                }
                UiEvent.NavigationDragonBallZ -> {
                    navController.navigate("dragon_ball_z")
                }
                is UiEvent.NavigationDragonBallZDetail -> {
                    navController.navigate("dragon_ball_z_detail/${event.id}")
                }
                UiEvent.StartScreen -> {
                    navController.navigate("start_screen")
                }
            }
        }
    }

    NavHost(navController = navController, startDestination = "start_screen") {
        composable("chat_history") { ChatHistory(innerPadding, viewModel) }
        composable(route = "chat") { ChatScreen(innerPadding, viewModel) }
        composable(route = "start_screen") { StartScreen(innerPadding, viewModel) }
        composable(route = "dragon_ball_z") {
            DragonBallZScreen(
                innerPadding,
                dragonBallZViewModel,
                viewModel,
                navController
            )
        }
        composable(route = "dragon_ball_z_detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            DragonBallZDetailScreen(innerPadding, dragonBallZViewModel, id)
        }
    }
}

fun simpleFlow() = flow {
    emit(1)
    delay(1000.milliseconds)
    emit(2)
    delay(1000.milliseconds)
//    1/0
    emit(0)
    delay(1000.milliseconds)
    emit(4)
}

@Composable
fun StartScreen(innerPadding: PaddingValues, viewModel: ChatViewModel) {

    LaunchedEffect(Unit) {
        simpleFlow()
            .catch { emit(-1) }
//            .map { it / 3f }
            .collect { value ->
                Log.d("Data.TAG", "StartScreen: $value")
            }
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onPrimaryFixedVariant)
            .padding(innerPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to the AI Assistant",
            style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.inversePrimary)
        )
        Button(
            onClick = {
//                navController.navigate("chat")
                viewModel.navigationToChat()
            }
        ) {
            Text(
                "Chat with Llama",
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.inversePrimary)
            )
        }
        Button(
            onClick = {
//                navController.navigate("chat_history")
                viewModel.navigationToChatHistory()
            }
        ) {
            Text(
                "Chat History",
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.inversePrimary)
            )
        }
        Button(
            onClick = {
//                navController.navigate("dragon_ball_z")
                viewModel.navigationToDragonBallZ()
            }
        ) {
            Text(
                "Dragon Ball Z",
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.inversePrimary)
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ollama_chat_applicationTheme {
        Greeting("Android")
    }
}