package com.example.el_hamd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.el_hamd.databinding.ItemBinding
import com.example.el_hamd.pojo.room.Item
import com.example.el_hamd.pojo.room.ItemDatabase
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ItemsAdapter(
    private val items: ArrayList<Item>,
    private val listener: OnItemCLickListener
) :
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private lateinit var view: View
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        view = parent.rootView
        context = parent.context
        val binding =
            ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(items.get(position))
        holder.binding.removeItem.setOnClickListener {
            val item = items.get(position)
            items.removeAt(position)
            GlobalScope.launch {
                ItemDatabase.getInstance(context).dao.deleteItem(item.id!!)
            }
            notifyItemRemoved(position)
            Snackbar.make(view, context.getString(R.string.item_deleted), Snackbar.LENGTH_LONG)
                .setAction(context.getString(R.string.restore), View.OnClickListener {
                    items.add(position, item)
                    GlobalScope.launch {
                        ItemDatabase.getInstance(context).dao.addItem(item)
                    }
                    notifyItemInserted(position)
                })
                .show()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemViewHolder(
        val binding: ItemBinding,
        private val listener: OnItemCLickListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bindItem(item: Item) {
            binding.buyPrice.text = item.buyPrice.toString()
            binding.sellPrice.text = item.sellPrice.toString()
            binding.itemName.text = item.name
            binding.itemQuantity.text = item.quantity.toString()

        }

        override fun onClick(p0: View?) {
            listener.onItemClick(items[adapterPosition])
        }
    }


    interface OnItemCLickListener {
        fun onItemClick(item: Item)
    }

}