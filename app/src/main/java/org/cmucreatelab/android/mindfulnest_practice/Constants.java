package org.cmucreatelab.android.mindfulnest_practice;

import org.cmucreatelab.android.mindfulnest_practice.ble.DeviceConnectionHandler;
import org.cmucreatelab.android.mindfulnest_practice.ble.bluetooth_birdbrain.UARTSettings;

import java.util.UUID;

public class Constants {

    public static final String LOG_TAG = "mindfulnest_practice";

    public static final UARTSettings FLOWER_UART_SETTINGS;
    public static final UARTSettings SQUEEZE_UART_SETTINGS;
    public static final UARTSettings WAND_UART_SETTINGS;
    public static final UARTSettings DEFAULT_UART_SETTINGS;

    /** Toggle support of legacy flower protocol (where ble notification returns three comma-separated values, instead of four) */

    public static final boolean SUPPORT_LEGACY_FLOWER_PROTOCOL = true;

    static {
        DEFAULT_UART_SETTINGS = new UARTSettings.Builder()
                .setUARTServiceUUID(UUID.fromString("6E400001-B5A3-F393-E0A9-E50E24DCCA9E"))
                .setRxCharacteristicUUID(UUID.fromString("6E400003-B5A3-F393-E0A9-E50E24DCCA9E"))
                .setTxCharacteristicUUID(UUID.fromString("6E400002-B5A3-F393-E0A9-E50E24DCCA9E"))
                .setRxConfigUUID(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"))
                .build();

        FLOWER_UART_SETTINGS = DEFAULT_UART_SETTINGS;

        SQUEEZE_UART_SETTINGS = DEFAULT_UART_SETTINGS;

        WAND_UART_SETTINGS = DEFAULT_UART_SETTINGS;
    }

}
