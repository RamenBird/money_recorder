package com.great.ramenbird.timemoney;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/1/22.
 */

public class CostRecordsFragment extends Fragment implements CursorContentAdapter.ActionProcessor {
    final Uri mUri = Uri.parse("content://com.great.ramenbird.money/records");
    final CursorContentAdapter mAdapter = new CursorContentAdapter(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cost_records, null);
        final RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        final Cursor cursor = getActivity().getContentResolver().query(mUri, null, null, null, null);
        mAdapter.updateCursor(cursor);
        recyclerView.setAdapter(mAdapter);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Cursor cursor = getActivity().getContentResolver().query(mUri, null, null, null, null);
                mAdapter.updateCursor(cursor);
            }
        });
        return view;
    }

    @Override
    public void remove(long id) {
        getActivity().getContentResolver().delete(mUri, "_id = ?", new String[]{
                id + ""
        });
        getView().findViewById(R.id.button).performClick();
    }

    @Override
    public void edit(long id) {
        startActivity(new Intent(getActivity(), MoneyInputActivity.class).putExtra("id", id));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.updateCursor(null);
    }
}
