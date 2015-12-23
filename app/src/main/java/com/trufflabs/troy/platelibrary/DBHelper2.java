package com.trufflabs.troy.platelibrary;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Stores the licence and names. Initially reads in the data from txt file
 * in the assets directory.
 * @author Troy
 * @version 12/22/15.
 */
public class DBHelper2 {
    //Static constants
    private static final String TAG = "DBHelper2";
    private static final String FILE = "LicenceData.csv";

    //Class fields
    private List<Driver> drivers;
    private Context mContext;

    /**
     * Constructor, creats a DBHelper2 object.
     * @param context
     */
    public DBHelper2(Context context) {
        mContext = context;
        drivers = new LinkedList<Driver>();
        addLicences();

        Log.d(TAG, "in constructor");
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
                    Log.i(TAG, "adding to List: " + lic + " : " + stu);
                    drivers.add(new Driver(lic, stu));
                }
            }
            is.close();

            Log.d(TAG, "Done adding licences");
        } catch(IOException e) {
            Log.d(TAG, "licence data file was NOT found");
        }
    }



    /**
     * Queries the table for the licence provided, returning the stu name
     * @param theLicence full or partial licence looking for
     * @return the names and licence for drivers found
     */
    public List<Driver> getStuName(String theLicence){
        List<Driver> results = new LinkedList<Driver>();

        for(Driver d : drivers) {
            if (d.getLicencePlate().toLowerCase().contains(theLicence.toLowerCase())) {
                results.add(d);
            }
        }

        // return student name
        return results;
    }


    /**
     * Queries the table for the name provided, returning the plates
     * @param theName name looking for
     * @return plates found (or 'no plates found')
     */
    public List<Driver> getPlates(String theName){
        List<Driver> results = new LinkedList<Driver>();

        for(Driver d : drivers) {
            if (d.getName().toLowerCase().contains(theName.toLowerCase())) {
                results.add(d);
            }
        }

        // return student name
        return results;
    }

    /**
     * Number of students available in the table.
     * @return Number of students available in the table.
     */
    public int studentCount() {

        return drivers.size();
    }

}
