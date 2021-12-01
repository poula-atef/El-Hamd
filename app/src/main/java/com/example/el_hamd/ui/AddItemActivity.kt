package com.example.el_hamd.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.el_hamd.R
import com.example.el_hamd.databinding.ActivityAddItemBinding
import com.example.el_hamd.pojo.mvvm.ItemViewModel
import com.example.el_hamd.pojo.room.Item
import com.google.android.material.snackbar.Snackbar

class AddItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemBinding
    private lateinit var itemViewModel: ItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_item)
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)
        val lifecycleOwner: LifecycleOwner = this
        binding.addItem.setOnClickListener {
            if (binding.itemBuyPrice.text.isNotEmpty() &&
                binding.itemQuantity.text.isNotEmpty() &&
                binding.itemSellPrice.text.isNotEmpty() &&
                binding.itemName.text.isNotEmpty()
            ) {
                itemViewModel.addItem(
                    Item(
                        name = binding.itemName.text.toString(),
                        quantity = binding.itemQuantity.text.toString().toInt(),
                        buyPrice = binding.itemBuyPrice.text.toString().toDouble(),
                        sellPrice = binding.itemSellPrice.text.toString().toDouble()
                    ), applicationContext
                )
                itemViewModel.itemAddedLiveData.observe(lifecycleOwner,
                    Observer {
                        onBackPressed()
                    })
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.all_fields_required),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }
}