package com.example.gurunavimenu

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class NextScrollListener: RecyclerView.OnScrollListener() {

    private var currentTotalCount = 0

    var isLoading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dx == 0 && dy == 0)
            return
        if (isLoading)
            return
        val customAdapter = recyclerView.adapter ?: return
        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager ?: return
        val totalItemCount = customAdapter.itemCount
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition() + 1
        currentTotalCount = totalItemCount
        println("currentTotalCount:$totalItemCount lastVisibleItem:$lastVisibleItem currentTotalCount:$currentTotalCount")
        if (lastVisibleItem >= totalItemCount - 10) {
            isLoading = true
            onLoadMore()
        }
    }

//    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//        super.onScrolled(recyclerView, dx, dy)
//
//
//
//        visibleItemCount = recyclerView?.childCount ?: 0
//        totalItemCount = layoutManager.itemCount
//        firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
//
//        if (firstVisibleItem + visibleItemCount >= totalItemCount) {
//            onLoadMore()
//        }
//    }


    abstract fun onLoadMore()
}