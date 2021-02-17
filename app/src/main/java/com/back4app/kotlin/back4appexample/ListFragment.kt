package com.back4app.kotlin.back4appexample

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {

    val dataList:MutableList<Data> = mutableListOf()
    lateinit var dataAdapter:DataAdapter
    lateinit var dataRecyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView= inflater.inflate(R.layout.fragment_list, container, false)

        var allData:MutableList<Data> = mutableListOf()

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.list_rvData)
        val tvNoData=rootView.findViewById<TextView>(R.id.list_tvNoData)

        val dataList=(activity as ListActivity).dataViewModel.dataLiveList
        dataList.observe(activity as ListActivity, Observer { Data ->
            // Update the cached copy of the words in the adapter.
            Data?.let { allData=it }
            if(allData.size==0){
                tvNoData.visibility=View.VISIBLE
                recyclerView.visibility=View.GONE
            }
            else{
                tvNoData.visibility=View.GONE
                recyclerView.visibility=View.VISIBLE
                val adapter = DataAdapter(allData,activity as ListActivity)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(activity)
            }
        })

        return rootView
    }


}