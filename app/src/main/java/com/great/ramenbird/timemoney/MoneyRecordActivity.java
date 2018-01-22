package com.great.ramenbird.timemoney;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MoneyRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_record);

        Uri uri = Uri.parse("content://com.great.ramenbird.money/records");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
    }
}
