package com.ashish.ollama_chat_application

import android.os.Bundle
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ashish.ollama_chat_application.screen.ChatHistory
import com.ashish.ollama_chat_application.screen.ChatScreen
import com.ashish.ollama_chat_application.ui.theme.Ollama_chat_applicationTheme
import com.ashish.ollama_chat_application.view_model.ChatViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel : ChatViewModel = viewModel()

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
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.inversePrimary),
                            text = "Chat App"
                        )
                    }
                ) { innerPadding ->
                    NavigationStack(innerPadding,viewModel)
                }
            }
        }
    }
}

@Composable
fun NavigationStack(innerPadding: PaddingValues, viewModel: ChatViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "start_screen") {
        composable("chat_history") { ChatHistory(innerPadding,viewModel) }
        composable(route = "chat") { ChatScreen(innerPadding,viewModel) }
        composable(route = "start_screen") { StartScreen(innerPadding,navController) }
    }
}

@Composable
fun StartScreen(innerPadding: PaddingValues, navController: NavController) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onPrimaryFixedVariant)
            .padding(innerPadding)
            .fillMaxSize()
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to the AI Assistant",
            style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.inversePrimary)
        )
        Button(
            onClick = {
                navController.navigate("chat")
            }
        ) {
            Text("Chat with Llama",
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.inversePrimary)
            )
        }
        Button(
            onClick = {
                navController.navigate("chat_history")
            }
        ) {
            Text("Chat History",
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.inversePrimary))
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