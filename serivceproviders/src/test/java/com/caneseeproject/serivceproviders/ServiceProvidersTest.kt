package com.caneseeproject.serivceproviders

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ServiceProvidersTest {

    companion object {
        lateinit var servicePortal: ServicePortal

        @BeforeClass
        @JvmStatic
        fun initTest() {
            servicePortal = MockServiceProvider().connect()
        }
    }


    @Test
    fun testServicePortalConnection() {
        assert(servicePortal.isActive)
    }

    @Test
    fun testServicePortalIncomingMessages() {
        servicePortal.send("Hello", " World")
        assert(servicePortal.incomingMessages().reduce { acc, s -> acc + s } == "Hello World")
        servicePortal.shutdown()
        assert(servicePortal.isActive.not())
    }
}
