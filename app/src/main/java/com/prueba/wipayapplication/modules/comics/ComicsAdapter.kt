package com.prueba.wipayapplication.modules.comics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prueba.wipayapplication.R
import com.prueba.wipayapplication.marvelapi.model.Result


class ComicsAdapter(private val context: Context?): RecyclerView.Adapter<ComicsAdapter.CosmicAdapter>()  {

    var listener: ComicsAdapterListener? = null

    class CosmicAdapter(val view: ViewGroup) : RecyclerView.ViewHolder(view)

    var comics: List<Result> = listOf()
        set(value) {
            field = value
            this.notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CosmicAdapter {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_comic_item, parent, false) as ViewGroup
        return CosmicAdapter(view)
    }

    override fun getItemCount(): Int {
        return comics.size
    }

    override fun onBindViewHolder(holder: CosmicAdapter, position: Int) {
        val view = holder.view
        val image = view.findViewById<ImageView>(R.id.imageComicRow)
        image.setImageResource(R.drawable.ic_marvel_studios_logo)
        configRow(holder.view, position)

    }

    private fun configRow(view: View, position: Int){

        val image = view.findViewById<ImageView>(R.id.imageComicRow)
        val urlImage = "${this.comics[position].thumbnail?.path}.${this.comics[position].thumbnail?.extension}"
        Glide.with(context!!).load(urlImage).into(image)

        view.setOnClickListener {
            listener?.itemSelected(position)
        }
    }

}