package org.cmucreatelab.android.mindfulnest_practice

import android.content.res.Configuration
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.cmucreatelab.android.mindfulnest_practice.ble.flower.BleFlower
import org.cmucreatelab.android.mindfulnest_practice.ble.flower.BleFlower.NotificationCallback
import org.cmucreatelab.android.mindfulnest_practice.ble.squeeze.BleSqueeze
import org.cmucreatelab.android.mindfulnest_practice.ble.wand.BleWand
import org.cmucreatelab.android.mindfulnest_practice.ui.theme.MindfulNestPracticeTheme


// Activity class definition

class TripleViewLayout2Activity : ConditionalLayoutActivity() {

    var isFlowerConnected = true
    var isSqueezeConnected = true
    var isWandConnected = true

    var isBreathing = false
    var isSqueezing = false
    var isWaving = false

    val MAGNITUDE_FOR_WAVING = 999


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InitScreen()
        }

        val globalHandler = GlobalHandler.getInstance(applicationContext)
        if (globalHandler.bleFlower != null && globalHandler.bleFlower.isConnected) {
            globalHandler.bleFlower.notificationCallback = object : BleFlower.NotificationCallback {
                override fun onReceivedData(
                    arg1: String,
                    arg2: String,
                    arg3: String,
                    arg4: String,
                    arg5: String
                ) {
                    //Log.v(Constants.LOG_TAG, String.format("FLOWER got values: %s,  %s,  %s,  %s,  %s", arg1, arg2, arg3, arg4, arg5))
                    if (arg4.equals("1")) {
                        // breath detected
                        isBreathing = true;
                    } else {
                        isBreathing = false;
                    }
                    // update views
                    setContent {
                        InitScreen()
                    }
                }
            }
        }
        if (globalHandler.bleSqueeze != null && globalHandler.bleSqueeze.isConnected) {
            globalHandler.bleSqueeze.notificationCallback = object : BleSqueeze.NotificationCallback {
                override fun onReceivedData(
                    arg1: String
                ) {
                    if (arg1.equals("1")) {
                        isSqueezing = true;
                    } else {
                        isSqueezing = false;
                    }
                    // update views
                    setContent {
                        InitScreen()
                    }
                }
            }
        }
        if (globalHandler.bleWand != null && globalHandler.bleWand.isConnected) {
            globalHandler.bleWand.notificationCallback = object : BleWand.NotificationCallback {
                override fun onReceivedData(
                    button: String,
                    x: String,
                    y: String,
                    z: String
                ) {
                    // TODO convert
                    val gx = Integer.parseInt(x)
                    val gy = Integer.parseInt(y)
                    val gz = Integer.parseInt(z)
                    val gg = gx*gx + gy*gy + gz*gz;
                    Log.v(Constants.LOG_TAG, String.format("WAND got values: %d <- (%s,  %s,  %s)", gg, x, y, z))
                    if (gg > MAGNITUDE_FOR_WAVING) {
                        isWaving = true;
                    } else {
                        isWaving = false;
                    }
                    // update views
                    setContent {
                        InitScreen()
                    }
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()

        // NOTE: avoid using UI thread for ble scans/connection
        AsyncTask.execute {
            val globalHandler = GlobalHandler.getInstance(applicationContext)
//            isFlowerConnected = globalHandler.bleFlower.isConnected
//            isSqueezeConnected = globalHandler.bleSqueeze.isConnected
//            isWandConnected = globalHandler.bleWand.isConnected
            isFlowerConnected = if (globalHandler.bleFlower != null) globalHandler.bleFlower.isConnected else false
            isSqueezeConnected = if (globalHandler.bleSqueeze != null) globalHandler.bleSqueeze.isConnected else false
            isWandConnected = if (globalHandler.bleWand != null) globalHandler.bleWand.isConnected else false
        }

        setContent {
            InitScreen()
        }
    }


    // Composable (UI) functions

    @Composable
    override fun ScreenLandscape(textPadding: Dp, innerPadding: PaddingValues) {
        Row(
            modifier = Modifier.padding(innerPadding),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (isFlowerConnected) TViewGreeting(
                isActive = isBreathing,
                name = "🌼",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            if (isSqueezeConnected) TViewGreeting(
                isActive = isSqueezing,
                name = "🐏",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            if (isWandConnected) TViewGreeting(
                isActive = isWaving,
                name = "🪄",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
        }
    }

    @Composable
    override fun ScreenPortrait(textPadding: Dp, innerPadding: PaddingValues) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            if (isFlowerConnected) TViewGreeting(
                isActive = isBreathing,
                name = "🌼",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            if (isSqueezeConnected) TViewGreeting(
                isActive = isSqueezing,
                name = "🐏",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            if (isWandConnected) TViewGreeting(
                isActive = isWaving,
                name = "🪄",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
        }
    }

    // TODO refactor (only needed override/copy to omit button)
    @Composable
    override fun InitScreen() {
        val configuration = LocalConfiguration.current
        MindfulNestPracticeTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Scaffold(modifier = Modifier.padding(innerPadding)) { innerPadding ->
                    val textPadding = 10.dp
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
//                        Button(
//                            onClick = { onClickButton() },
//                            modifier = Modifier.align(Alignment.BottomEnd)
//                        ) {
//                            Text("Begin")
//                        }
                    }
                    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        ScreenPortrait(textPadding, innerPadding)
                    } else {
                        // Horizontal layout in landscape mode
                        ScreenLandscape(textPadding, innerPadding)
                    }
                }
            }
        }
    }

    @Composable
    fun TViewGreeting(isActive: Boolean, name: String, modifier: Modifier = Modifier) {
        //val backgroundColor = (if (isActive) Color.Green else Color.Red)
        val backgroundColor = Color.LightGray
        val displayText = (if (isActive) "$name✅" else "❌$name");
        Text(
            text = displayText,
            modifier = modifier
                .background(backgroundColor)
                .fillMaxWidth()
                .fillMaxHeight(),
            textAlign = TextAlign.Center,
            fontSize = 48.sp
        )
    }

    @Preview(name = "Landscape Mode", showBackground = true, widthDp = 640, heightDp = 360)
    @Preview(name = "Portrait Mode", showBackground = true)
    @Composable
    override fun GreetingPreview() {
        InitScreen()
    }

}
