package com.github.tuvy22686.mobiledeveloperchallenge.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.tuvy22686.mobiledeveloperchallenge.R
import com.github.tuvy22686.mobiledeveloperchallenge.model.business.Quote
import com.github.tuvy22686.mobiledeveloperchallenge.ui.viewholder.QuoteViewHolder

class QuoteViewAdapter(private val context: Context, private val quotes: List<Quote>, private val itemClickListener: QuoteViewHolder.ItemClickListener) : RecyclerView.Adapter<QuoteViewHolder>() {

    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.itemTextView.text = quotes[position].name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.item_list, parent, false)
        view.setOnClickListener {
            recyclerView?.apply {
                itemClickListener.onItemClick(quotes[getChildAdapterPosition(view)].rate)
            }
        }
        return QuoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quotes.size
    }
}