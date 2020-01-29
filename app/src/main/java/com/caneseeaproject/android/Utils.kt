package com.caneseeaproject.android

import android.util.Log

/**
 *@author mhashim6 on 2020-01-29
 */


fun Any.debug(what: String) {
    Log.d(javaClass.simpleName, what)
}

fun Any.wtf(what: String) {
    Log.wtf(javaClass.simpleName, what)
}