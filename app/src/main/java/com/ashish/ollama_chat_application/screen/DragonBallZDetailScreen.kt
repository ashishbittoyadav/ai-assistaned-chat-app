package com.ashish.ollama_chat_application.screen

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ashish.ollama_chat_application.view_model.DragonBallZViewModel
import android.graphics.Color as AndroidColor


@Composable
fun DragonBallZDetailScreen(
    innerPadding: PaddingValues,
    dragonBallZViewModel: DragonBallZViewModel,
    id: String?
) {
    val state = dragonBallZViewModel.selectedCharacter.collectAsState()

    val painter = rememberAsyncImagePainter(
        model = state.value?.image
    )

    val bitmapState = painter.state

    var dominantColor by remember { mutableStateOf(Color.Gray) }
    var invertDominantColor by remember { mutableStateOf(Color.Gray) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
            .background(
                brush = Brush.radialGradient(
                    radius = 1400f,
                    colors = listOf(dominantColor,invertDominantColor)
                )
            )
        ,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(state.value?.image)
                    .allowHardware(false)
                    .build(),
                contentDescription = state.value?.name,
                onSuccess = { success ->
                    val bitmap = (success.result.drawable as? BitmapDrawable)?.bitmap

                    bitmap?.let {
                        val palette = Palette.from(it).generate()

                        val colorInt = palette.getDominantColor(android.graphics.Color.GRAY)
                        dominantColor = Color(colorInt)
                        invertDominantColor = oppositeColor(Color(colorInt))
                    }
                }
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly
        ) {
            SetTitleAndValue(title = "Name", value = state.value?.name ?: "",dominantColor,invertDominantColor)
            Spacer(modifier = Modifier.width(16.dp))
            SetTitleAndValue(
                title = "Gender",
                value = state.value?.gender ?: "",
                dominantColor = dominantColor,
                invertDominantColor = invertDominantColor
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly
        ) {
            SetTitleAndValue(
                title = "Race",
                value = state.value?.race ?: "",
                dominantColor = dominantColor,
                invertDominantColor = invertDominantColor
            )
            Spacer(modifier = Modifier.width(16.dp))
            SetTitleAndValue(
                title = "Affiliation",
                value = state.value?.affiliation ?: "",
                dominantColor = dominantColor,
                invertDominantColor = invertDominantColor
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            text = state.value?.description ?: "",
            style = MaterialTheme.typography.bodyLarge.copy(color = changeHue(dominantColor,260f))
        )
    }
}

@Composable
fun SetTitleAndValue(title: String, value: String, dominantColor: Color, invertDominantColor: Color) {
    Row {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = changeHue(dominantColor,130f))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = value, style = MaterialTheme.typography.bodyLarge.copy(color = changeHue(dominantColor,260f)))
    }
}

fun oppositeColor(color: Color): Color {
    return Color(
        red = 1f - color.red,
        green = 1f - color.green,
        blue = 1f - color.blue,
        alpha = color.alpha
    )
}

fun changeHue(color: Color, degree: Float): Color {
    val hsv = FloatArray(3)

    AndroidColor.colorToHSV(color.toArgb(), hsv)

    hsv[0] = (hsv[0] + degree) % 360f  // 🔄 shift hue

    return Color(AndroidColor.HSVToColor(hsv))
}

