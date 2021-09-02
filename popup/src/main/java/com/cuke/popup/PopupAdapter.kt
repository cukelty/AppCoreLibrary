package com.cuke.popup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PopupAdapter : RecyclerView.Adapter<PopupViewHolder>() {
    var listBeans: List<PopupItemBean?>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopupViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_popup_custom, parent, false)
        return PopupViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopupViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return if (listBeans == null) 0 else listBeans!!.size
    }
}