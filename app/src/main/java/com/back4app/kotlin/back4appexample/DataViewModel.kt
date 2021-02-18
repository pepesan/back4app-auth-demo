package com.back4app.kotlin.back4appexample

import android.util.Log
import android.widget.Toast
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
                    val data: Data = Data()
                    data.objectId= objects[i].getString("objectId")
                    data.itemName= objects[i].getString("itemName")
                    dataList.add(data)
                }
                itemList.setValue(dataList)
            }
        }
        Log.d("app","dataList: "+ dataList)
    }

    fun insert(data: Data){
        // Configure Query
        // Configure Query
        val reminderList = ParseObject("reminderList")

        // Store an object

        // Store an object
        reminderList.put("itemName", data.itemName!!)
        reminderList.put("additionalInformation", data.additionalInformation!!)
        reminderList.put("dateCommitment", data.dateCommitment!!)
        reminderList.put("isAvailable",data.isAvailable!!)
        //reminderList.put("userId", ParseUser.getCurrentUser())
        // Saving object
        reminderList.saveInBackground {
            if (it == null) {
                //vuelve
                Log.d("app", "reminderList: ${reminderList.objectId}")
                data.objectId= reminderList.objectId
                item.setValue(data)
            } else {
                Log.d("app",  it.message.toString())
            }

        }
    }
    fun getById(id: String){

    }
    fun update(data: Data){

    }
    fun deleteById(id: String){

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