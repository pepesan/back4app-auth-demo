package com.back4app.kotlin.back4appexample

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders


class DetailFragment : Fragment() {

    var dataViewModel:DataViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //dataViewModel = ViewModelProviders.of(requireActivity()).get(DataViewModel::class.java)
        dataViewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        dataViewModel?.item?.observe(requireActivity(), {
            Log.d("app","Observed data: $it")
        })
        Log.d("app","detail itemid: "+ dataViewModel?.itemId)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("app","Detail bundle id: "+ savedInstanceState?.getString("name"))
        Log.d("app","Data: "+ dataViewModel?.item)
        Log.d("app","Data: "+ dataViewModel?.item?.value)
        Log.d("app","Data: "+ dataViewModel?.item?.value?.itemName)
    }

}