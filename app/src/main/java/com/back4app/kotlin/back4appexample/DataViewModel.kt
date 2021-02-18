package com.back4app.kotlin.back4appexample

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.parse.ParseObject
import com.parse.ParseQuery


class DataViewModel(): ViewModel() {

    val itemList: MutableLiveData<MutableList<Data>> by lazy {
        MutableLiveData()
    }
    val item: MutableLiveData<Data> by lazy {
        MutableLiveData()
    }

    fun getAll(){
        var dataList:MutableList<Data> = mutableListOf()
        // Configure Query
        val query = ParseQuery.getQuery<ParseObject>("reminderList")

        // Sorts the results in ascending order by the itemName field
        query.orderByAscending("itemName")
        query.findInBackground { objects, e ->
            if (e == null) {
                // Adding objects into the Array

                for (i in objects.indices) {
                    objects[i].getString("itemName")?.let {dataList.add(Data(it))}
                }
                itemList.setValue(dataList)
            }
        }
        Log.d("app","dataList: "+ dataList)
    }
}

class DataViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DataViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}