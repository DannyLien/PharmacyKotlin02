package com.hom.pharmacykotlin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.hom.pharmacykotlin.data.PharmacyInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class PharmViewModel : ViewModel() {
    var pharmInfoData: PharmacyInfo? = null
    var allCounty = mutableListOf<String>()
    var allTown = mutableListOf<String>()
    var getPharmInfoData = MutableLiveData<PharmacyInfo>()
    var getAllCounty = MutableLiveData<List<String>>()
    var getAllTown = MutableLiveData<List<String>>()
    //
    private val TAG: String? = PharmViewModel::class.java.simpleName

    companion object {
        val pharmUrl_132 = "http://delexons.ddns.net:81/pharmacies/info_132.json"
        val pharmUrl_5K = "http://delexons.ddns.net:81/pharmacies/info.json"
    }

    fun vmPharmData() {
        CoroutineScope(Dispatchers.IO).launch {
            val json = URL(pharmUrl_132).readText()
            pharmInfoData = Gson().fromJson(json, PharmacyInfo::class.java)
//            Log.d(TAG, "vmPharmData: mask- pharmInfoData- ${pharmInfoData}")
            getPharmInfoData.postValue(pharmInfoData!!)
            vmUpdataCounty()
        }
    }

    fun vmUpdataCounty() {
        pharmInfoData?.also { p ->
            val data = p.features.groupBy { it.properties.county }
            data.forEach {
                allCounty.add(it.key)
            }
            getAllCounty.postValue(allCounty)
        }
    }

    fun vmUpdataTown(selectCounty: String) {
        pharmInfoData?.also { p ->
            val data = p.features.filter { it.properties.county == selectCounty }
            allTown.clear()
            data.forEach {
                allTown.add(it.properties.town)
            }
            allTown = allTown.distinct().toMutableList()
            getAllTown.postValue(allTown)
        }
    }

}







