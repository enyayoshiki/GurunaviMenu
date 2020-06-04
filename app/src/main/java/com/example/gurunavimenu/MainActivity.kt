package com.example.gurunavimenu

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
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


    companion object {
        fun start(activity: Activity) =
            activity.startActivity(Intent(activity, MainActivity::class.java))
    }
}