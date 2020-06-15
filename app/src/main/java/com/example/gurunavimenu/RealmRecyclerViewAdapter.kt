package com.example.gurunavimenu


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors.getColor
import com.squareup.picasso.Picasso
import io.realm.RealmList
import io.realm.RealmResults


class RealmRecyclerViewAdapter (realmResults:List<FavoriteStore>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val item = FavoriteStore.findAll()

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
//            rootView.setBackgroundColor(
//                Context.getColor(
//                    context,
//                    if (position % 2 == 0) R.color.colorPrimaryDark else R.color.colorAccent
//                )
//            )
//            rootView.setOnClickListener {
//                Toast.makeText(, "${data?.title}", Toast.LENGTH_SHORT).show()
//            }
        }
    }

}