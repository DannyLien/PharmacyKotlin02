package com.hom.pharmacykotlin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hom.pharmacykotlin.data.Feature
import com.hom.pharmacykotlin.data.PharmacyInfo
import com.hom.pharmacykotlin.databinding.ActivityPharmacyDetailBinding

class PharmacyDetail : AppCompatActivity() {
    private lateinit var getData: Feature
    private lateinit var pharmAddress: TextView
    private lateinit var pharmPhone: TextView
    private lateinit var maskChild: TextView
    private lateinit var maskAdult: TextView
    private lateinit var pharmName: TextView
    private val TAG: String? = PharmacyDetail::class.java.simpleName
    private lateinit var binding: ActivityPharmacyDetailBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPharmacyDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getData = PharmAdapter.PharmDataHolder.pharmacyData
//        Log.d(TAG, "onCreate: mask- getData- ${getData}")
        findViews()
        getData.properties.apply {
            pharmName.setText(name)
            maskAdult.setText(mask_adult.toString())
            maskChild.setText(mask_child.toString())
            pharmPhone.setText(phone)
            pharmAddress.setText(address)
        }
    }

    private fun findViews() {
//        tv_name_det , tv_adult_amount_det , tv_child_amount_det , tv_phone_det , tv_address_det
        pharmName = binding.tvNameDet
        maskAdult = binding.tvAdultAmountDet
        maskChild = binding.tvChildAmountDet
        pharmPhone = binding.tvPhoneDet
        pharmAddress = binding.tvAddressDet
    }

    fun setShowMaps(view: View) {
        Intent(this, MapsActivity::class.java)
            .also { startActivity(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_pharmacy_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuId = item.itemId
        when (menuId) {
            R.id.action_detail_back -> {
                finish()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}







