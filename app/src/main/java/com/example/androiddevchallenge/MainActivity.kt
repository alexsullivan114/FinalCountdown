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
import androidx.compose.foundation.layout.*
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.ui.theme.MyTheme
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
fun TimerView() {
    val state = remember {
        val state = mutableStateOf(10)
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
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val hours = totalSecs / 3600;
        val minutes = (totalSecs % 3600) / 60;
        val seconds = totalSecs % 60;
        if (hours <= 0 && minutes <= 0 && seconds < 0) {
            Text("Donezo")
        } else {
            val hoursString = String.format("%02d", hours)
            val minutesString = String.format("%02d", minutes)
            val secondsString = String.format("%02d", seconds)
            Text("$hoursString:$minutesString:$secondsString")
        }
    }
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
