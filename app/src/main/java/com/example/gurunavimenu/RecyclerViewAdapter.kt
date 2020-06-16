package com.example.gurunavimenu


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.gurunavimenu.extention.Visible
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
            if (list.size > 10) {
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


    private fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        realm= Realm.getDefaultInstance()

        val data = items[position]
        Picasso.get().load(data.image_url.qrcode).into(holder.rImage)
        holder.apply {
            rTitle.text = data.name
            rCategory.text = data.category
            rArea.text = data.code.areaname_s
            favoriteBtnFolse.setOnClickListener {
                realm.executeTransaction {
                    val gurunavirealm =
                        realm.createObject<com.example.gurunavimenu.FavoriteStore>(position)
                    gurunavirealm.apply {
                        title = data.name
                        image = data.image_url.qrcode
                        category = data.category
                        area = data.code.areaname
                        id = data.id
                    }
                }
                it.Visible(false)
                favoriteBtnTrue.Visible(true)
                Toast.makeText(context, "保存しました", Toast.LENGTH_SHORT).show()
            }
            favoriteBtnTrue.setOnClickListener {
                realm.executeTransaction {
                    val deleteRealm = realm.where<com.example.gurunavimenu.FavoriteStore>()
                        .equalTo("title", "${holder.rTitle.text}")
                        ?.findFirst()
                        ?.deleteFromRealm()
                }
                it.Visible(false)
                favoriteBtnFolse.Visible(true)
                Toast.makeText(context, "お気に入りを解除しました", Toast.LENGTH_SHORT).show()
            }

            val realmResults = FavoriteStore.findBy(data.id)
            holder.apply {
                favoriteBtnFolse.Visible(realmResults == null)
                favoriteBtnTrue.Visible(realmResults !== null)
            }
        }
    }
}


class ItemViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view) {
    val rImage: ImageView = view.findViewById(R.id.rImage)
    val rTitle: TextView = view.findViewById(R.id.rTitle)
    val rCategory: TextView = view.findViewById(R.id.rCategory)
    val rArea: TextView = view.findViewById(R.id.rArea)
    val favoriteBtnFolse: Button = view.findViewById(R.id.favoriteBtnFolse)
    val favoriteBtnTrue: Button = view.findViewById(R.id.favoriteBtnTrue)
}




