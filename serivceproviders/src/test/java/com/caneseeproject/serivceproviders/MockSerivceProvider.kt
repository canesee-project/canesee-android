package com.caneseeproject.serivceproviders

/**
 *@author mhashim6 on 2019-12-08
 */
class MockServiceProvider : ServiceProvider {
    override fun connect(): ServicePortal {
        println("service is connected")
        return MockEchoServicePortal()
    }
}

class MockEchoServicePortal : ServicePortal {
    override var isActive: Boolean = true
    private val messages = mutableListOf<String>()

    override fun send(vararg messages: String) {
        this.messages.addAll(messages)
    }

    override fun incomingMessages(): Sequence<String> = messages.asSequence()

    override fun shutdown() {
        isActive = false
        println("service is shutdown")
    }

}