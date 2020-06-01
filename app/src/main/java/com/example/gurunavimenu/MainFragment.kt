package com.example.gurunavimenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.child_fragment.*
import java.io.IOException

open class MainFragment : Fragment() {

    private val customAdapter by lazy { activity?.let { RecyclerViewAdapter(it) } }
    private val handler = Handler()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.child_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        initLayout()
    }

    private fun initLayout() {
        updateData()
        initRecyclerView()
        initSwipeRefreshLayout()
    }


    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            updateData()
        }
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            adapter = customAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun updateData() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.gnavi.co.jp/RestSearchAPI/v3/?keyid=10d7139a174395ebb2a656fad8ef098a&offset_page=1")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                handler.post {
                    swipeRefreshLayout.isRefreshing = false
                    customAdapter?.refresh(listOf())
                }
            }

            override fun onResponse(call: Call, response: Response) {
                var result: GurunaviResponse? = null
                response.body?.string()?.also {
                    val gson = Gson()
                    result = gson.fromJson(it, GurunaviResponse::class.java)
                }
                handler.post {
                    result?.also {
                        customAdapter?.refresh(it.rest)
                    } ?: run {
                        customAdapter?.refresh(listOf())
                    }
                }
            }


        })
    }
}


