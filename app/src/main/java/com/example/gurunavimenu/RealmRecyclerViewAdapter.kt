package com.example.gurunavimenu

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.realm.RealmResults

class RealmRecyclerViewAdapter (realmResults:RealmResults<Realm>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val item: RealmResults<Realm> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        RecyclerViewAdapter.ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.one_result,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecyclerViewAdapter.ItemViewHolder)
            onBindViewHolder(holder, position)
    }

    private fun onBindViewHolder(holder: RecyclerViewAdapter.ItemViewHolder, position: Int,context:Context) {
        val data = item[position]
        Picasso.get().load(data?.image).into(holder.rImage)
        holder.rTitle.text = data?.title
        holder.rCategory.text = data?.category
        holder.rArea.text = data?.area
        holder.rootView.setBackgroundColor(
            ContextCompat.getColor(
                context,
                if (position % 2 == 0) R.color.colorPrimary else R.color.colorAccent
            )
        )
        holder.rootView.setOnClickListener {
            Toast.makeText(context, "${data?.title}", Toast.LENGTH_SHORT).show()
        }
    }
    
}