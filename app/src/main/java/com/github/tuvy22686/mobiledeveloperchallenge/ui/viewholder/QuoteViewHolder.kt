package com.github.tuvy22686.mobiledeveloperchallenge.ui.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.tuvy22686.mobiledeveloperchallenge.R

class QuoteViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val itemTextView: TextView = view.findViewById(R.id.text_name_quote)

    interface ItemClickListener {
        fun onItemClick(rate: Double)
    }
}