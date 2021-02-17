package com.back4app.kotlin.back4appexample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListFragment : Fragment() {

    val dataList:MutableList<Data> = mutableListOf()
    var dataAdapter: DataAdapter? = null
    var dataRecyclerView: RecyclerView? = null
    private val dataViewModel: DataViewModel = DataViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView= inflater.inflate(R.layout.fragment_list, container, false)

        var allData:MutableList<Data> = mutableListOf()
        activity?.setTitle("List")
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true)
        dataRecyclerView = rootView.findViewById<RecyclerView>(R.id.list_rvData)
        val tvNoData=rootView.findViewById<TextView>(R.id.list_tvNoData)
        //val dataList=(activity as ListActivity).dataViewModel.dataLiveList
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
                Log.d("app","num elem: "+ allData.size)
                dataRecyclerView?.adapter = dataAdapter
                dataRecyclerView?.layoutManager = LinearLayoutManager(activity)
            }
        })
        dataViewModel.getAll()

        return rootView
    }

    override fun onResume() {
        super.onResume()
        dataViewModel.getAll()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = view?.findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            (activity as ListActivity).navHost.navController.navigate(R.id.action_listFragment_to_addFragment)
        }
    }

}