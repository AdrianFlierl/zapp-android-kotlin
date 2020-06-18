package com.example.othregensburg.zapp.music.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.othregensburg.zapp.R
import com.example.othregensburg.zapp.music.model.DsAlbum
import java.util.ArrayList

class AlbumAdapter(context: Context,
    items: ArrayList<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val mItems: ArrayList<Any> = items

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val album = mItems[position] as DsAlbum
            holder.mTextViewTitle.text = album.title
            holder.mTextViewPerformer.text = album.performer
            holder.mImageViewCover.setImageResource(album.resIdImage)
            holder.mCardView.setCardBackgroundColor(album.backgroundColor)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mItems[position] is DsAlbum) VIEW_TYPE_ALBUM else VIEW_TYPE_HEADER
    }

    internal inner class ViewHolderHeader(view: View?) :
        RecyclerView.ViewHolder(view!!)

    internal inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val mCardView: CardView
        val mTextViewTitle: TextView
        val mTextViewPerformer: TextView
        val mImageViewCover: ImageView

        init {
            mCardView = view.findViewById<View>(R.id.row_music_card_view) as CardView
            mTextViewTitle = view.findViewById<View>(R.id.row_music_title) as TextView
            mTextViewPerformer =
                view.findViewById<View>(R.id.row_music_performer) as TextView
            mImageViewCover =
                view.findViewById<View>(R.id.row_music_image) as ImageView
        }
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ALBUM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_HEADER -> return ViewHolderHeader(mInflater.inflate(R.layout.row_music_header,
                parent,
                false))
            VIEW_TYPE_ALBUM -> return ViewHolder(
                mInflater.inflate(R.layout.row_music, parent, false))
        }
        return ViewHolder(
            mInflater.inflate(R.layout.row_music, parent, false))
    }

    override fun getItemCount(): Int {
        return mItems.size
    }
}