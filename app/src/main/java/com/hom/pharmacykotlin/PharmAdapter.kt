package com.hom.pharmacykotlin

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hom.pharmacykotlin.data.Feature
import com.hom.pharmacykotlin.data.PharmacyInfo
import com.hom.pharmacykotlin.databinding.RowPharmViewBinding

class PharmAdapter(val _context: Context, val filterData: List<Feature>) :
    RecyclerView.Adapter<PharmAdapter.PharmViewHolder>() {
    private val TAG: String? = PharmAdapter::class.java.simpleName

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

    object PharmDataHolder {
        lateinit var pharmacyData: Feature
    }

    override fun onBindViewHolder(holder: PharmViewHolder, position: Int) {
        val data = filterData.get(position)
        holder.pharmName.setText(data.properties.name)
        holder.pharmAdult.setText(data.properties.mask_adult.toString())
        holder.pharmChild.setText(data.properties.mask_child.toString())
        holder.itemView.setOnClickListener {
//            Log.d(TAG, "onBindViewHolder: mask- data- ${data}")
            // 設置單例資料
            PharmDataHolder.pharmacyData = data
            Intent(_context, PharmacyDetail::class.java)
                .also { _context.startActivity(it) }
        }
    }

}






