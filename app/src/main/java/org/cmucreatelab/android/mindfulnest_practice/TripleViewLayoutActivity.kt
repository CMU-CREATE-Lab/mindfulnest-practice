package org.cmucreatelab.android.mindfulnest_practice

import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
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
import org.cmucreatelab.android.mindfulnest_practice.ble.bluetooth_birdbrain.UARTConnection
import org.cmucreatelab.android.mindfulnest_practice.ble.flower.BleFlowerScanner
import org.cmucreatelab.android.mindfulnest_practice.ble.squeeze.BleSqueezeScanner
import org.cmucreatelab.android.mindfulnest_practice.ble.wand.BleWandScanner


// Activity class definition

class TripleViewLayoutActivity : ConditionalLayoutActivity() {

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
                bleSqueezeScanner.requestScan()
                bleWandScanner.requestScan()
            } else {
                requestPermissions()
            }
        } else {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, start BLE scan
                bleFlowerScanner.requestScan()
                bleSqueezeScanner.requestScan()
                bleWandScanner.requestScan()
            } else {
                requestPermissions()
            }
        }
    }

    // ...

    lateinit var bleFlowerScanner: BleFlowerScanner
    lateinit var bleSqueezeScanner: BleSqueezeScanner
    lateinit var bleWandScanner: BleWandScanner

    fun updateViews() {
        setContent {
            InitScreen()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bleFlowerScanner = BleFlowerScanner(this,
            BleFlowerScanner.DiscoveryListener { bleFlower ->
                Log.v(Constants.LOG_TAG, "discovered flower" + bleFlower.deviceName);
            },
            object : UARTConnection.ConnectionListener {
                override fun onConnected() {
                    //TODO("Not yet implemented")
                    Log.v(Constants.LOG_TAG, "onConnected");
                    updateViews()
                }

                override fun onDisconnected() {
                    //TODO("Not yet implemented")
                    Log.v(Constants.LOG_TAG, "onDisconnected");
                    updateViews()
                }
            }
        )
        bleSqueezeScanner = BleSqueezeScanner(this,
            BleSqueezeScanner.DiscoveryListener { bleSqueeze ->
                Log.v(Constants.LOG_TAG, "discovered squeeze" + bleSqueeze.deviceName);
            },
            object : UARTConnection.ConnectionListener {
                override fun onConnected() {
                    //TODO("Not yet implemented")
                    Log.v(Constants.LOG_TAG, "onConnected (squeeze)");
                    updateViews()
                }

                override fun onDisconnected() {
                    //TODO("Not yet implemented")
                    Log.v(Constants.LOG_TAG, "onDisconnected (squeeze)");
                    updateViews()
                }
            }
        )
        bleWandScanner = BleWandScanner(this,
            BleWandScanner.DiscoveryListener { bleWand ->
                Log.v(Constants.LOG_TAG, "discovered wand" + bleWand.deviceName);
            },
            object : UARTConnection.ConnectionListener {
                override fun onConnected() {
                    //TODO("Not yet implemented")
                    Log.v(Constants.LOG_TAG, "onConnected (wand)");
                    updateViews()
                }

                override fun onDisconnected() {
                    //TODO("Not yet implemented")
                    Log.v(Constants.LOG_TAG, "onDisconnected (wand)");
                    updateViews()
                }
            }
        )

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
            // TODO check all
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
        bleSqueezeScanner.stopScan()
        bleWandScanner.stopScan()
    }

    // ...

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            InitScreen()
//        }
//    }

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
                isActive = bleFlowerScanner.isFlowerConnected,
                name = "🌼",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            TViewGreeting(
                isActive = bleSqueezeScanner.isSqueezeConnected,
                name = "🐏",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            TViewGreeting(
                isActive = bleWandScanner.isWandConnected,
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
                isActive = bleFlowerScanner.isFlowerConnected,
                name = "🌼",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            TViewGreeting(
                isActive = bleSqueezeScanner.isSqueezeConnected,
                name = "🐏",
                modifier = Modifier
                    .padding(textPadding)
                    .weight(1f)
            )
            TViewGreeting(
                isActive = bleWandScanner.isWandConnected,
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
