package com.example.gurunavimenu

import android.provider.ContactsContract
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Realm : RealmObject(){
    @PrimaryKey
    var id: Long? = 0
    var title : String? = ""
    var image: String? = ""
    var category :String? = ""
    var area : String? = ""
}

open class OnOffRealm : RealmObject(){
    @PrimaryKey
    var id : Long? = 0
    var onOff : Boolean = true
}