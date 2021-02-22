package com.back4app.kotlin.back4appexample

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels


class DetailFragment : Fragment() {

    val dataViewModel:DataViewModel by viewModels {DataViewModelFactory()}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataViewModel.item.observe(activity!!, {
            Log.d("app","Observed data: $it")
        })
        Log.d("app","Data: "+ dataViewModel.item)
        Log.d("app","Data: "+ dataViewModel.item.value)
        Log.d("app","Data: "+ dataViewModel.item.value?.itemName)
    }

}