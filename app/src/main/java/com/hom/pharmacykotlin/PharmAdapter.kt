package com.hom.pharmacykotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hom.pharmacykotlin.data.Feature
import com.hom.pharmacykotlin.databinding.RowPharmViewBinding

class PharmAdapter(val filterData: List<Feature>) :
    RecyclerView.Adapter<PharmAdapter.PharmViewHolder>() {
    class PharmViewHolder(var view: RowPharmViewBinding) : ViewHolder(view.root) {
        val pharmName = view.tvName
        val pharmAdult = view.tvAdultAmount
        val pharmChild = view.tvChildAmount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmViewHolder {
        val v = RowPharmViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PharmViewHolder(v)
    }

    override fun getItemCount(): Int {
        return filterData.size
    }

    override fun onBindViewHolder(holder: PharmViewHolder, position: Int) {
        val data = filterData.get(position).properties
        holder.pharmName.setText(data.name)
        holder.pharmAdult.setText(data.mask_adult.toString())
        holder.pharmChild.setText(data.mask_child.toString())
    }

}
