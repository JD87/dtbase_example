package com.example.david.dtbase_example;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by david on 9/23/2017.
 */

public class ListDataActivity extends AppCompatActivity{

    private static final String TAG = "ListDataActivity";

    DatabaseHelper mDatabaseHelper;

    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);
        populateListview();
    }

    private void populateListview(){
        Log.d(TAG,"populateListView: Displaying data in the ListView");

        //get data and append to a list
        Cursor data = mDatabaseHelper.getData();
        List<Map<String, String>> mapData = new ArrayList<Map<String, String>>();


        while(data.moveToNext()){
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("title", data.getString(1));
            datum.put("sub", data.getString(2) + " , " + data.getString(3));
            //get value from the database in columns 1, 2 and 3
            //then add it to the List< Map< > >
            mapData.add(datum);
        }

        //create the list adapter and set the adapter
        SimpleAdapter simpleAdapter = new SimpleAdapter
                (this, mapData, android.R.layout.simple_list_item_2, new String[] {"title", "sub"}, new int[] {android.R.id.text1, android.R.id.text2});


        mListView.setAdapter(simpleAdapter);

        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap name = ((HashMap) parent.getItemAtPosition(position));
                String newname = name.get("title").toString();
                Log.d(TAG, "onItemClick: you clicked on " + newname);

                //get the ID associated to that name
                Cursor data = mDatabaseHelper.getItemID(newname);
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }

                if(itemID > -1){
                    Log.d(TAG, "onItemCLick: the ID is : " + itemID);
                    Intent editScreenIntent = new Intent(ListDataActivity.this, EditdataActivity.class);
                    editScreenIntent.putExtra("id", itemID);
                    editScreenIntent.putExtra("name", newname);
                    startActivity(editScreenIntent);
                }
                else
                    toastMessage("No ID associated to that name ");
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
