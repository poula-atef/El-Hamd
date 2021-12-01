package com.example.el_hamd.pojo.mvvm

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.el_hamd.pojo.room.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ItemViewModel : ViewModel() {

    private var itemsMutableLiveData: MutableLiveData<List<Item>>? = MutableLiveData<List<Item>>()
    val itemsLiveData: LiveData<List<Item>>?
        get() = itemsMutableLiveData

    private var itemMutableLiveData: MutableLiveData<Item>? = MutableLiveData<Item>()
    val itemLiveData: LiveData<Item>?
        get() = itemMutableLiveData

    private var itemAddedMutableLiveData = MutableLiveData<Boolean>()
    val itemAddedLiveData: LiveData<Boolean>
        get() = itemAddedMutableLiveData


    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)


    fun getAllItems(context: Context) {
        uiScope.launch {
            itemsMutableLiveData?.value = ItemRepository.getAllItemsData(context)
        }
    }

    fun getItemById(id: Long, context: Context) {
        uiScope.launch {
            itemMutableLiveData?.value = ItemRepository.getItem(id, context)
        }
    }

    fun addItem(item: Item, context: Context) {
        uiScope.launch {
            ItemRepository.addItem(item, context)
            itemAddedMutableLiveData.value = true
        }
    }

    fun deleteItem(id: Long, context: Context) {
        uiScope.launch {
            ItemRepository.deleteItem(id, context)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
        itemsMutableLiveData = null
        itemMutableLiveData = null
    }

}