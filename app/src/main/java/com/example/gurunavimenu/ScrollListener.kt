package com.example.gurunavimenu

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class NextScrollListener() : RecyclerView.OnScrollListener() {


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
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition() +1

        if (lastVisibleItem >= totalItemCount) {
            isLoading = true
            onLoadMore()
        }
    }
    
    abstract fun onLoadMore()
}