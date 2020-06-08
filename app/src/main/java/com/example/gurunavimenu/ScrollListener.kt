package com.example.gurunavimenu

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class NextScrollListener(private val layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener() {



    private var visibleItemCount: Int = -1
    private var totalItemCount: Int = -1
    private var firstVisibleItem: Int = -1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView?.childCount ?: 0
        totalItemCount = layoutManager.itemCount
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition()


        if (firstVisibleItem + visibleItemCount >= totalItemCount) {
            onLoadMore()
        }
    }


    abstract fun onLoadMore()
}