package com.srj.logdata

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat.getTimeInstance
import java.util.*

@Suppress("NAME_SHADOWING")
class MainActivity : AppCompatActivity() {

    private lateinit var mTimeSetListener: TimePickerDialog.OnTimeSetListener
    lateinit var spinnerr: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            saveData()
        }
       spinnerSetup()
        etDate.setOnClickListener {
            DatePick()
        }
        timepick.setOnClickListener {
            timepicker()
        }
        button2.setOnClickListener {
            val intent=Intent(this@MainActivity,MainActivity2::class.java)
            startActivity(intent)
        }

    }

    private fun saveData() {

        val date = etDate.text.toString()
        val shift = spinnerr
        val time = timepick.text.toString()
        val production = etProduction.text.toString()
        val delay = etDelay.text.toString()
        val driveTemp = etDriveRoom.text.toString()
        val mccTemp = etMcc.text.toString()
        val automationTemp = etAutomation.text.toString()
        val chillerTemp = etChiller.text.toString()
        val sample = etSample.text.toString()
        when{
            date.toString().isEmpty() -> Toast.makeText(this, "please insert date", Toast.LENGTH_SHORT).show()
            shift.isEmpty()-> Toast.makeText(this, "shift is empty", Toast.LENGTH_SHORT).show()
            time.toString().isEmpty()-> Toast.makeText(this, "time is empty", Toast.LENGTH_SHORT).show()
            production.isEmpty() -> Toast.makeText(this, "production is empty", Toast.LENGTH_SHORT)
                .show()
            delay.isEmpty() -> Toast.makeText(this, "delay is empty", Toast.LENGTH_SHORT)
                .show()
            driveTemp.toString().isEmpty() -> Toast.makeText(this, "drive  Room Temperature is empty", Toast.LENGTH_SHORT)
                .show()
            mccTemp.toString().isEmpty() -> Toast.makeText(this, "MCC Room temperature is empty", Toast.LENGTH_SHORT)
                .show()
            automationTemp.toString().isEmpty() -> Toast.makeText(this, "automation room temperature is empty", Toast.LENGTH_SHORT)
                .show()
            chillerTemp.toString().isEmpty() -> Toast.makeText(this,
                "Chiller unit Temperature is empty",
                Toast.LENGTH_SHORT).show()
            sample.toString().isEmpty() -> Toast.makeText(this, "sample value is empty", Toast.LENGTH_SHORT)
                .show()
            else->{
                val db=Database(this)
                db.writedata(this,date,shift,time,production,delay,driveTemp,mccTemp,automationTemp,chillerTemp,sample)


            }
        }


    }

    @SuppressLint("SimpleDateFormat")
    private fun timepicker() {
        val calendar = Calendar.getInstance()
        // val seconds =Calendar.SECOND
        mTimeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            //calendar.set(Calendar.SECOND,seconds)
            timepick.text = getTimeInstance().format(calendar.time)
        }
        TimePickerDialog(
            this, mTimeSetListener,
            calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
            true
        ).show()
    }


    private fun DatePick() {
        val builder:MaterialDatePicker.Builder<*> = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Select Date")
        val picker:MaterialDatePicker<*> = builder.build()
        picker.show(supportFragmentManager,picker.toString())
        picker.addOnPositiveButtonClickListener{
            etDate.text=picker.headerText

        }
       /* val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(
            this,
            android.R.style.Theme_DeviceDefault_Dialog_Alert,
            mdateSetListener,
            year,
            month,
            day
        )
        dialog.window
            ?.setBackgroundDrawable(ColorDrawable(Color.DKGRAY))
        dialog.show()

        mdateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                var month = month
                 month += 1

                val date = "$day-$month-$year"

                etDate.text = date
            }*/

    }


    private fun spinnerSetup(){
        val shift = arrayOf("Choose Shift","A","B","C")

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter: ArrayAdapter<String> = ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line, shift
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    spinnerr = parent.getItemAtPosition(position).toString()
                    Toast.makeText(applicationContext,
                         shift[position], Toast.LENGTH_SHORT).show()

                }

                override fun onNothingSelected(parent: AdapterView<*>) =
// write code to perform some action
                    Unit
            }
        }
    }


}