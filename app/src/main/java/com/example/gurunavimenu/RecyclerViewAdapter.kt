package com.example.gurunavimenu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Rest>()

    fun refresh(list: List<Rest>) {
        items.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ItemViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.one_result,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder)
            onBindViewHolder(holder, position)
    }

    private fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = items[position]
        Picasso.get().load(data.image_url.qrcode).into(holder.rImage)
        holder.rTitle.text = data.name
        holder.rCategory.text = data.category
        holder.rArea.text = data.code.areaname_s
        holder.rootView.setBackgroundColor(
            ContextCompat.getColor(
                context,
                if (position % 2 == 0) R.color.colorPrimary else R.color.colorAccent
            )
        )
        holder.rootView.setOnClickListener {
            Toast.makeText(context, "${data.name}", Toast.LENGTH_SHORT).show()
        }
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rootView: ConstraintLayout = view.findViewById(R.id.rootView)
        val rImage: ImageView = view.findViewById(R.id.rImage)
        val rTitle: TextView = view.findViewById(R.id.rTitle)
        val rCategory: TextView = view.findViewById(R.id.rCategory)
        val rArea: TextView = view.findViewById(R.id.rArea)
    }
}
