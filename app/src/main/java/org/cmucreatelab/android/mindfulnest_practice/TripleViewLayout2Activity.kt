package org.cmucreatelab.android.mindfulnest_practice

import android.os.AsyncTask
import android.os.Bundle
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp


// Activity class definition

class TripleViewLayout2Activity : ConditionalLayoutActivity() {

    var isFlowerConnected = true
    var isSqueezeConnected = true
    var isWandConnected = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InitScreen()
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
                isActive = true,
                name = "🌼",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            if (isSqueezeConnected) TViewGreeting(
                isActive = true,
                name = "🐏",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            if (isWandConnected) TViewGreeting(
                isActive = true,
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
                isActive = true,
                name = "🌼",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            if (isSqueezeConnected) TViewGreeting(
                isActive = true,
                name = "🐏",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            if (isWandConnected) TViewGreeting(
                isActive = true,
                name = "🪄",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
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
