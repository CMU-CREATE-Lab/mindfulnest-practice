package org.cmucreatelab.android.mindfulnest_practice

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.cmucreatelab.android.mindfulnest_practice.ui.theme.MindfulNestPracticeTheme


// Activity class definition

class ConditionalLayoutActivity : AbstractActivity() {

    var view1 = "View 1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InitScreen()
        }
    }


    fun onClickButton() {
        Log.v(Constants.LOG_TAG, "onClickButton")
        view1 = "view1"
        setContent {
            InitScreen()
        }
    }


    // Composable (UI) functions

    @Composable
    fun InitScreen() {
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
                        Button(
                            onClick = { onClickButton() },
                            modifier = Modifier.align(Alignment.BottomEnd)
                        ) {
                            Text("Begin")
                        }
                    }
                    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
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
                    } else {
                        // Horizontal layout in landscape mode
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
                }
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

    @Preview(name = "Landscape Mode", showBackground = true, widthDp = 640, heightDp = 360)
    @Preview(name = "Portrait Mode", showBackground = true)
    @Composable
    fun GreetingPreview() {
        InitScreen()
    }

}
