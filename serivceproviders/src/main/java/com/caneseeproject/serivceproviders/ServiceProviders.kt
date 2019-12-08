package com.caneseeproject.serivceproviders


/**
 * Abstract Service provider, meant to be implemented by actual services,
 * whether they are remote or local.
 *@author mhashim6 on 2019-12-08
 */
interface ServiceProvider {

    /**
     * Opens a connection portal to this service.
     */
    fun connect(): ServicePortal
}


/**
 * Abstract Service connection object. actual portals mostly rely on
 * InputStream/ OutputStream; be it from a Bluetooth or a TCP connection.
 */
interface ServicePortal {

    /**
     * Send a message to this service through its portal.
     */
    fun send(vararg messages: String)

    /**
     * Receive messages from this service through its portal.
     * Operations on this sequence are expected to block the current thread.
     */
    fun incomingMessages(): Sequence<String>

    /**
     * Indicates wither [shutdown] has been called or not.
     */
    val isActive: Boolean

    /**
     * Shutdown the portal and disconnect the service.
     */
    fun shutdown()
}