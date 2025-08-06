package com.hom.pharmacykotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.hom.pharmacykotlin.databinding.InfoWindowsBinding

class MyInfoAdapter(val context: Context) : GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(marker: Marker): View? {
        val v = InfoWindowsBinding.inflate(
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        )
        val mask = marker.snippet.toString().split(",")
        v.tvInfoName.setText(marker.title)
        v.tvInfoAdultAmount.setText(mask.get(0))
        v.tvInfoChildAmount.setText(mask.get(1))
        return v.root
    }

    override fun getInfoWindow(p0: Marker): View? {
        return null
    }


}
