package com.great.ramenbird.timemoney;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.great.ramenbird.timemoney.DatabaseObject.MoneyRecord;

/**
 * Created by Administrator on 2018/1/22.
 */

public class CursorContentAdapter extends RecyclerView.Adapter<PlainViewHolder> {
    private Cursor mCursor;
    private ActionProcessor mActionProcessor;

    interface ActionProcessor {
        void remove(long id);

        void edit(long id);
    }

    public CursorContentAdapter(ActionProcessor actionProcessor) {
        mActionProcessor = actionProcessor;
    }

    @Override
    public PlainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cursor_content,
                parent, false));
    }

    @Override
    public void onBindViewHolder(PlainViewHolder holder, final int position) {
        if (mCursor != null) {
            if (mCursor.moveToPosition(position)) {
                holder.mDateText.setText(mCursor.getString(mCursor.getColumnIndex(MoneyRecord.Companion.getCOLUMN_OCCUR_TIME())));
                holder.mAmountText.setText(String.format("%1$,.2f", mCursor.getDouble(mCursor.getColumnIndex(MoneyRecord.Companion.getCOLUMN_AMOUNT()))));
                holder.mDescriptionText.setText(mCursor.getString(mCursor.getColumnIndex(MoneyRecord.Companion.getCOLUMN_DESCRIPTION())));
                holder.mRemoveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCursor.moveToPosition(position)) {
                            new AlertDialog.Builder(v.getContext()).setTitle("Delete")
                                    .setMessage("Sure delete?").setPositiveButton("Fine", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    long id = mCursor.getLong(mCursor.getColumnIndex(BaseColumns._ID));
                                    mActionProcessor.remove(id);
                                }
                            }).setNegativeButton("Nuh", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                        }
                    }
                });
                holder.mEditBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        long id = mCursor.getLong(mCursor.getColumnIndex(BaseColumns._ID));
                        mActionProcessor.edit(id);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public void updateCursor(Cursor query) {
        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
        }

        mCursor = query;
        notifyDataSetChanged();
    }
}
