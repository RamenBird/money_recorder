package com.great.ramenbird.timemoney

import android.app.DatePickerDialog
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import com.great.ramenbird.timemoney.DatabaseObject.MoneyRecord
import kotlinx.android.synthetic.main.activity_money_input.*
import java.util.*

class MoneyInputActivity : AppCompatActivity() {
    val path = "content://com.great.ramenbird.money/records"
    val uri = Uri.parse(path)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money_input)

        val id = intent.getLongExtra("id", -1);
        var update = !id.equals(-1L)
        val update_uri = Uri.parse(path + "/" + id)
        if (update) {
            val cursor = contentResolver.query(update_uri, null, null, null,
                    null)

            update = cursor.count > 0
            val lateObject = MoneyRecord.readFromCursor(cursor)

            if (lateObject != null) {
                occur_date.text = lateObject.occurTime
                amount_input.setText(String.format("%.2f", lateObject.amount))
                description_input.setText(lateObject.description)
            }
        }

        make_date_today.setOnClickListener { v: View ->
            val now = Calendar.getInstance()
            occur_date.text = String.format("%d-%02d-%02d",
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH) + 1,
                    now.get(Calendar.DAY_OF_MONTH))
        }
        date_pick1.setOnClickListener {
            val now = Calendar.getInstance()
            var datepickerdialog = DatePickerDialog(
                    this@MoneyInputActivity,
                    { datePicker: DatePicker, i: Int, i1: Int, i2: Int ->
                        occur_date.text = String.format("%d-%02d-%02d", i, i1 + 1, i2)
                    },
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            )
            datepickerdialog.show()
        }
        add.setOnClickListener { v: View ->
            if (occur_date.text.length == 0) {
                Toast.makeText(
                        this@MoneyInputActivity,
                        "Please input date",
                        Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (amount_input.text.length == 0) {
                Toast.makeText(
                        this@MoneyInputActivity,
                        "Please input amount",
                        Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val content_values = ContentValues()
            content_values.put(MoneyRecord.COLUMN_OCCUR_TIME, occur_date.text.toString())
            content_values.put(MoneyRecord.COLUMN_AMOUNT, amount_input.text.toString().toDouble())
            content_values.put(MoneyRecord.COLUMN_RECORDING_TIME, System.currentTimeMillis())

            if (description_input.text.length > 0)
                content_values.put(MoneyRecord.COLUMN_DESCRIPTION, description_input.text.toString()
                        .replace("\n", ""))

            if (update)
                contentResolver.update(update_uri, content_values, null, null)
            else
                contentResolver.insert(uri, content_values)
            finish()
        }
    }
}
