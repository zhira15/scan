package com.scanner.document.docscanner.ui.documents

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.scanner.document.docscanner.R
import com.scanner.document.docscanner.data.model.DocItem
import kotlinx.android.synthetic.main.adapter_document.view.*

/**
 * Created by AnthonyCAS on 8/20/18.
 */
class DocumentsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = ArrayList<DocItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemSelected: (DocItem?) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.adapter_document, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ItemViewHolder).bind(getItem(position), position) { position ->
            itemSelected(getItem(position))
        }
    }

    internal fun getItem(position: Int): DocItem {
        return items[position]
    }

    internal fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun isEmpty(): Boolean {
        return items.isEmpty()
    }

    internal class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: DocItem, position: Int, itemSelected: (Int) -> Unit) {
            itemView.doc_name.text = item.name
            itemView.doc_timestamp.text = item.timestamp
            itemView.setOnClickListener { itemSelected(position) }

            val picture = item.bitmap ?: return
            Glide.with(itemView.context)
                    .load(picture)
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.placeholderOf(R.drawable.camera))
                    .into(itemView.doc_pic)
        }
    }

}