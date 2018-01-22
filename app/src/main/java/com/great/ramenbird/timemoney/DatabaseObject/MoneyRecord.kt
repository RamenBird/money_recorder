package com.great.ramenbird.timemoney.DatabaseObject

import android.database.Cursor
import java.lang.Exception

/**
 * Created by Administrator on 2018/1/22.
 */
data class MoneyRecord(val recordingTime: Long, val amount: Double,
                       val occurTime: String, val description: String?) {
    companion object {
        val COLUMN_RECORDING_TIME = "recordingtime"
        val COLUMN_AMOUNT = "amount"
        val COLUMN_OCCUR_TIME = "occurtime"
        val COLUMN_DESCRIPTION = "description"
        val TABLE_NAME = "moneyrecord"

        fun readFromCursor(cursor : Cursor) : MoneyRecord? {
            try {
                cursor.moveToFirst()
                return MoneyRecord(
                        cursor.getLong(cursor.getColumnIndex(COLUMN_RECORDING_TIME)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_OCCUR_TIME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                )
            } catch (e : Exception) {
                e.printStackTrace()
            }
            return null
        }
    }
}