package org.cmucreatelab.android.mindfulnest_practice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp


// Activity class definition

class TripleViewLayoutActivity : ConditionalLayoutActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InitScreen()
        }
    }

    override fun onClickButton() {
        Log.v(Constants.LOG_TAG, "onClickButton")
        //val sendIntent = Intent(this, TripleViewLayout2Activity::class.java)
        val sendIntent = Intent(this, MainActivity::class.java)
        //sendIntent.putExtra("foo", "bar")
        startActivity(sendIntent)
    }


    // Composable (UI) functions

    @Composable
    override fun ScreenLandscape(textPadding: Dp, innerPadding: PaddingValues) {
        Row(
            modifier = Modifier.padding(innerPadding),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TViewGreeting(
                isActive = false,
                name = "🌼",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            TViewGreeting(
                isActive = true,
                name = "🐏",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            TViewGreeting(
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
            TViewGreeting(
                isActive = false,
                name = "🌼",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            TViewGreeting(
                isActive = true,
                name = "🐏",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            TViewGreeting(
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
        val backgroundColor = (if (isActive) Color.Green else Color.Red)
        val displayText = (if (isActive) "$name✅" else "❌$name");
        Text(
            text = displayText,
            modifier = modifier
                .background(backgroundColor)
                .fillMaxWidth()
                .fillMaxHeight(),
            textAlign = TextAlign.Center
        )
    }

    @Preview(name = "Landscape Mode", showBackground = true, widthDp = 640, heightDp = 360)
    @Preview(name = "Portrait Mode", showBackground = true)
    @Composable
    override fun GreetingPreview() {
        InitScreen()
    }

}
