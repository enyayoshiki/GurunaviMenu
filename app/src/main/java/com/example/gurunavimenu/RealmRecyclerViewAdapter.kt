package com.example.gurunavimenu

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.realm.RealmResults


class RealmRecyclerViewAdapter (realmResults:RealmResults<Realm>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val item: RealmResults<Realm> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.one_result,
                parent,
                false
            ) as ViewGroup
        )

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder)
            onBindViewHolder(holder, position)
    }

    private fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = item[position]
        Picasso.get().load(data?.image).into(holder.rImage)
        holder.apply {
            rTitle.text = data?.title
            rCategory.text = data?.category
            rArea.text = data?.area
            /*rootView.setBackgroundColor(
                ContextCompat.getColor(
                    ,
                    if (position % 2 == 0) R.color.colorPrimaryDark else R.color.colorAccent
                )
            )
            rootView.setOnClickListener {
                Toast.makeText(, "${data?.title}", Toast.LENGTH_SHORT).show()
            }*/
        }
    }

}