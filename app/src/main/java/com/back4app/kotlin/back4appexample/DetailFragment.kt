package com.back4app.kotlin.back4appexample

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.switchmaterial.SwitchMaterial
import org.w3c.dom.Text
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class DetailFragment : Fragment() {

    //var dataViewModel:DataViewModel? = null
    var id:String=""
    val dataViewModel:DataViewModel by viewModels {DataViewModelFactory()}
    private var itemName: EditText? = null
    private var itemAdd: EditText? = null
    lateinit var itemDate:EditText
    private var isAvailable: SwitchMaterial? = null
    private var formatterDate: Date? = null
    private var picker_button: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.setTitle("Detail")
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        //dataViewModel = ViewModelProviders.of(requireActivity()).get(DataViewModel::class.java)
        //dataViewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        /*dataViewModel?.item?.observe(requireActivity(), {
            Log.d("app","Observed data: $it")
        })
        Log.d("app","detail itemid: "+ dataViewModel?.itemId)*/
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemName = view.findViewById(R.id.edtItem)
        itemAdd = view.findViewById(R.id.edtAdditionalInformation)
        itemDate=view.findViewById(R.id.etDate)
        isAvailable = view.findViewById(R.id.swiAvailable)
        picker_button = view.findViewById(R.id.dpText)

        id= arguments?.getString("objectId") ?:""
        view.findViewById<TextView>(R.id.tvId).setText(String.format("ID: $id"))
        if(id!="") dataViewModel.getById(id)

        dataViewModel.item?.observe(requireActivity()){
            itemName?.setText(it?.itemName)
            itemAdd?.setText(it?.additionalInformation)
            itemDate.setText(it.dateCommitment.toString())
            formatterDate=it.dateCommitment
            isAvailable?.isChecked= it?.isAvailable!!
        }


        /*if (!dataViewModel?.itemId.equals("")){
            Log.d("app","No está vacío")
            dataViewModel?.item?.observe(requireActivity()){
                itemName?.setText(it?.itemName)
                itemAdd?.setText(it?.additionalInformation)
                picker_button?.setText(it.dateCommitment.toString())
                isAvailable?.isChecked= it?.isAvailable!!
            }
        }*/
        picker_button?.setOnClickListener {
            // Create the date picker builder and set the title
            val builder = MaterialDatePicker.Builder.datePicker()

            // create the date picker
            val datePicker = builder.build()

            // set listener when date is selected
            datePicker.addOnPositiveButtonClickListener {

                // Create calendar object and set the date to be that returned from selection
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                calendar.time = Date(it)
                formatterDate=Date(it)
                /*picker_button?.text = "${calendar.get(Calendar.DAY_OF_MONTH)}- " +
                        "${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.YEAR)}"*/
                //formatterDate =  convertStringToData(calendar.get(Calendar.DAY_OF_MONTH).toString() + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR))
                //etDate.setText(formatterDate.toString())
                itemDate.setText(String.format(calendar.get(Calendar.DAY_OF_MONTH).toString() + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR)))
            }
            datePicker.show(getParentFragmentManager(), "MyTAG")
        }
    }

    /*fun convertStringToData(getDate: String?): Date? {
        var today: Date? = null
        val simpleDate = SimpleDateFormat("dd/MM/yyyy")
        try {
            today = simpleDate.parse(getDate!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return today
    }*/

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_delete){
            delete()
        }
        if(item.itemId == R.id.action_edit){
            save()
        }
        if(item.itemId ==android.R.id.home) {
            findNavController().navigate(R.id.action_detailFragment_to_listFragment)
        }
        (activity as AppCompatActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(false)
        return super.onOptionsItemSelected(item)

    }

    private fun save() {
            //Validating the log in data
            var validationError = false

            val validationErrorMessage = StringBuilder("Please, ")
            if (isEmptyText(itemName!!)) {
                validationError = true
                validationErrorMessage.append("insert an name")
            }
            if (isEmptyDate()) {
                if (validationError) {
                    validationErrorMessage.append(" and ")
                }
                validationError = true
                validationErrorMessage.append("select a Date")
            }
            validationErrorMessage.append(".")
            if (validationError) {
                Toast.makeText(requireContext(), validationErrorMessage.toString(), Toast.LENGTH_LONG)
                    .show()
                return
            } else {
                saveObject()
            }

    }
    private fun isEmptyText(text: EditText): Boolean {
        return if (text.text.toString().trim { it <= ' ' }.length > 0) {
            false
        } else {
            true
        }
    }

    private fun isEmptyDate(): Boolean {
        return if (formatterDate.toString() !== "null") {
            false
        } else {
            true
        }
    }
    private fun saveObject() {
        val data = Data(id, itemName!!.text.toString(), itemAdd!!.text.toString(), formatterDate!!, isAvailable!!.isChecked)
        dataViewModel.update(data)
        (activity as ListActivity).navHost.navController.navigate(R.id.action_detailFragment_to_listFragment)
    }

    private fun delete() {
        dataViewModel.deleteById(id)
        (activity as ListActivity).navHost.navController.navigate(R.id.action_detailFragment_to_listFragment)
    }
}