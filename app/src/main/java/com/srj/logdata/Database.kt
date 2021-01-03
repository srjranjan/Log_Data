package com.srj.logdata

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class Database(context: Context) : SQLiteOpenHelper(
    context, DATABASE, null, VERSION
) {
    companion object compObject {
        private const val DATABASE = "LOG_DETAILS.db"
        private const val VERSION = 1
        const val tableName = "RECORD_DETAILS"
        const val col_Date = "Date"
        const val col_Shift = "Shift"
        const val col_Production = "Production"
        const val col_Delay = "Delay"
        const val col_mcc = "MccRoom"
        const val col_Drive = "Drive_Room"
        const val col_automation = "Automation_room"
        const val col_time = "Time"
        const val col_chiller_unit = "Chillerunittemp"
        const val col_sample = "Sample"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql =
            "create table $tableName($col_Date Date,$col_time Time, $col_Shift STRING, $col_Production STRING, $col_Delay STRING, $col_mcc DECIMAL(4,2), $col_Drive DECIMAL(4,2), $col_automation DECIMAL(4,2), $col_chiller_unit DECIMAL(4,2), $col_sample DECIMAL(4,4))"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }


   /* fun getData(context: Context, date1:String,date2: String, shift: String): ArrayList<retrieve> {
        var expnse = ArrayList<retrieve>()
        //var query =
        val db = this.readableDatabase
        try {
            val cursor =
                db.rawQuery("Select $col_mcc, $col_Date From $tableName Where $col_Date Between $date1 and $date2",
                    null)
            if (cursor != null) {
                val expense = retrieve()
                while (cursor.moveToNext()) {

                    expense.mcctemparray = arrayListOf(cursor.getString(cursor.getColumnIndex(
                        col_mcc)))
                    expense.dateArray =
                        arrayListOf(cursor.getString(cursor.getColumnIndex(col_Date)))
                }
                expnse.add(expense)

            }
            cursor.close()
            db.close()
        }
        catch(e:Exception){
            Toast.makeText(context, e.message,Toast.LENGTH_SHORT).show()
        }


        return expnse
        }*/
        fun writedata(
       context: Context,
       date: String,
       shift: String,
       time: String,
       production: String,
       delay: String,
       driveTemp: String,
       mccTemp: String,
       automationTemp: String,
       chillerTemp: String,
       sample: String,

       ) {
            val cv = ContentValues()
            val db = this.writableDatabase
            cv.put(col_Date, date.toString())
            cv.put(col_time,time.toString())
            cv.put(col_Shift, shift)
            cv.put(col_Production, production)
            cv.put(col_Delay, delay)
            cv.put(col_Drive, driveTemp)
            cv.put(col_mcc, mccTemp)
            cv.put(col_automation, automationTemp.toString())
            cv.put(col_chiller_unit, chillerTemp.toString())
            cv.put(col_sample, sample)

            try {
                db.insert(tableName, null, cv)
                Toast.makeText(context, "Data inserted", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()

            }
        }
    }