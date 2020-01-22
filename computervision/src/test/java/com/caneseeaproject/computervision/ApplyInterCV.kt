package com.caneseeaproject.computervision

import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*


class ApplyInterCV: InterCV {
    private val channel = Channel<Flow<String>>() //standalone //laterOnConsumeFromBelow
    fun emitter(): Flow<String> =
        { "Data" }
            .asFlow()
    suspend fun channelSend(data: Flow<String>) {
        channel.send(emitter())
    }




    override suspend fun getData():Flow<String>{
        val recieved =  channel.receive()
        return recieved
    }



    override suspend fun sendData(data: Flow<String>) {
        channelSend(getData())
    }

}