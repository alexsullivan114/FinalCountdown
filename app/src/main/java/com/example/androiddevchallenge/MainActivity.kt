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
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.MyTheme

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
fun VerticalDigits(focusedDigit: Int) {
    val height = 50.dp
    val heightPixels = LocalDensity.current.run { height.toPx() }
    val translation: Float by animateFloatAsState(
        targetValue = verticalDigitTranslation(focusedDigit, heightPixels)
    )
    val heightModifier = Modifier.height(height)
    fun getStyle(digit: String): TextStyle {
        val style = TextStyle(fontSize = 45.sp, color = Color.LightGray)
        return if (focusedDigit.toString() == digit) {
            style.copy(color = Color.Red)
        } else {
            style
        }
    }
    return Column(modifier = Modifier.graphicsLayer(translationY = translation)) {
        Text("0", style = getStyle("0"), modifier = heightModifier)
        Text("1", style = getStyle("1"), modifier = heightModifier)
        Text("2", style = getStyle("2"), modifier = heightModifier)
        Text("3", style = getStyle("3"), modifier = heightModifier)
        Text("4", style = getStyle("4"), modifier = heightModifier)
        Text("5", style = getStyle("5"), modifier = heightModifier)
        Text("6", style = getStyle("6"), modifier = heightModifier)
        Text("7", style = getStyle("7"), modifier = heightModifier)
        Text("8", style = getStyle("8"), modifier = heightModifier)
        Text("9", style = getStyle("9"), modifier = heightModifier)
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

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Hours
        VerticalDigits(hoursTens)
        VerticalDigits(hoursOnes)
        Text(":", modifier = Modifier.padding(4.dp))
        // Minutes
        VerticalDigits(minutesTens)
        VerticalDigits(minutesOnes)
        Text(":", modifier = Modifier.padding(4.dp))
        // Seconds
        VerticalDigits(secondTens)
        VerticalDigits(secondOnes)
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