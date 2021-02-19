package com.back4app.kotlin.back4appexample

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.parse.GetCallback
import com.parse.ParseObject
import com.parse.ParseQuery
import java.text.ParseException


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
        Log.d("app", "dataList: " + dataList)
    }

    fun insert(data: Data){
        // Configure Query
        // Configure Query
        val newItem = ParseObject("reminderList")

        // Store an object

        // Store an object
        newItem.put("itemName", data.itemName!!)
        newItem.put("additionalInformation", data.additionalInformation!!)
        newItem.put("dateCommitment", data.dateCommitment!!)
        newItem.put("isAvailable", data.isAvailable!!)
        //reminderList.put("userId", ParseUser.getCurrentUser())
        // Saving object
        newItem.saveInBackground {
            if (it == null) {
                //vuelve
                Log.d("app", "reminderList: ${newItem.objectId}")
                data.objectId= newItem.objectId
                item.setValue(data)
                //getById(data.objectId!!)
            } else {
                Log.d("app", it.message.toString())
            }

        }

    }
    fun getById(id: String){
        //Configure Query
        val query = ParseQuery.getQuery<ParseObject>("reminderList");
        // Query Parameters
        query.whereEqualTo("objectId", id);
        // How we need retrive exactly one result we can use the getFirstInBackground method
        query.getFirstInBackground{ parseObject,parseException ->
            if (parseException == null) {
                val data: Data= Data()
                data.objectId = id
                data.dateCommitment=parseObject.getDate("dateCommitment")
                data.additionalInformation=parseObject.getString("additionalInformation")
                data.itemName=parseObject.getString("itemName")
                data.isAvailable=parseObject.getBoolean("isAvailable")
                data.dateCommitment=parseObject.getDate("dateCommitment")
                data.dateCommitment=parseObject.getDate("dateCommitment")
                item.setValue(data)
                Log.d("app", "GetByID $id: $data")
            } else {
                Log.d("app", "Get ItemById Error: "+ parseException.message.toString())
            }
        }
    }
    fun update(data: Data){
        //Configure Query
        val query = ParseQuery.getQuery<ParseObject>("reminderList");
        // Query Parameters
        query.whereEqualTo("objectId", data.objectId);
        // How we need retrive exactly one result we can use the getFirstInBackground method
        query.getFirstInBackground{ parseObject,parseException ->
            if (parseException == null) {

                parseObject.put("dateCommitment",data.dateCommitment!!)
                parseObject.put("additionalInformation",data.additionalInformation!!)
                parseObject.put("isAvailable",data.isAvailable!!)
                parseObject.put("itemName",data.itemName!!)
                parseObject.saveInBackground{
                    if (it==null){
                        item.setValue(data)
                        Log.d("app", "Update By Id ${data.objectId}: $data")
                    }else{
                        Log.d("app", "Fail to Update By Id ${data.objectId}: Error: "+ it.message)
                    }
                }

            } else {
                Log.d("app", "Get ItemById Error: "+ parseException.message.toString())
            }
        }
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