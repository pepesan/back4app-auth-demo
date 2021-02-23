package com.back4app.kotlin.back4appexample

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView

class DataAdapter(private val allData: MutableList<Data>, val dataActivity: ListActivity):RecyclerView.Adapter<DataAdapter.DataViewHolder>() {
    inner class DataViewHolder(view:View):RecyclerView.ViewHolder(view), View.OnClickListener{
        var name:TextView=view.findViewById(R.id.item_tvName)
        var itemId:TextView=view.findViewById(R.id.item_id)
        init {
            name.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            dataActivity.dataViewModel.getById(itemId.text.toString())
            dataActivity.dataViewModel.itemId = itemId.text.toString()
            Log.d("app","Item ID: "+ dataActivity.dataViewModel.itemId)
            val bundle= bundleOf("objectId" to this.itemId.text.toString())
            dataActivity.navHost.navController.navigate(R.id.action_listFragment_to_detailFragment, bundle)
        }

        fun setItem(data:Data){
            Log.d("app", "Data: $data")
            name.text=data.itemName
            itemId.text= data.objectId

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.setItem(allData[position])
    }

    override fun getItemCount(): Int {
        Log.d("app", "DataAdapter Size: "+ allData.size)
        return allData.size
    }
}