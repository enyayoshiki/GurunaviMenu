package com.example.gurunavimenu


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import okhttp3.*
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.solver.widgets.ConstraintWidget
import androidx.constraintlayout.solver.widgets.ConstraintWidget.INVISIBLE
import androidx.constraintlayout.solver.widgets.ConstraintWidget.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.createObject
import kotlinx.android.synthetic.main.child_fragment.*
import kotlinx.android.synthetic.main.one_result.view.*
import java.io.IOException

open class MainFragment : Fragment() {

    var loadPage = 1
    private lateinit var customAdapter: RecyclerViewAdapter
    private val handler = Handler()
    private lateinit var realm: Realm


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
        //    initScroll()
    }


    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            updateData()
        }
    }

    @SuppressLint("WrongConstant")
    private fun initRecyclerView() {
//        val realmResults = realm.where(OnOffRealm::class.java)
//            .findAll()
//            .sort("id", Sort.DESCENDING)
//        val OnOffRealm = realm.createObject<OnOffRealm>(id)

        activity?.also {
            customAdapter = RecyclerViewAdapter(it)
        }
        recyclerView.apply {
            adapter = customAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
//            if(OnOffRealm.onOff){
//                favoriteBtnFolse.visibility = VISIBLE
//                favoriteBtnTrue.visibility = INVISIBLE
//            }else{
//                favoriteBtnFolse.visibility = INVISIBLE
//                favoriteBtnTrue.visibility = VISIBLE
//            }
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
            }
        }

        override fun onResponse(call: Call, response: Response) {
            var items = mutableListOf<Rest>()
            var result: GurunaviResponse? = null
            response.body?.string()?.also {
                val gson = Gson()
                result = gson.fromJson(it, GurunaviResponse::class.java)
            }
            handler.post {
                result?.also {
                    if (loadPage == 1) {
                        customAdapter.refresh(it.rest)
                        items.addAll(it.rest)
                    } else {
                        items.addAll(it.rest)
                        customAdapter.refresh(items)
                    }
                } ?: run {
                    customAdapter.refresh(listOf())
                }
            }
        }
    })
}

//    private fun initScroll() {
//        recyclerView.addOnScrollListener(object :
//            NextScrollListener(LinearLayoutManager(context)) {
//            override fun onLoadMore() {
//                loadPage++
//                updateData()
//            }
//        })
//    }


}





