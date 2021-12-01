package com.example.el_hamd.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.el_hamd.ItemsAdapter
import com.example.el_hamd.R
import com.example.el_hamd.databinding.ActivityMainBinding
import com.example.el_hamd.pojo.mvvm.ItemViewModel
import com.example.el_hamd.pojo.room.ItemDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import com.example.el_hamd.ItemsAdapter.OnItemCLickListener
import com.example.el_hamd.pojo.room.Item
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), OnItemCLickListener {
    private lateinit var itemViewModel: ItemViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)
        binding.rec.setHasFixedSize(true)
        binding.addItem.setOnClickListener {
            startActivity(Intent(applicationContext, AddItemActivity::class.java))
            finish()
        }
        val listener: OnItemCLickListener = this
        itemViewModel.getAllItems(this)
        itemViewModel.itemsLiveData?.observe(this, Observer {
            if (it != null)
                binding.rec.adapter = ItemsAdapter(ArrayList(it), listener)
        })
    }


    override fun onItemClick(item: Item) {
        val intent = Intent(applicationContext,UpdateItemActivity::class.java)
        intent.putExtra("item",item)
        startActivity(intent)
    }
}