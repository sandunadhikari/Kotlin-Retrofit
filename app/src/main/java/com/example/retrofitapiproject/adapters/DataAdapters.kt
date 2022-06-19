package com.example.retrofitapiproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofitapiproject.R
import com.example.retrofitapiproject.model.Meme
import com.example.retrofitapiproject.ui.DataViewModel
import kotlinx.android.synthetic.main.card_view.view.*

class DataAdapters(
    var memeData:List<Meme>,
    private val viewModel: DataViewModel
): RecyclerView.Adapter<DataAdapters.DataViewHolder>() {

    inner class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view,parent,false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val data = memeData[position]
        holder.itemView.apply {
            Glide.with(this).load(data.url).into(rvImage)
            rvId.text = data.id
            rvName.text = data.name

            setOnClickListener {
                onItemClickListener?.let { it(data) }
            }
        }
    }

    override fun getItemCount(): Int {
        return memeData.size
    }

    private var onItemClickListener: ((Meme) -> Unit)? = null

    fun setOnItemClickListener(listener: (Meme) -> Unit) {
        onItemClickListener = listener
    }


}