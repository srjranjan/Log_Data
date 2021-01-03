package com.srj.logdata


import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.*
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.Utils
import com.google.android.material.datepicker.MaterialDatePicker
import com.srj.logdata.Database.compObject.col_Date
import com.srj.logdata.Database.compObject.col_Drive
import com.srj.logdata.Database.compObject.col_Shift
import com.srj.logdata.Database.compObject.col_mcc
import com.srj.logdata.Database.compObject.tableName
import kotlinx.android.synthetic.main.activity_main2.*



class MainActivity2 : AppCompatActivity() {

    private var spin: String? = null

    // var i by Delegates.notNull<Int>()
    private val dbhelper = Database(this)
    private val dataSet = ArrayList<IBarDataSet>()

    //private var mdateSetListener: DatePickerDialog.OnDateSetListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
       // Utils.init(this)

        val tvdate1: TextView = findViewById(R.id.tvdate1)
        val tvdate2: TextView = findViewById(R.id.tvdate2)
      //  val graph = this.findViewById<LineChart>(R.id.graph)

        // spinnerset()

        tvdate1.setOnClickListener {
            datepicker()
        }
        tvdate2.setOnClickListener {
            datepicker1()
        }

        showButton()

    }

    private fun showButton() {
        button3.setOnClickListener {
            val barDataSet = BarDataSet(getDataValues(), "Temperature")

           // barDataSet.values = getDataValues()
            barDataSet.label = "Mcc Room Temp"
            barDataSet.formLineWidth = 2F
            barDataSet.setColors(ColorTemplate.colorWithAlpha(1, 78))
            dataSet.clear()
            dataSet.add(barDataSet)
            val lineData = BarData(dataSet)
            graph.clear()
            graph.data = lineData
            val XAxis = graph.xAxis
            XAxis.valueFormatter=IndexAxisValueFormatter()
            XAxis.position= XAxisPosition.BOTTOM
            XAxis.setDrawGridLines(false)
            XAxis.setDrawAxisLine(false)
            XAxis.granularity=1f
            XAxis.labelCount = dataSet.size
            XAxis.labelRotationAngle= 270F
            graph.animateY(2000)

            graph.invalidate()

        }
    }

    private fun getDataValues(): MutableList<BarEntry>? {
        val db = dbhelper.readableDatabase
        val date1 = tvdate1.text.toString()
        val date2 = tvdate2.text.toString()
        val shift = "A"
        var dataVals = ArrayList<BarEntry>()
        val month= ArrayList<BarEntry>()
        try {

            val cursor =
                db.rawQuery("select $col_Drive,$col_mcc From $tableName Where $col_Date between '$date1' and '$date2' and $col_Shift='$shift'",
                    null)

            for (i in 0 until cursor.count) {

                cursor.moveToNext()
                dataVals.add(BarEntry(cursor.getFloat(cursor.getColumnIndex(col_Drive)),
                    cursor.getFloat(cursor.getColumnIndex(
                        col_mcc))))
            }
            cursor.close()

        }
        catch (e:Exception){
            Toast.makeText(this, e.cause.toString(), Toast.LENGTH_SHORT).show()
        }
        return dataVals
    }


    private fun datepicker() {

        val builder: MaterialDatePicker.Builder<*> = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Select Date")
        val picker: MaterialDatePicker<*> = builder.build()
        picker.show(supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            tvdate1.text = picker.headerText

        }


        /* val cal = Calendar.getInstance()
         val date = cal.get(Calendar.DAY_OF_MONTH)
         val year = cal.get(Calendar.YEAR)
         val month = cal.get(Calendar.MONTH)
         val dialog = DatePickerDialog(this, android.R.style.ThemeOverlay_Material_Dialog_Alert,
             mdateSetListener, year, month, date)
         dialog.window?.setBackgroundDrawable(ColorDrawable(Color.DKGRAY))
         dialog.show()
         mdateSetListener = DatePickerDialog.OnDateSetListener { _, year: Int, month, date ->
             var month = month
             month += 1
             val dat = "$date-$month-$year"
             tvdate1.text = dat


         }
 */
    }

    private fun datepicker1() {

        val builder: MaterialDatePicker.Builder<*> = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Select Date")
        val picker: MaterialDatePicker<*> = builder.build()
        picker.show(supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            tvdate2.text = picker.headerText

        }


        /*  val cal = Calendar.getInstance()
          val date = cal.get(Calendar.DAY_OF_MONTH)
          val year = cal.get(Calendar.YEAR)
          val month = cal.get(Calendar.MONTH)
          val dialog = DatePickerDialog(this, android.R.style.ThemeOverlay_Material_Dialog_Alert,
              mdateSetListener, year, month, date)
          dialog.window?.setBackgroundDrawable(ColorDrawable(Color.DKGRAY))
          dialog.show()
          mdateSetListener = DatePickerDialog.OnDateSetListener { _, year: Int, month, date ->
              var month = month
              month += 1
              val dat = "$date-$month-$year"
              tvdate2.text = dat


          }
  */
    }


    private fun spinnerset(): String? {

        val array = arrayOf("Choose Shift", "A", "B", "C")
        if (spinner2 != null) {
            /*val adapter: ArrayAdapter<String> = ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line, shift
            )*/
            val adapter: ArrayAdapter<String> =
                ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, array)
            spinner2?.adapter = adapter

        }
        var spinnervalue: String? = null
        spinner2?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                spinnervalue = spinner2?.selectedItemPosition.toString()
                Toast.makeText(this@MainActivity2, spinnervalue, Toast.LENGTH_SHORT).show()


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        return spinnervalue
    }
}