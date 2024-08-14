package org.cmucreatelab.android.mindfulnest_practice

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.cmucreatelab.android.mindfulnest_practice.ui.theme.MindfulNestPracticeTheme


// Activity class definition

class MainActivity : AbstractActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InitScreen()
        }
    }
}


// Composable (UI) functions

@Composable
fun InitScreen() {
    MindfulNestPracticeTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val textPadding = 10.dp
            Row(
                // NOTE (from IDE)
                // "The content lambda in Scaffold has a padding parameter which will include any
                // inner padding for the content due to app bars. If this parameter is ignored,
                // then content may be obscured by the app bars resulting in visual issues or
                // elements that can't be interacted with."
                modifier = Modifier.padding(innerPadding)
            ) {
                Greeting(
                    name = "Android",
                    modifier = Modifier.padding(textPadding)
                )
                Greeting(
                    name = "Google",
                    modifier = Modifier.padding(textPadding)
                )
                Greeting(
                    name = "Foo",
                    modifier = Modifier.padding(textPadding)
                )
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InitScreen()
}