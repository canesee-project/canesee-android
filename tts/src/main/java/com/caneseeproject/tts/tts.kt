package com.caneseeproject.tts

fun main(args: Array<String>){
    var myVariable = texttospeach()
    myVariable.tts()
}

interface tts{

    fun tts()
}

class texttospeach : tts{
    override fun tts() {

    }
}