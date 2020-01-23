package com.caneseeproject.bluetooth
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter



class discover(private val context : Context)  {
    private var discoveredDevices:MutableList<BluetoothDevice> = mutableListOf()
    val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)

        val receiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                val action: String = intent.action
                when(action) {
                    BluetoothDevice.ACTION_FOUND -> {
                        // Discovery has found a device. Get the BluetoothDevice
                        // object and its info from the Intent.
                        val device: BluetoothDevice =
                            intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        val deviceName = device.name
                        val deviceHardwareAddress = device.address // MAC address
                        addDiscoveredDevice(device)

                    }
                }
            }
        }
    fun registerreceiver(){
        context.registerReceiver(receiver, filter)

    }
    fun unregisterreceiver(){
        context.unregisterReceiver(this.receiver)
    }
    private fun addDiscoveredDevice(device: BluetoothDevice) {
        if (device.bondState != BluetoothDevice.BOND_BONDED)
            return

        for (device in discoveredDevices) {
            if (device.address.equals(device.address))
                return
        }
        discoveredDevices.add(device)
    }
    fun found(){

        discoveredDevices = mutableListOf()
        if (bluetoothAdapter?.isDiscovering!!)
            bluetoothAdapter?.cancelDiscovery()
        bluetoothAdapter?.startDiscovery()
        /*for (device in discoveredDevices)
            if (mydevice.equals(device.address))
                mydevice.name.length.toString()*/

    }



}