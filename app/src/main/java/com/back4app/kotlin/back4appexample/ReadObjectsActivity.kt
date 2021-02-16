package com.back4app.kotlin.back4appexample

import android.R
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import java.util.*


class ReadObjectsActivity : AppCompatActivity() {
    var dataList = ArrayList<String>()
    var myArray = arrayOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*
        setContentView(R.layout.activity_read_objects)

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            val intent = Intent(this, CreateObjectActivity::class.java)
            startActivity(intent)
        }
        findObjects()

         */
    }
    /*
    private fun findObjects() {
        myArray = arrayOf()
        val listView = findViewById<View>(R.id.listviewA) as ListView

        // Configure Query
        val query = ParseQuery.getQuery<ParseObject>("reminderList")

        // Query Parameters
        query.whereEqualTo("userId", ParseUser.getCurrentUser())

        // Sorts the results in ascending order by the itemName field
        query.orderByAscending("itemName")
        query.findInBackground { objects, e ->
            if (e == null) {
                // Adding objects into the Array
                for (i in objects.indices) {
                    val element = objects[i].getString("itemName")
                    dataList.add(element.toString())
                }
            } else {
            }
            myArray = dataList.toTypedArray()
            val list =
                ArrayList(Arrays.asList(*myArray))
            val adapterList = ArrayAdapter(
                this,
                R.layout.simple_list_item_single_choice,
                myArray
            )
            listView.adapter = adapterList
            listView.onItemClickListener =
                OnItemClickListener { adapter, v, position, id ->
                    val value = adapter.getItemAtPosition(position) as String

                    //Alert showing the options related with the object (Update or Delete)
                    val builder =
                        AlertDialog.Builder(this@ReadObjects)
                            .setTitle("$value movie")
                            .setMessage("What do you want to do?")
                            .setPositiveButton(
                                "Delete"
                            ) { dialog, which ->
                                dataList.removeAt(position)
                                deleteObject(value)
                                myArray = dataList.toTypedArray()
                                val adapterList =
                                    ArrayAdapter(
                                        this@ReadObjects,
                                        R.layout.simple_list_item_single_choice,
                                        myArray
                                    )
                                listView.adapter = adapterList
                            }
                            .setNeutralButton(
                                "Update"
                            ) { dialog, which ->
                                val intent =
                                    Intent(this, UpdateObject::class.java)
                                //Send string value to UpdateObject Activity with putExtra Method
                                intent.putExtra("objectName", value)
                                startActivity(intent)
                            }.setNegativeButton(
                                "Cancel"
                            ) { dialog, which -> dialog.cancel() }
                    val ok = builder.create()
                    ok.show()
                }
        }
    }

    // Delete object
    private fun deleteObject(value: String) {
        val query = ParseQuery.getQuery<ParseObject>("reminderList")

        // Query parameters based on the item name
        query.whereEqualTo("itemName", value)
        query.findInBackground { `object`, e ->
            if (e == null) {
                //Delete based on the position
                `object`[0].deleteInBackground { e ->
                    if (e == null) {
                    } else {
                        Toast.makeText(
                            applicationContext,
                            e.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    e.message.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

     */
}