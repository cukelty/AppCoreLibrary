package com.cuke.habit.binding.viewadapter.recycleview

import androidx.recyclerview.widget.RecyclerView

object LinearManagers {

    interface LineManagerFactory {
        fun create(recyclerView: RecyclerView): RecyclerView.ItemDecoration
    }

    fun both(): LineManagerFactory? {
        return object : LineManagerFactory {
            override fun create(recyclerView: RecyclerView): RecyclerView.ItemDecoration {
                return DividerLine(recyclerView.context, DividerLine.LineDrawMode.BOTH)
            }
        }
    }

    fun horizontal(): LineManagerFactory? {
        return object : LineManagerFactory {
            override fun create(recyclerView: RecyclerView): RecyclerView.ItemDecoration {
                return DividerLine(recyclerView.context, DividerLine.LineDrawMode.HORIZONTAL)
            }
        }
    }

    fun vertical(): LineManagerFactory? {
        return object : LineManagerFactory {
            override fun create(recyclerView: RecyclerView): RecyclerView.ItemDecoration {
                return DividerLine(recyclerView.context, DividerLine.LineDrawMode.VERTICAL)
            }
        }
    }
}