package com.back4app.kotlin.back4appexample

import android.icu.text.DateFormat
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.switchmaterial.SwitchMaterial
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AddFragment : Fragment() {
    val dataViewModel:DataViewModel by viewModels {DataViewModelFactory()}

    private var itemName: EditText? = null
    private var itemAdd: EditText? = null
    private var itemDate: CalendarView? = null
    private var isAvailable: SwitchMaterial? = null
    private var formatterDate: Date? = null
    private var picker_button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        activity?.setTitle("Add")
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemName = view.findViewById(R.id.edtItem)
        itemAdd = view.findViewById(R.id.edtAdditionalInformation)
        itemDate = view.findViewById(R.id.calendarView)
        isAvailable = view.findViewById(R.id.swiAvailable)
        picker_button = view.findViewById(R.id.dpText)
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
                /*picker_button?.text = "${calendar.get(Calendar.DAY_OF_MONTH)}- " +
                        "${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.YEAR)}"*/
                formatterDate =  convertStringToData(calendar.get(Calendar.DAY_OF_MONTH).toString() + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR))
                picker_button?.text= formatterDate.toString()
            }

            datePicker.show(getParentFragmentManager(), "MyTAG")
        }
        /*
        itemDate?.setOnDateChangeListener(OnDateChangeListener { calendarView, year, month, dayOfMonth ->
            val getDate = dayOfMonth.toString() + "/" + (month + 1) + "/" + year
            formatterDate = convertStringToData(getDate)
        })
         */
    }
    override fun onPrepareOptionsMenu(menu: Menu){
        super.onPrepareOptionsMenu(menu)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_edit){
            save()
        }
        if(item.itemId ==android.R.id.home) {
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
        (activity as AppCompatActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(false)
        return super.onOptionsItemSelected(item)

    }

    private fun save() {
        //Validating the log in data
        //Validating the log in data
        var validationError = false

        val validationErrorMessage = StringBuilder("Please, ")
        if (isEmptyText(itemName!!)) {
            validationError = true
            validationErrorMessage.append("insert a name")
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

    private fun saveObject() {
        val data= Data(null, itemName!!.text.toString(), itemAdd!!.text.toString(), formatterDate!!, isAvailable!!.isChecked)
        dataViewModel.insert(data)
        (activity as ListActivity).navHost.navController.navigate(R.id.action_addFragment_to_listFragment)
    }

    fun convertStringToData(getDate: String?): Date? {
        var today: Date? = null
        val simpleDate = SimpleDateFormat("dd/MM/yyyy")
        //val simpleDate=DateFormat.getDateInstance(DateFormat.YEAR_MONTH_DAY)
        try {
            today = simpleDate.parse(getDate!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return today
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

}