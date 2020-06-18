package com.example.othregensburg.zapp.travelStream.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.othregensburg.zapp.R
import com.example.othregensburg.zapp.travelStream.model.DsTravelStream

class TravelStreamAdapter(context: Context,
    items: List<DsTravelStream>) :
    RecyclerView.Adapter<TravelStreamAdapter.ViewHolder?>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val mItems: List<DsTravelStream> = items

    override fun onBindViewHolder(holder: ViewHolder,
        position: Int) {
        val travelStream = mItems[position]
        holder.mImageView.setImageResource(travelStream.resId)
        holder.mTextViewTitle.text = travelStream.title
        holder.mTextViewSubtitle.text = travelStream.subtitle
    }

    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val mImageView: ImageView
        val mTextViewTitle: TextView
        val mTextViewSubtitle: TextView

        init {
            mImageView =
                view.findViewById<View>(R.id.row_travel_stream_image) as ImageView
            mTextViewTitle =
                view.findViewById<View>(R.id.row_travel_stream_title) as TextView
            mTextViewSubtitle =
                view.findViewById<View>(R.id.row_travel_stream_subtitle) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            mInflater.inflate(R.layout.row_travel_stream, parent, false))
    }

    override fun getItemCount(): Int {
        return mItems.size
    }
}