package com.cuke.popup

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PopupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvName: TextView

    init {
        tvName = itemView.findViewById<View>(R.id.tv_name) as TextView
    }
}