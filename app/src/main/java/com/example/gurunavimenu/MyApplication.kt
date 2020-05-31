package com.example.gurunavimenu

import android.app.Application

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {//アプリが立ち上がっているときに絶対に1回しか呼ばれない初期化
    }
}