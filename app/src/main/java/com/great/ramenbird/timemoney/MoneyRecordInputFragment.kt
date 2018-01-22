package com.great.ramenbird.timemoney

import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Administrator on 2018/1/21.
 */

class MoneyRecordInputFragment : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_date_picker, null);
    }

    override fun onResume() {
        super.onResume()
        dialog.setTitle("Record")
    }
}