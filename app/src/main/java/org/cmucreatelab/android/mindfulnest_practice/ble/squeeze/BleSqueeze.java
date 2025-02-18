package org.cmucreatelab.android.mindfulnest_practice.ble.squeeze;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import org.cmucreatelab.android.mindfulnest_practice.Constants;
import org.cmucreatelab.android.mindfulnest_practice.ble.bluetooth_birdbrain.UARTConnection;

public class BleSqueeze {

    public interface NotificationCallback {
        void onReceivedData(@NonNull String arg1);
    }

    private UARTConnection uartConnection;

    public NotificationCallback notificationCallback = null;


    public BleSqueeze(Context appContext, BluetoothDevice device, UARTConnection.ConnectionListener connectionListener) {
        this.uartConnection = new UARTConnection(appContext, device, Constants.SQUEEZE_UART_SETTINGS, connectionListener);
        uartConnection.addRxDataListener(new UARTConnection.RXDataListener() {
            @Override
            public void onRXData(byte[] newData) {
                Log.d(Constants.LOG_TAG, "newData='" + new String(newData).trim() + "'");
                if (notificationCallback != null) {
                    String param = new String(newData).trim();
                    if (param.isEmpty()) {
                        Log.e(Constants.LOG_TAG, "parsed empty parameter from notification='"+new String(newData).trim()+"'; unable to call NotificationCallback.");
                        return;
                    }
                    // remove any non-numerical characters in the message (leading a-z printed on some notifications)
                    param = param.replaceAll("[^0-9]","");
                    // remove first character in string (firmware prepends 0-9 on packet to avoid sending identical BLE messages)
                    param = param.substring(1);
                    notificationCallback.onReceivedData(param);
                }
            }
        });
    }


    public boolean isConnected() {
        return uartConnection.isConnected();
    }


    public String getDeviceName() {
        BluetoothDevice bluetoothDevice = uartConnection.getBLEDevice();
        if (bluetoothDevice == null) {
            Log.w(Constants.LOG_TAG, "getDeviceName with null bluetooth device");
            return null;
        } else {
            return bluetoothDevice.getName();
        }
    }


    public void disconnect() {
        this.uartConnection.disconnect();
    }


    public void writeData(byte[] bytes) {
        if(bytes != null) {
            boolean wrote = this.uartConnection.writeBytes(bytes);
            if (!wrote) {
                Log.w(Constants.LOG_TAG, "Value: " + bytes[0] + " was not written");
            }
        }
    }

}