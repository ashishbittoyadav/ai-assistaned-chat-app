package com.ashish.ollama_chat_application.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ashish.ollama_chat_application.uiState.DragonBallUiState
import com.ashish.ollama_chat_application.view_model.DragonBallZViewModel

@Composable
fun DragonBallZScreen(
    innerPadding: PaddingValues,
    viewModel: DragonBallZViewModel,
    navController: NavHostController
) {

    LaunchedEffect(Unit) {
        viewModel.getDragonBallZCharacters()
    }

    val uiState by viewModel.dragonBallUiState.collectAsState()

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onPrimaryFixedVariant)
            .padding(innerPadding)
            .fillMaxSize(),
        horizontalAlignment = if (uiState == DragonBallUiState.Loading) {
            Alignment.CenterHorizontally
        } else {
            Alignment.Start
        },
        verticalArrangement = if (uiState == DragonBallUiState.Loading) {
            Arrangement.Center
        } else {
            Arrangement.Top
        }
    ) {
        when (uiState) {
            is DragonBallUiState.Loading -> {
                CircularProgressIndicator()
            }

            is DragonBallUiState.Characters -> {
                val data = (uiState as DragonBallUiState.Characters).characters
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    items(items = data.items, key = { item -> item.id }) { item ->
                        val expand = remember { mutableStateOf(false) }
                        Card(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxSize()
                                .clickable {
                                    viewModel.selectCharacter(item.id)
                                    navController.navigate("dragon_ball_z_detail/${item.id}")
                                },
                        ) {
                            Row {
                                AsyncImage(
                                    modifier = Modifier.size(100.dp),
                                    model = item.image,
                                    contentDescription = item.name
                                )
                                Column(
                                    modifier = Modifier
                                        .height(height = 100.dp)
                                        .fillMaxWidth(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        item.name,
                                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        item.affiliation,
                                        modifier = Modifier.clickable {
                                            expand.value = !expand.value
                                        },
                                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                        maxLines = if (!expand.value) {
                                            1
                                        } else {
                                            Int.MAX_VALUE
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            is DragonBallUiState.Error -> {
                Text("Error occurred")
            }
        }
    }

}