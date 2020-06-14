package com.example.gurunavimenu

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required


open class Realm : RealmObject(){
    @PrimaryKey
    @Required
    var id: Long? = 0L
    var title : String? = ""
    var image: String? = ""
    var category :String? = ""
    var area : String? = ""
    var primalId:String? =""
}
