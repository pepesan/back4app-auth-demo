package com.back4app.kotlin.back4appexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.switchmaterial.SwitchMaterial
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.microedition.khronos.egl.EGLDisplay


class DetailFragment : Fragment() {

    val dataViewModel:DataViewModel by viewModels {DataViewModelFactory()}
    lateinit var etId:TextView
    lateinit var etDate:EditText
    lateinit var etItem:EditText
    lateinit var etAditionalInformation:EditText
    lateinit var sIsAvailable:SwitchMaterial
    lateinit var picker_button:Button
    lateinit var data:Data

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity?.setTitle("Detail")
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etId = view.findViewById(R.id.tvId)
        etDate = view.findViewById(R.id.etDate)
        etItem = view.findViewById(R.id.edtItem)
        etAditionalInformation = view.findViewById(R.id.edtAdditionalInformation)
        sIsAvailable = view.findViewById(R.id.swiAvailable)
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
                etDate.setText("${calendar.get(Calendar.DAY_OF_MONTH)}/ " +
                        "${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}")
            }
            datePicker.show(getParentFragmentManager(), "MyTAG")

            val posicion: String? = arguments?.getString("objectId")
            if (posicion != null) {
                dataViewModel.getById(posicion)
                dataViewModel.item.observe(requireActivity()) {
                    data = Data(it.objectId, it.itemName, it.additionalInformation, it.dateCommitment, it.isAvailable)
                    etId.setText(String.format("ID: ${data.objectId}"))
                    etDate.setText(data.dateCommitment.toString())
                    etItem.setText(data.itemName)
                    etAditionalInformation.setText(data.additionalInformation)
                    data.isAvailable?.let { sIsAvailable.isChecked = it }
                }
            }

            view.findViewById<Button>(R.id.btnUpdate).setOnClickListener {
                if (etDate.text.toString() == data.dateCommitment.toString() &&
                        etItem.text.toString() == data.itemName &&
                        etAditionalInformation.text.toString() == data.additionalInformation &&
                        sIsAvailable.isChecked == data.isAvailable) Toast.makeText(activity, "Tienes que modificar algún dato", Toast.LENGTH_SHORT).show()
                else {
                    data.itemName = etItem.text.toString()
                    data.additionalInformation = etAditionalInformation.text.toString()
                    data.dateCommitment = convertStringToData(etDate.text.toString())
                    data.isAvailable = sIsAvailable.isChecked
                    dataViewModel.update(data)

                }
            }

        }
    }

    fun convertStringToData(getDate: String?): Date? {
        var today: Date? = null
        val simpleDate = SimpleDateFormat("dd/MM/yyyy")
        try {
            today = simpleDate.parse(getDate!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return today
    }
}