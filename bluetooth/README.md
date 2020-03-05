**Contents of**  **bluetooth**  **Module:**

This module is responsible for sending and receiving data between the android application and the other hardware components (cane and glasses)

- BluetoothSensorPortal
- SensorData

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**Purpose of Each File:**

1. BluetoothSensorPortal

This file is an implementation of &quot;SensorPortal&quot; interface, it takes the MAC address of the server(HC-05 bluetooth module) and starts a connection between this module and the client (android device) through &quot;connect&quot; function which creates RFCOMM socket after providing it with serial UUID.

1. SensorData

This file is an implementation of &quot;Sensor&quot; interface, it manages the connection through 4 functions:

-&quot;Readings&quot;: reads the incoming stream of data from bluetooth module, buffers each 1024 bytes, maps them to another format and converts them to a flow.

-&quot;Send&quot;: sends the incoming data from CV and OD modules to bluetooth module.

-&quot;isActive&quot;: gets the status of the socket whether it is connected or not.

&quot;Shutdown&quot;: closes the connection by closing the socket.
