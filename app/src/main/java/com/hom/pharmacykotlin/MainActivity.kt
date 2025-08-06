package com.hom.pharmacykotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hom.pharmacykotlin.data.PharmacyInfo
import com.hom.pharmacykotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var selectTown: String = ""
    private var selectCounty: String = ""
    private var pharmInfoData: PharmacyInfo? = null
    private lateinit var viewModel: PharmViewModel

    //
    private lateinit var progressBar: ProgressBar
    private lateinit var spinnerCounty: Spinner
    private lateinit var spinnerTown: Spinner
    private lateinit var recy: RecyclerView
    private val TAG: String? = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViews()

        viewModel = ViewModelProvider(this).get(PharmViewModel::class.java)
        progressBar.visibility = View.VISIBLE
        viewModel.vmPharmData()
        viewModel.getPharmInfoData.observe(this) {
            pharmInfoData = it
        }
        viewModel.getAllCounty.observe(this) {
            setSpinnerCounty(it)
        }
        viewModel.getAllTown.observe(this) {
            setSpinnerTown(it)
            progressBar.visibility = View.GONE
        }
    }

    private fun setSpinnerCounty(countyName: List<String>) {
        val countyAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countyName)
                .apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        spinnerCounty.adapter = countyAdapter
        spinnerCounty.prompt = " Select County "
        spinnerCounty.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectCounty = spinnerCounty.selectedItem.toString()
                Log.d(TAG, "onItemSelected: mask- selectCounty- ${selectCounty}")
                viewModel.vmUpdataTown(selectCounty)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun setSpinnerTown(townName: List<String>) {
        val townAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, townName)
                .apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        spinnerTown.adapter = townAdapter
        spinnerTown.prompt = " Select Town "
        spinnerTown.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectTown = spinnerTown.selectedItem.toString()
                Log.d(TAG, "onItemSelected: mask- selectTown- ${selectTown}")
                setRecyclerPharm(pharmInfoData)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun setRecyclerPharm(pharmInfoData: PharmacyInfo?) {
        pharmInfoData?.also { p ->
            val filterData = p.features.filter {
                it.properties.county == selectCounty &&
                        it.properties.town == selectTown
            }
            if (filterData != null) {
                recy.adapter = PharmAdapter(this, filterData)
            }
        }
    }

    private fun findViews() {
        spinnerCounty = binding.spinnerCounty
        spinnerTown = binding.spinnerTown
        recy = binding.recyclerPharm
        progressBar = binding.progressBar
        recy.setHasFixedSize(true)
        recy.layoutManager = GridLayoutManager(this, 1)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuId = item.itemId
        when (menuId) {
            R.id.action_exit -> {
                finish()
                true
            }

            R.id.action_maps -> {
                Intent(this, MapsActivity::class.java)
                    .also { startActivity(it) }
                true
            }

        }
        return super.onOptionsItemSelected(item)
    }

}








