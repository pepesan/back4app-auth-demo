package com.back4app.kotlin.back4appexample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListFragment : Fragment() {


    var dataAdapter: DataAdapter? = null
    var dataRecyclerView: RecyclerView? = null
    val dataViewModel:DataViewModel by viewModels {DataViewModelFactory()}
    lateinit var tvNoData:TextView
    //var dataViewModel:DataViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //dataViewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        //dataViewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        //dataViewModel = ViewModelProvider(viewModelStore,dataViewModelFactory!!)
        //        .get(DataViewModel::class.java)

        val rootView= inflater.inflate(R.layout.fragment_list, container, false)

        activity?.setTitle("List")
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        var allData:MutableList<Data> = mutableListOf()

        dataRecyclerView = rootView.findViewById(R.id.list_rvData)
        tvNoData=rootView.findViewById<TextView>(R.id.list_tvNoData)

        dataViewModel.getAll()
        dataViewModel.itemList.observe(activity as ListActivity, Observer { Data ->
            Log.d("app", "data: " + Data)
            // Update the cached copy of the words in the adapter.
            Data?.let { allData = it }
            if (allData.size == 0) {
                tvNoData.visibility = View.VISIBLE
                dataRecyclerView?.visibility = View.GONE
            } else {
                tvNoData.visibility = View.GONE
                dataRecyclerView?.visibility = View.VISIBLE
                dataAdapter = DataAdapter(allData, activity as ListActivity)
                //Log.d("app","num elem: "+ allData.size)
                dataRecyclerView?.adapter = dataAdapter
                dataRecyclerView?.layoutManager = LinearLayoutManager(activity)
            }
        })

        return rootView
    }

    /*override fun onResume() {
        super.onResume()
        dataViewModel.getAll()
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = view.findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            dataViewModel.itemId= ""
            (activity as ListActivity).navHost.navController.navigate(R.id.action_listFragment_to_addFragment)
        }
    }

}