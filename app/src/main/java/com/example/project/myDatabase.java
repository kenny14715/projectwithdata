package com.example.project;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

class myDatabase extends SQLiteOpenHelper {
    ProgressDialog pd;
    private static final String TAG = myDatabase.class.getSimpleName();

    private Resources mResources;
    private static final String DATABASE_NAME = "Place.db";
    private static final int DATABASE_VERSION = 1;
    SQLiteDatabase db;
    Context context;

    public myDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mResources = context.getResources();
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DbContract.MenuEntry.TABLE_NAME +
                " (" + DbContract.MenuEntry.COLUMN_SNO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbContract.MenuEntry.COLUMN_NAME + " TEXT, " +
                DbContract.MenuEntry.COLUMN_AREA + " TEXT, " +
                DbContract.MenuEntry.COLUMN_LAT + " TEXT, " +
                DbContract.MenuEntry.COLUMN_LNG + " TEXT);";

        db.execSQL(query);
        Log.d(TAG, "Database Created Successfully" );

        try {
            readDataToDb(db);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        final String SQL = "DROP TABLE " + DbContract.MenuEntry.TABLE_NAME;
        db.execSQL(SQL);
    }

    private void readDataToDb(SQLiteDatabase db) throws IOException, JSONException {

        try {
            String jsonDataString = readJsonDataFromFile();//修改readJsonDataFromFile回傳值
            JSONArray menuItemsJsonArray = new JSONArray(jsonDataString);

            for (int i = 0; i < menuItemsJsonArray.length(); i++) {

                JSONObject spots = menuItemsJsonArray.getJSONObject(i);
                ContentValues spotValues = new ContentValues();

                spotValues.put(DbContract.MenuEntry.COLUMN_SNO, spots.getString(JsonField.SPOTS_ID));
                spotValues.put(DbContract.MenuEntry.COLUMN_NAME, spots.getString(JsonField.SPOTS_NAME));
                spotValues.put(DbContract.MenuEntry.COLUMN_AREA, spots.getString(JsonField.SPOTS_AREA));
                spotValues.put(DbContract.MenuEntry.COLUMN_LNG, spots.getString(JsonField.SPOTS_LNG));
                spotValues.put(DbContract.MenuEntry.COLUMN_LAT, spots.getString(JsonField.SPOTS_LAT));

                db.insert(DbContract.MenuEntry.TABLE_NAME, null, spotValues);

                Log.d(TAG, "Inserted Successfully " + spotValues);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private String readJsonDataFromFile() throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
            inputStream = mResources.openRawResource(R.raw.taichungspots);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return new String(builder);
    }

    public ArrayList<HashMap<String, String>> searchByname(String getName) { //以景點名稱搜尋特定資料
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        Cursor c = db.rawQuery(" SELECT * FROM " + DbContract.MenuEntry.TABLE_NAME
                + " WHERE spot_name =" + "'" + getName + "'", null);

        while (c.moveToNext()) {
            HashMap<String, String> spotValues = new HashMap<>();

            String id = c.getString(0);
            String name = c.getString(1);
            String area = c.getString(2);
            String lng = c.getString(3);
            String lat = c.getString(4);

            spotValues.put(DbContract.MenuEntry.COLUMN_SNO, id);
            spotValues.put(DbContract.MenuEntry.COLUMN_NAME, name);
            spotValues.put(DbContract.MenuEntry.COLUMN_AREA, area);
            spotValues.put(DbContract.MenuEntry.COLUMN_LNG, lng);
            spotValues.put(DbContract.MenuEntry.COLUMN_LAT, lat);
            arrayList.add(spotValues);
        }
        return arrayList;
    }
}
