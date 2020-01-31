package com.caneseeaproject.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.caneseeaproject.tts.*
import com.caneseeproject.tts.TextToSpeachClass


class MainActivity : AppCompatActivity() {
    var x = TextToSpeachClass()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
