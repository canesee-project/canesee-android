package com.caneseeproject.bluetooth

import android.app.Application
import android.content.Context

class globalActivity : Application() {
    companion object {
        var ctx: Context? = null
    }
    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
    }
    fun getAppContext(): Context? {
        return ctx
    }




    }