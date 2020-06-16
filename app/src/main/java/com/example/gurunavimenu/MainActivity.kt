package com.example.gurunavimenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*



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
    }


    private fun initTabLayout() {
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun initViewPager() {
        var adapter = ViewPagerAdapter(supportFragmentManager, fragmentList)
        viewPager.adapter = adapter
    }



}