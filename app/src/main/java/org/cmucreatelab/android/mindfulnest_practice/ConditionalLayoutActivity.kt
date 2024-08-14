package org.cmucreatelab.android.mindfulnest_practice

import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.AsyncTask
import android.os.Build
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
import org.cmucreatelab.android.mindfulnest_practice.ble.bluetooth_birdbrain.UARTConnection
import org.cmucreatelab.android.mindfulnest_practice.ble.flower.BleFlowerScanner
import org.cmucreatelab.android.mindfulnest_practice.ui.theme.MindfulNestPracticeTheme


// Activity class definition

class ConditionalLayoutActivity : AbstractActivity() {


    private val requiredPermissions = arrayOf(
        android.Manifest.permission.BLUETOOTH_SCAN,
        android.Manifest.permission.BLUETOOTH_CONNECT,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requestPermissions(requiredPermissions, REQUEST_CODE_BLUETOOTH_PERMISSIONS)
        } else {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_BLUETOOTH_PERMISSIONS)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_BLUETOOTH_PERMISSIONS) {
            // Handle the result of the permission request
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                // All permissions granted, you can proceed with BLE scan
            } else {
                // Handle the case where permissions were not granted
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_BLUETOOTH_PERMISSIONS = 1001
    }


    private fun startBleScan() {
        Log.v(Constants.LOG_TAG, "startBleScan")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (checkSelfPermission(android.Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(android.Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                // Permissions are granted, start BLE scan
                bleFlowerScanner.requestScan()
            } else {
                requestPermissions()
            }
        } else {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, start BLE scan
                bleFlowerScanner.requestScan()
            } else {
                requestPermissions()
            }
        }
    }

    // ...

    lateinit var bleFlowerScanner: BleFlowerScanner
    var view1 = "View 1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InitScreen()
        }

        bleFlowerScanner = BleFlowerScanner(this,
            BleFlowerScanner.DiscoveryListener { bleFlower ->
                Log.v(Constants.LOG_TAG, "discovered flower" + bleFlower.deviceName);
            },
            object : UARTConnection.ConnectionListener {
                override fun onConnected() {
                    //TODO("Not yet implemented")
                    Log.v(Constants.LOG_TAG, "onConnected");
                }

                override fun onDisconnected() {
                    //TODO("Not yet implemented")
                    Log.v(Constants.LOG_TAG, "onDisconnected");
                }
        })
    }

    override fun onResume() {
        super.onResume()

        // NOTE: avoid using UI thread for ble scans/connection
        AsyncTask.execute {
            val globalHandler = GlobalHandler.getInstance(applicationContext)
            if (bleFlowerScanner.isFlowerDiscovered) {
                //updateFlower(globalHandler.bleFlower)
                // TODO get object?
            } else {
                //bleFlowerScanner.requestScan()
                startBleScan()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        bleFlowerScanner.stopScan()
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
