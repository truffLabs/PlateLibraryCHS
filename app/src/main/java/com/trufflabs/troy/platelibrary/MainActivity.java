package com.trufflabs.troy.platelibrary;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    //database to pull data from textfile
    private DBHelper2 mPlateDB;

    //where user types search
    private EditText mPlateSearchText;

    //to populate with students
    private ListView mListView;
    private ArrayAdapter mAdapter;

    /**
     * Initializes all variables and prepares for interaction.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlateDB = new DBHelper2(this);
        mPlateSearchText = (EditText) findViewById(R.id.plateSearchText);

        mListView = (ListView) findViewById(R.id.stuNameListView);


        //Use array adapter to populate lower area (currently empty)
        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new LinkedList<String>());
        mListView.setAdapter(mAdapter);
    }

    /**
     * Currently unused
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Currently not handled
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when the search plate button clicked.
     * Pulls text from field and then searches for licence.
     * Populates text field below.
     * @param view View that the button was clicked in.
     */
    public void searchPlate(View view) {
        Activity activity = this;
        String plate = mPlateSearchText.getText().toString();
        List<Driver> results = mPlateDB.getStuName(plate);

//        //Convert map data to a list
//        final List<String> data = new LinkedList<String>();
//        for(Driver d: results) {
//            data.add(key + " - " + results.get(key));
//        }

        //Use array adapter to populate lower area
        mAdapter = new ArrayAdapter<Driver>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                results);
        mListView.setAdapter(mAdapter);



        //TODO
        //Magic of attaching each of the list Items to the alert dialog.
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//                DefinitionDialogFragment definitionFrag = new DefinitionDialogFragment();
//
//                String word = words.get(position);
//                String def = mVocabDB.getDefinition(word);
//
//                Bundle args = new Bundle();
//                args.putString(DefinitionDialogFragment.DEFINITION, def);
//                args.putString(DefinitionDialogFragment.WORD, word);
//                definitionFrag.setArguments(args);
//
//                definitionFrag.show(getFragmentManager(), "definition");
//            }
//        });

    }
    /**
     * Called when the search name button clicked.
     * Pulls text from field and then searches for a name.
     * Populates text field below.
     * @param view View that the button was clicked in.
     */
    public void searchName(View view) {
        Activity activity = this;
        String name = mPlateSearchText.getText().toString();
        List<Driver> results = mPlateDB.getPlates(name);

//        //Convert map data to a list
//        final List<String> data = new LinkedList<String>();
//        for(String key: results.keySet()) {
//            data.add(key + " - " + results.get(key));
//        }

        mAdapter = new ArrayAdapter<Driver>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                results);
        mListView.setAdapter(mAdapter);


        //TODO
        //Magic of attaching each of the list Items to the alert dialog.
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//                DefinitionDialogFragment definitionFrag = new DefinitionDialogFragment();
//
//                String word = words.get(position);
//                String def = mVocabDB.getDefinition(word);
//
//                Bundle args = new Bundle();
//                args.putString(DefinitionDialogFragment.DEFINITION, def);
//                args.putString(DefinitionDialogFragment.WORD, word);
//                definitionFrag.setArguments(args);
//
//                definitionFrag.show(getFragmentManager(), "definition");
//            }
//        });

    }
}
