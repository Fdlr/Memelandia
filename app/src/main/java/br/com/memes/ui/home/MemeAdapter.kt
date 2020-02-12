package br.com.memes.ui.home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.memes.R
import br.com.memes.model.MemeModel
import br.com.memes.ui.settings.MemesManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_meme.view.*

class MemeAdapter(private val context: Context, private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list: List<MemeModel> = arrayListOf()
    private var memesManager: MemesManager?=null
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_meme, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ItemViewHolder){
            holder.bind(list[position], onItemClickListener)
        }
    }

    fun setMeme(listMemes: List<MemeModel>){
        list = listMemes
    }

    interface OnItemClickListener{
        fun onItemClicked( item : MemeModel )
        fun onShareClicked( item : MemeModel )
        fun onFavoriteClicked( item : MemeModel )
    }

    inner class ItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(item : MemeModel, onItemClickListerner: OnItemClickListener){

            itemView.apply {
                memesManager= MemesManager(context)
                colorBackgroundMemes.setBackgroundColor(Color.parseColor(memesManager?.color))
                img_meme.setOnClickListener {
                    onItemClickListerner.onItemClicked( item )
                }

                img_shared.setOnClickListener {
                    onItemClickListerner.onShareClicked( item )
                }

                img_favorite.setOnClickListener {
                    onItemClickListerner.onFavoriteClicked( item )
                }

                if(item.isFavorite){
                    img_favorite.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_red))
                } else {
                    img_favorite.setImageDrawable(context.getDrawable(R.drawable.ic_favorite))
                }

                //Picasso.get().load(context.getDrawable(R.drawable.logo)).into(img_meme)
                tv_title.text = item.name

            }
        }

    }


}