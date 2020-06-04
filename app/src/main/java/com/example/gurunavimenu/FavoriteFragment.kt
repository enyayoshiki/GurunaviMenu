package com.example.gurunavimenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.Sort
import kotlinx.android.synthetic.main.child_fragment.*

class FavoriteFragment: Fragment() {

    private lateinit var realm: Realm
    private lateinit var customAdapter: RealmRecyclerViewAdapter

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
        initRecyclerView()
    }

    private fun initRecyclerView() {

        val realmResults = realm.where(com.example.gurunavimenu.Realm::class.java)
            .findAll()
            .sort("id", Sort.DESCENDING)

        recyclerView.apply {
            customAdapter = RealmRecyclerViewAdapter(realmResults)
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}