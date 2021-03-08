/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    Surface(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()) {
        TimerView()
    }
}

@Composable
fun VerticalDigits(modifier: Modifier = Modifier) {
    val height = 50.dp
    val style = TextStyle(fontSize = 45.sp)
    val heightModifier = Modifier.height(height)
    return BoxWithConstraints(modifier = modifier) {
        Column {
            Text("0", style = style, modifier = heightModifier)
            Text("1", style = style, modifier = heightModifier)
            Text("2", style = style, modifier = heightModifier)
            Text("3", style = style, modifier = heightModifier)
            Text("4", style = style, modifier = heightModifier)
            Text("5", style = style, modifier = heightModifier)
            Text("6", style = style, modifier = heightModifier)
            Text("7", style = style, modifier = heightModifier)
            Text("8", style = style, modifier = heightModifier)
            Text("9", style = style, modifier = heightModifier)
        }
    }
}

@Composable
fun TimerView() {
    val state = remember {
        val state = mutableStateOf(1000)
        val handler = Handler()
        val runnable = object: Runnable {
            override fun run() {
                state.value = state.value - 1
                handler.postDelayed(this, 1000)
            }
        }
        handler.postDelayed(runnable, 1000)
        state
    }

    TimerView(state.value)
}

@Composable
fun TimerView(totalSecs: Int) {
    val hours = totalSecs / 3600
    val hoursOnes = hours % 10
    val hoursTens = hours / 10
    val minutes = (totalSecs % 3600) / 60
    val minutesOnes = minutes % 10
    val minutesTens = minutes / 10
    val seconds = totalSecs % 60
    val secondOnes = seconds % 10
    val secondTens = seconds / 10

    val height = 50.dp
    val heightPixels = LocalDensity.current.run { height.toPx() }

    val secondsOnesTranslation: Float by animateFloatAsState(
        targetValue = verticalDigitTranslation(secondOnes, heightPixels)
    )
    val secondsTensTranslation: Float by animateFloatAsState(
        targetValue = verticalDigitTranslation(secondTens, heightPixels)
    )
    val minutesOnesTranslation: Float by animateFloatAsState(
        targetValue = verticalDigitTranslation(minutesOnes, heightPixels)
    )
    val minutesTensTranslation: Float by animateFloatAsState(
        targetValue = verticalDigitTranslation(minutesTens, heightPixels)
    )
    val hoursTensTranslation: Float by animateFloatAsState(
        targetValue = verticalDigitTranslation(hoursTens, heightPixels)
    )
    val hoursOnesTranslation: Float by animateFloatAsState(
        targetValue = verticalDigitTranslation(hoursOnes, heightPixels)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Hours
        VerticalDigits(modifier = Modifier.graphicsLayer(translationY = hoursTensTranslation))
        VerticalDigits(modifier = Modifier.graphicsLayer(translationY = hoursOnesTranslation))
        Text(":", modifier = Modifier.padding(4.dp))
        // Minutes
        VerticalDigits(modifier = Modifier.graphicsLayer(translationY = minutesTensTranslation))
        VerticalDigits(modifier = Modifier.graphicsLayer(translationY = minutesOnesTranslation))
        Text(":", modifier = Modifier.padding(4.dp))
        // Seconds
        VerticalDigits(modifier = Modifier.graphicsLayer(translationY = secondsTensTranslation))
        VerticalDigits(modifier = Modifier.graphicsLayer(translationY = secondsOnesTranslation))
    }
}


private fun verticalDigitTranslation(centeredOn: Int, heightPixels: Float): Float {
    return when (centeredOn) {
        9 -> 4.5 * (-1 * heightPixels)
        8 -> 3.5 * (-1 * heightPixels)
        7 -> 2.5 * (-1 * heightPixels)
        6 -> 1.5 * (-1 * heightPixels)
        5 -> 0.5 * (-1 * heightPixels)
        4 -> 0.5 * heightPixels
        3 -> 1.5 * heightPixels
        2 -> 2.5 * heightPixels
        1 -> 3.5 * heightPixels
        0 -> 4.5 * heightPixels
        else -> 0.0
    }.toFloat()
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
