package com.example.gurunavimenu


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.solver.widgets.ConstraintWidget.VISIBLE
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.INVISIBLE
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class RecyclerViewAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Rest>()
    private lateinit var realm: Realm

    fun refresh(list: List<Rest>) {
        items.apply {
            if (items.size > 10) {
                addAll(list)
            } else
                clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.one_result,
                parent,
                false
            ) as ViewGroup
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder)
            onBindViewHolder(holder, position)
    }


    @SuppressLint("WrongConstant")
    private fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        realm = Realm.getDefaultInstance()

        val data = items[position]
        Picasso.get().load(data.image_url.qrcode).into(holder.rImage)
        holder.rTitle.text = data.name
        holder.rCategory.text = data.category
        holder.rArea.text = data.code.areaname_s
        val realmResults =
            realm.where(com.example.gurunavimenu.Realm::class.java).equalTo("primalId", data.id)
                .findAll()
//        val ResultArray: String = realmResults.toTypedArray().toString()
        when (realmResults.size.toString()) {
            "1" ->
                holder.apply {
                    favoriteBtnFolse.visibility = INVISIBLE
                    favoriteBtnTrue.visibility = VISIBLE
                }
            else ->
                holder.apply {
                    favoriteBtnFolse.visibility = VISIBLE
                    favoriteBtnTrue.visibility = INVISIBLE
                }
        }

        holder.favoriteBtnFolse.setOnClickListener {
            realm.executeTransaction {
                val gurunavirealm = realm.createObject<com.example.gurunavimenu.Realm>(position)
                gurunavirealm.apply {
                    title = data.name
                    image = data.image_url.qrcode
                    category = data.category
                    area = data.code.areaname
                    primalId = data.id
                }
            }
            it.visibility = INVISIBLE
            holder.favoriteBtnTrue.visibility = VISIBLE
            Toast.makeText(context, "保存しました", Toast.LENGTH_SHORT).show()
        }
        holder.favoriteBtnTrue.setOnClickListener {
            realm.executeTransaction {
                val deleteRealm = realm.where<com.example.gurunavimenu.Realm>()
                    .equalTo("title", "${holder.rTitle.text}")
                    ?.findFirst()
                    ?.deleteFromRealm()
            }
            it.visibility = INVISIBLE
            holder.favoriteBtnFolse.visibility = VISIBLE
            Toast.makeText(context, "お気に入りを解除しました", Toast.LENGTH_SHORT).show()
        }
    }
}


class ItemViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view) {
    val rootView: ConstraintLayout = view.findViewById(R.id.rootView)
    val rImage: ImageView = view.findViewById(R.id.rImage)
    val rTitle: TextView = view.findViewById(R.id.rTitle)
    val rCategory: TextView = view.findViewById(R.id.rCategory)
    val rArea: TextView = view.findViewById(R.id.rArea)
    val favoriteBtnFolse: Button = view.findViewById(R.id.favoriteBtnFolse)
    val favoriteBtnTrue: Button = view.findViewById(R.id.favoriteBtnTrue)
}




