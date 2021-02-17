package com.back4app.kotlin.back4appexample

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseObject
import com.parse.ParseUser
import java.text.SimpleDateFormat
import java.util.*


class CreateObject : AppCompatActivity() {
    private var itemName: EditText? = null
    private var itemAdd: EditText? = null
    private var itemDate: CalendarView? = null
    private var create_button: Button? = null
    private var isAvailable: Switch? = null
    private var formatterDate: Date? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_object)
        itemName = findViewById<View>(R.id.edtItem) as EditText
        itemAdd = findViewById<View>(R.id.edtAdditionalInformation) as EditText
        itemDate = findViewById<View>(R.id.calendarView) as CalendarView
        create_button = findViewById(R.id.btnCreate)
        isAvailable = findViewById<View>(R.id.swiAvailable) as Switch
        // Get Date from CalendarView
        itemDate!!.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            val getDate = (dayOfMonth.toString() + "/" + (month + 1) + "/" + year)
            formatterDate = convertStringToData(getDate)
        }
        create_button.setOnClickListener(View.OnClickListener { //Validating the log in data
            var validationError = false
            val validationErrorMessage = StringBuilder("Please, ")
            if (isEmptyText(itemName)) {
                validationError = true
                validationErrorMessage.append("insert an name")
            }
            if (isEmptyDate) {
                if (validationError) {
                    validationErrorMessage.append(" and ")
                }
                validationError = true
                validationErrorMessage.append("select a Date")
            }
            validationErrorMessage.append(".")
            if (validationError) {
                Toast.makeText(
                    this@CreateObject,
                    validationErrorMessage.toString(),
                    Toast.LENGTH_LONG
                ).show()
                return@OnClickListener
            } else {
                saveObject()
            }
        })
    }

    private fun isEmptyText(text: EditText?): Boolean {
        return if (text!!.text.toString().trim { it <= ' ' }.length > 0) {
            false
        } else {
            true
        }
    }

    private val isEmptyDate: Boolean
        private get() = if (formatterDate.toString() !== "null") {
            false
        } else {
            true
        }

    fun saveObject() {
        // Configure Query
        val reminderList = ParseObject("reminderList")

        // Store an object
        reminderList.put("itemName", itemName!!.text.toString())
        reminderList.put("additionalInformation", itemAdd!!.text.toString())
        reminderList.put("dateCommitment", formatterDate!!)
        reminderList.put("isAvailable", isAvailable!!.isChecked)
        reminderList.put("userId", ParseUser.getCurrentUser())

        // Saving object
        reminderList.saveInBackground { e ->
            if (e == null) {
                val intent = Intent(this@CreateObject, ReadObjects::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    applicationContext,
                    e.message.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    companion object {
        fun convertStringToData(getDate: String?): Date? {
            var today: Date? = null
            val simpleDate = SimpleDateFormat("dd/MM/yyyy")
            try {
                today = simpleDate.parse(getDate)
            } catch (e: java.text.ParseException) {
                e.printStackTrace()
            }
            return today
        }
    }
}