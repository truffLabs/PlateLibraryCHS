package com.trufflabs.troy.platelibrary;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * While for memory purposes and efficency a better route, for the short term have moved to an
 * in memory approach DBHelper2.
 * @author Troy
 * @version 12/22/15.
 */
public class DBHelper extends SQLiteOpenHelper{
    private static final String TAG = "DBHelper";
    private static final String FILE = "LicenceData.csv";
    private static final int DATABASE_VERSION = 8;
    // Database Name here
    private static final String DATABASE_NAME = "LicenceDatabase";
    // tasks table name
    private static final String TABLE_NAME = "LicencePlatesTable";
    // tasks Table Columns names
    private static final String LICENCE = "Licence";
    private static final String STU_NAME = "Name";


    private SQLiteDatabase dbase;
    private Context mContext;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        dbase = getWritableDatabase();


        Log.d(TAG, "in constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        dbase = db;
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS " + TABLE_NAME );
        sql.append(" ( ");
        sql.append(LICENCE);
        sql.append(" TEXT, ");
        sql.append(STU_NAME);
        sql.append(" TEXT)");

        Log.d(TAG, "onCreate executing: " + sql.toString());
        db.execSQL(sql.toString());

        addLicences();
    }

    /**
     * Reads from the local resources file to populate the database.
     */
    private void addLicences(){
        try{
            InputStream is = mContext.getResources().getAssets().open(FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            Log.d(TAG, "LicenceData file WAS found, about to add licences");

            String line;
            if (is!=null) {
                reader.readLine(); //to toss the title line
                while ((line = reader.readLine()) != null) {
                    int dashIndex = line.indexOf(',');
                    String lic = line.substring(0, dashIndex);
                    String stu = line.substring(dashIndex + 1);
                    Log.i(TAG, "adding to database: " + lic + " : " + stu);
                    addLICENCE(lic, stu);
                }
            }
            is.close();

            Log.d(TAG, "Done adding licences");
        } catch(IOException e) {
            Log.d(TAG, "licence data file was NOT found");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    /**
     * Adds a licences to the table
     * @param the_licence the licence to add
     * @param the_stu_name the student name of the LICENCE
     */
    private void addLICENCE(String the_licence, String the_stu_name) {

        ContentValues values = new ContentValues();
        values.put(LICENCE, the_licence);
        values.put(STU_NAME, the_stu_name);
        // Inserting Row
        dbase.insert(TABLE_NAME, null, values);
    }


    /**
     * Queries the table for the licence provided, returning the stu name
     * @param theLicence LICENCE looking for
     * @return STU_NAMEinition found (or 'no STU_NAMEinition found')
     */
    public Map<String,String> getStuName(String theLicence){
        String selectQuery = "SELECT " + STU_NAME + ", " + LICENCE +
                " FROM " + TABLE_NAME +
                " WHERE " + LICENCE +" = '" + theLicence + "'";


        Log.i(TAG, "SQL query: " + selectQuery);

        dbase = this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);

        Map<String, String> results = new HashMap<String, String>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            String resultLicence = cursor.getString(0);
            String resultName = cursor.getString(1);
            Log.i(TAG, "results: " + resultLicence + " - " + resultName);
            results.put(resultLicence, resultName);
            cursor.moveToNext();
        }
        Log.i(TAG, "size of result: " + results.size());
        if (results.size() == 0) {
            results.put("None", "No results found");
        }
        cursor.close();
        // return student name
        return results;
    }


    /**
     * Queries the table for the name provided, returning the plates
     * @param theName name looking for
     * @return plates found (or 'no plates found')
     */
    public Map<String,String> getPlates(String theName){

        Log.i(TAG, "pulling from: " + studentCount() + " students");

        String selectQuery = "SELECT " + STU_NAME + ", " + LICENCE +
                " FROM " + TABLE_NAME +
                " WHERE " + STU_NAME + " = '" + theName + "'";

        Log.i(TAG, "SQL query: " + selectQuery);

        dbase = this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);

        Map<String, String> results = new HashMap<String, String>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            String resultLicence = cursor.getString(0);
            String resultName = cursor.getString(1);
            Log.i(TAG, "results: " + resultLicence + " - " + resultName);
            results.put(resultLicence, resultName);
            cursor.moveToNext();
        }
        Log.i(TAG, "size of result: " + results.size());


        if (results.size() == 0) {
            results.put("None", "No results found");
        }
        cursor.close();
        // return student name
        return results;
    }

    /**
     * Number of students available in the table.
     * @return Number of students available in the table.
     */
    public int studentCount() {
        Cursor mCount = dbase.rawQuery("Select count(*) from " + TABLE_NAME, null);
        mCount.moveToFirst();
        int count = mCount.getInt(0);
        return count;
    }
}
