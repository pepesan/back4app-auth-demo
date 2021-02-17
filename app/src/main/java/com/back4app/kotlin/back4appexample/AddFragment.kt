package com.back4app.kotlin.back4appexample

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.parse.ParseObject
import com.parse.ParseUser
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AddFragment : Fragment() {

    private var itemName: EditText? = null
    private var itemAdd: EditText? = null
    private var itemDate: CalendarView? = null
    private var isAvailable: Switch? = null
    private var formatterDate: Date? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.setTitle("Add")
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemName = view.findViewById(R.id.edtItem);
        itemAdd = view.findViewById(R.id.edtAdditionalInformation);
        itemDate = view.findViewById(R.id.calendarView);
        isAvailable = view.findViewById(R.id.swiAvailable);
        itemDate?.setOnDateChangeListener(OnDateChangeListener { calendarView, year, month, dayOfMonth ->
            val getDate = dayOfMonth.toString() + "/" + (month + 1) + "/" + year
            formatterDate = convertStringToData(getDate)
        })
    }
    override fun onPrepareOptionsMenu(menu: Menu){
        super.onPrepareOptionsMenu(menu)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_add){
            save()
        }
        if(item.itemId ==android.R.id.home) {
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
        (activity as AppCompatActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(false);
        return super.onOptionsItemSelected(item)

    }

    private fun save() {
        //Validating the log in data
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

    private fun saveObject() {
        // Configure Query
        // Configure Query
        val reminderList = ParseObject("reminderList")

        // Store an object

        // Store an object
        reminderList.put("itemName", itemName!!.text.toString())
        reminderList.put("additionalInformation", itemAdd!!.text.toString())
        reminderList.put("dateCommitment", formatterDate!!)
        reminderList.put("isAvailable", isAvailable!!.isChecked)
        //reminderList.put("userId", ParseUser.getCurrentUser())

        // Saving object

        // Saving object
        reminderList.saveInBackground {
            if (it == null) {
                //vuelve
                Log.d("app", "reminderList: ${reminderList.objectId}")
                (activity as ListActivity).navHost.navController.navigate(R.id.action_addFragment_to_listFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    it.message.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }

    fun convertStringToData(getDate: String?): Date? {
        var today: Date? = null
        val simpleDate = SimpleDateFormat("dd/MM/yyyy")
        try {
            today = simpleDate.parse(getDate)
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