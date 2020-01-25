package com.caneseeproject.bluetooth
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.widget.Toast



class discover(val context: Context)  {
    private var discoveredDevices:MutableList<BluetoothDevice> = mutableListOf()
    val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    val mydevice : String = "98:D3:61:FD:66:FB"
    //val mydevice : String = "54:27:58:C2:18:CF"

    var MYdevice = bluetoothAdapter!!.getRemoteDevice(mydevice)
    val serviceprovider = serviceProvider.ConnectionThreed(MYdevice)

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
                       // Toast.makeText(context, device.uuids.toString(), Toast.LENGTH_LONG).show()

                        if(MYdevice.address.equals(device.address)){
                            Toast.makeText(context, "EQQQQUUUAAAALLLLSSS", Toast.LENGTH_LONG).show()
                            //serviceprovider.run()

                        }

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
        if (device.bondState != BluetoothDevice.BOND_BONDED) {
            return
        }

        for (device in discoveredDevices) {
            if (device.address.equals(device.address))
                return
        }

        discoveredDevices.add(device)
    }
    fun search(){

        discoveredDevices = mutableListOf()
        if (bluetoothAdapter!!.isDiscovering!!)
            bluetoothAdapter.cancelDiscovery()
        bluetoothAdapter.startDiscovery()

    }
    fun endBluetooth(){
        if (bluetoothAdapter?.isEnabled == true) {
            bluetoothAdapter.disable()
            Log.i("MyActivity", "OOOOOOFFFFFFFF")
            serviceprovider.cancel()


        }
    }
    fun startBluetooth(){
        if (bluetoothAdapter?.isEnabled == false) {
            bluetoothAdapter.enable()
            Log.i("MyActivity", "ooooooonnnnnnnnnnn")

        }
    }
}