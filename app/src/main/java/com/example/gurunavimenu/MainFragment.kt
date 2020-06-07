package com.example.gurunavimenu

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.child_fragment.*
import java.io.IOException

open class MainFragment : Fragment() {

    var loadPage = 1
    private lateinit var realm: Realm
    private lateinit var customAdapter: RecyclerViewAdapter
    private val handler = Handler()

    private val scrollListener = object : NextScrollListener() {
        override fun onLoadMore() {
            updateData(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.child_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        realm = Realm.getDefaultInstance()
        initialize()
    }

    private fun initialize() {
        initLayout()
    }

    private fun initLayout() {
        updateData()
        initRecyclerView()
        initSwipeRefreshLayout()
        initClick()
    }

    private fun initClick() {
//        favoriteBtnFolse.setOnClickListener {
//            realm?.executeTransaction {
//                val maxId = realm.where<com.example.gurunavimenu.Realm>().max("id")
//                val nextId = (maxId?.toLong() ?: 0L) + 1L
//                val favorite = realm.createObject<com.example.gurunavimenu.Realm>(nextId)
//                favorite?.apply {
//                    title = rTitle?.toString()
//                    image = rImage?.toString()
//                    category = rCategory?.toString()
//                    area = rArea?.toString()
//                }
//            }
//            Toast.makeText(context, "保存しました", Toast.LENGTH_SHORT).show()
//        }
    }


    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            updateData()
        }
    }

    private fun initRecyclerView() {
        activity?.also {
            customAdapter = RecyclerViewAdapter(it)
        }
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.apply {
            adapter = customAdapter
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            addOnScrollListener(scrollListener)
        }
    }

    private fun updateData(isAdd: Boolean = false) {
        if (isAdd) {
            loadPage ++
        } else {
            loadPage = 1
        }
        println("updateData loadPage:$loadPage")
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.gnavi.co.jp/RestSearchAPI/v3/?keyid=10d7139a174395ebb2a656fad8ef098a&offset_page=${loadPage}")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                handler.post {
                    swipeRefreshLayout.isRefreshing = false
                    customAdapter?.refresh(listOf())
                    scrollListener.isLoading = false
                }
            }

            override fun onResponse(call: Call, response: Response) {
                var result: GurunaviResponse? = null
                response.body?.string()?.also {
                    val gson = Gson()
                    result = gson.fromJson(it, GurunaviResponse::class.java)
                }
                handler.post {
                    swipeRefreshLayout.isRefreshing = false
                    result?.also {
                        if (isAdd)
                            customAdapter?.add(it.rest)
                        else
                            customAdapter?.refresh(it.rest)
                    } ?: run {
                        customAdapter?.refresh(listOf())
                    }
                    scrollListener.isLoading = false
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}





