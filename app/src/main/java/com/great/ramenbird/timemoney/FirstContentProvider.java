package com.great.ramenbird.timemoney;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

import com.great.ramenbird.timemoney.DatabaseObject.MoneyRecord;

public class FirstContentProvider extends ContentProvider {
    private BaseDbOpenHelper mDbHelper;
    private static UriMatcher sUriMatcher;
    private static final int CODE_RECORDS = 1;
    private static final int CODE_SINGLE_RECORD = 2;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI("com.great.ramenbird.money", "records", CODE_RECORDS);
        sUriMatcher.addURI("com.great.ramenbird.money", "records/#", CODE_SINGLE_RECORD);
    }

    public FirstContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case CODE_RECORDS:
                return mDbHelper.getWritableDatabase().delete(MoneyRecord.Companion.getTABLE_NAME(),
                        selection, selectionArgs);
        }
        return -1;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (sUriMatcher.match(uri)) {
            case CODE_RECORDS:
                long id = mDbHelper.getWritableDatabase().insert("moneyrecord",
                        null,
                        values);
                if (id > 0)
                    return ContentUris.withAppendedId(uri, id);
            default:
                break;
        }

        throw new SQLException("Failed to insert row into" + uri);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new BaseDbOpenHelper(getContext(), null, null, -1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (sUriMatcher.match(uri)) {
            case CODE_RECORDS:
                return mDbHelper.getReadableDatabase().query(MoneyRecord.Companion.getTABLE_NAME(),
                        null, null, null, null, null, null);
            case CODE_SINGLE_RECORD:
                return mDbHelper.getReadableDatabase().query(MoneyRecord.Companion.getTABLE_NAME(),
                        null, "_id = ?", new String[]{uri.getLastPathSegment()},
                        null, null, null);
            default:
                break;
        }

        throw new SQLException("Can't query uri" + uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case CODE_RECORDS:
                return mDbHelper.getWritableDatabase().update(MoneyRecord.Companion.getTABLE_NAME(),
                        values, selection, selectionArgs);
            case CODE_SINGLE_RECORD:
                return mDbHelper.getWritableDatabase().update(MoneyRecord.Companion.getTABLE_NAME(),
                        values, "_id = ?", new String[]{uri.getLastPathSegment()});
            default:
                break;
        }

        throw new SQLException("Can't update uri" + uri);
    }
}
