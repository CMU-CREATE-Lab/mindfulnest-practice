package org.cmucreatelab.android.mindfulnest_practice

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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


    // Composable (UI) functions

    @Composable
    override fun ScreenLandscape(textPadding: Dp, innerPadding: PaddingValues) {
        Row(
            modifier = Modifier.padding(innerPadding),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Greeting(
                name = view1,
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            Greeting(
                name = "View 2",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            Greeting(
                name = "View 3",
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
            Greeting(
                name = view1,
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            Greeting(
                name = "View 2",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            Greeting(
                name = "View 3",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
        }
    }

    @Preview(name = "Landscape Mode", showBackground = true, widthDp = 640, heightDp = 360)
    @Preview(name = "Portrait Mode", showBackground = true)
    @Composable
    override fun GreetingPreview() {
        InitScreen()
    }

}
