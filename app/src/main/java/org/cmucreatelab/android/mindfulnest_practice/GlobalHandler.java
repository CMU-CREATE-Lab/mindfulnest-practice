package org.cmucreatelab.android.mindfulnest_practice;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.cmucreatelab.android.mindfulnest_practice.ble.DeviceConnectionHandler;
import org.cmucreatelab.android.mindfulnest_practice.ble.bluetooth_birdbrain.UARTConnection;
import org.cmucreatelab.android.mindfulnest_practice.ble.flower.BleFlower;
import org.cmucreatelab.android.mindfulnest_practice.ble.squeeze.BleSqueeze;
import org.cmucreatelab.android.mindfulnest_practice.ble.wand.BleWand;

/**
 *
 * A class that provides access across activities.
 *
 */
public class GlobalHandler {

    public final Context appContext;
    public BleFlower bleFlower;
    public BleSqueeze bleSqueeze;
    public BleWand bleWand;
    public final DeviceConnectionHandler deviceConnectionHandler;

    public boolean isTeacherMode = false, isRunningActivityForImageResult = false;


    private GlobalHandler(Context context) {
        this.appContext = context;
        this.deviceConnectionHandler = new DeviceConnectionHandler();
    }


    // Singleton Implementation


    private static GlobalHandler classInstance;


    public static synchronized GlobalHandler getInstance(Context context) {
        if (classInstance == null) {
            classInstance = new GlobalHandler(context);
        }
        return classInstance;
    }


    // BLE methods


    public boolean isFlowerConnected() {
        return (bleFlower != null && bleFlower.isConnected());
    }

    public boolean isWandConnected() {
        return (bleWand != null && bleWand.isConnected());
    }

    public boolean isSqueezeConnected() {
        return (bleSqueeze != null && bleSqueeze.isConnected());
    }


    /**
     * start a new BLE connection with a BLE device
     * @param classToValidate The type of device we are connecting to (example: {@link BleFlower}).
     * @param bluetoothDevice should be one of MindfulNest BLE devices.
     * @param connectionListener Listen for connection state changes.
     */
    public synchronized void startConnection(Class classToValidate, BluetoothDevice bluetoothDevice, UARTConnection.ConnectionListener connectionListener) {
        if (classToValidate == BleFlower.class) {
            if (bleFlower != null) {
                Log.w(Constants.LOG_TAG, "current bleFlower in GlobalHandler is not null; attempting to close.");
                try {
                    bleFlower.disconnect();
                } catch (Exception e) {
                    Log.e(Constants.LOG_TAG, "Exception caught in GlobalHandler.startConnection (likely null reference in UARTConnection); Exception message was: ``" + e.getMessage() + "''");
                }
            }
            this.bleFlower = new BleFlower(appContext, bluetoothDevice, connectionListener);
        }
        if (classToValidate == BleSqueeze.class) {
            if (bleSqueeze != null) {
                Log.w(Constants.LOG_TAG, "current bleSqueeze in GlobalHandler is not null; attempting to close.");
                try {
                    bleSqueeze.disconnect();
                } catch (Exception e) {
                    Log.e(Constants.LOG_TAG, "Exception caught in GlobalHandler.startConnection (likely null reference in UARTConnection); Exception message was: ``" + e.getMessage() + "''");
                }
            }
            Log.w(Constants.LOG_TAG, "Trying to create new bleSqueeze");
            this.bleSqueeze = new BleSqueeze(appContext, bluetoothDevice, connectionListener);
            Log.w(Constants.LOG_TAG, "Created new bleSqueeze");
        }
        if (classToValidate == BleWand.class) {
            if (bleWand != null) {
                Log.w(Constants.LOG_TAG, "current bleWand in GlobalHandler is not null; attempting to close.");
                try {
                    bleWand.disconnect();
                } catch (Exception e) {
                    Log.e(Constants.LOG_TAG, "Exception caught in GlobalHandler.startConnection (likely null reference in UARTConnection); Exception message was: ``" + e.getMessage() + "''");
                }
            }
            Log.w(Constants.LOG_TAG, "Trying to create new BleWand");
            this.bleWand = new BleWand(appContext, bluetoothDevice, connectionListener);
            Log.w(Constants.LOG_TAG, "Created new BleWand");
        }
    }

}
