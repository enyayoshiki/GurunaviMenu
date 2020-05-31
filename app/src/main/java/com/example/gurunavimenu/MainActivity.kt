package com.example.gurunavimenu

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var progressDialog: MaterialDialog? = null

    private val fragmentList = arrayListOf<Fragment>(
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
//        showProgress()
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


    private fun showProgress() {
        hideProgress()
        var progressDialog = MaterialDialog(this).apply {
            cancelable(false)
            setContentView(
                LayoutInflater.from(this@MainActivity)
                    .inflate(R.layout.progress_dialog, null, false)
            )
            show()
        }
    }

    private fun hideProgress() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    companion object {
        fun start(activity: Activity) =
            activity.startActivity(Intent(activity, MainActivity::class.java))
    }
}