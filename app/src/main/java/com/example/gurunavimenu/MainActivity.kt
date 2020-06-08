package com.example.gurunavimenu

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.one_result.*


class MainActivity : AppCompatActivity() {

    private val fragmentList = arrayListOf(
        MainFragment(),
        FavoriteFragment()
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
    }

    private fun initialize() {
        initLayout()
    }

    private fun initLayout() {
        initClick()
        initTabLayout()
        initViewPager()
    }

    private fun initClick() {
        closeImageView.setOnClickListener {
            finish()
        }
    /*    favoriteBtnFolse.setOnClickListener{
            realm.executeTransaction {
                val maxId = realm.where<com.example.gurunavimenu.Realm>().max("id")
                val nextId = (maxId?.toLong() ?: 0L) + 1L
                val gurunavirealm = realm.createObject<com.example.gurunavimenu.Realm>(nextId)
                gurunavirealm.apply {
                    title= rTitle.text.toString()
                    image = rImage.toString()
                    category = rCategory.text.toString()
                    area = rArea.text.toString()
                }
            }
            Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_SHORT).show()
        }*/

    }


    private fun initTabLayout() {
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun initViewPager() {
        var adapter = ViewPagerAdapter(supportFragmentManager, fragmentList)
        viewPager.adapter = adapter
    }



    companion object {
        fun start(activity: Activity) =
            activity.startActivity(Intent(activity, MainActivity::class.java))
    }
}