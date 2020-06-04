package com.example.gurunavimenu

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration


class CusttomAplication: Application() {
     override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)
    }
}