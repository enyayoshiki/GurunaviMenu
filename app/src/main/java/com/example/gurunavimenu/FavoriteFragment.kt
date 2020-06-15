package com.example.gurunavimenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import kotlinx.android.synthetic.main.child_fragment.*

class FavoriteFragment : Fragment() {


    private lateinit var customAdapter: RealmRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.child_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()
        val realmResults = FavoriteStore.findAll()

        activity?.also {
            customAdapter = RealmRecyclerViewAdapter(realmResults)
        }
        recyclerView.apply {
            adapter = customAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }


    companion object {
        fun newInstance(position: Int): FavoriteFragment {
            return FavoriteFragment()
        }
    }
}
