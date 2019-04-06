package com.example.david.dtbase_example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button btnAdd, btnViewdata;
    private EditText edtxt;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtxt = (EditText) findViewById(R.id.editText);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnViewdata = (Button) findViewById(R.id.btnView);
        mDatabaseHelper = new DatabaseHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = edtxt.getText().toString();
                float lat = 0, lon = 0;
                if (edtxt.length() != 0){
                    AddData(newEntry, lat, lon);
                    edtxt.setText("");
                }
                else
                    toastMessage("You must put something in the text field");

            }
        });

        btnViewdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });
    }

    public void AddData(String newEntry, float lat, float lon){
        boolean instertData = mDatabaseHelper.addData(newEntry, lat, lon);
        if (instertData)
            toastMessage("Data succesfully inserted.");
        else
            toastMessage("Something went wrong.");
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
