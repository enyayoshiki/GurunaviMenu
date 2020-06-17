package com.example.gurunavimenu


import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import okhttp3.*
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.child_fragment.*
import java.io.IOException

open class MainFragment : Fragment() {

    var loadPage = 1
    private lateinit var customAdapter: RecyclerViewAdapter
    private val handler = Handler()
    var items = mutableListOf<Rest>()

    private val scrollListener = object : NextScrollListener() {
        override fun onLoadMore() {
            loadPage++
            updateData()
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
            items.clear()
            loadPage = 1
            updateData()
        }
    }


    private fun initRecyclerView() {
        activity?.also {
            customAdapter = RecyclerViewAdapter(it)
        }
        recyclerView.apply {
            adapter = customAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener(scrollListener)
        }
    }


    private fun updateData() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.gnavi.co.jp/RestSearchAPI/v3/?keyid=10d7139a174395ebb2a656fad8ef098a&offset_page=${loadPage}")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                handler.post {
                    swipeRefreshLayout.isRefreshing = false
                    customAdapter.refresh(listOf())
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
                        items.addAll(it.rest)
                        customAdapter.refresh(items)
                        scrollListener.isLoading = false
                    } ?: run {
                        customAdapter.refresh(listOf())
                        scrollListener.isLoading = false
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        items.clear()
    }
}





