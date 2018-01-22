package com.great.ramenbird.timemoney;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/1/22.
 */

public class PlainViewHolder extends RecyclerView.ViewHolder {
    final TextView mDateText;
    final TextView mAmountText;
    final TextView mDescriptionText;
    final Button mRemoveBtn;
    final Button mEditBtn;

    public PlainViewHolder(View itemView) {
        super(itemView);
        mDateText = itemView.findViewById(R.id.occur_date);
        mAmountText = itemView.findViewById(R.id.amount);
        mDescriptionText = itemView.findViewById(R.id.description);
        mRemoveBtn = itemView.findViewById(R.id.remove);
        mEditBtn = itemView.findViewById(R.id.edit);
    }
}
