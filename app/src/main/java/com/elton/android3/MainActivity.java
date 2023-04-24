package com.elton.android3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public SQLiteDatabase db;
    EditText name,phone,email,street,city,state,zip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.editTextTextPersonName);
        phone = findViewById(R.id.editTextTextPersonName2);
        email = findViewById(R.id.editTextTextPersonName3);
        street = findViewById(R.id.editTextTextPersonName4);
        city = findViewById(R.id.editTextTextPersonName5);
        state = findViewById(R.id.editTextTextPersonName6);
        zip = findViewById(R.id.editTextTextPersonName7);
        db = openOrCreateDatabase("AddressBook.db",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Contacts ("
                + "Name TEXT PRIMARY KEY,"
                + "PHONE INTEGER,"
                + "EMAIL TEXT,"
                + "STREET TEXT,"
                + "CITY TEXT,"
                + "STATE TEXT,"
                + "ZIP INTEGER)"
        );

    }
    public void addContact(View view){
        if (!name.getText().toString().isEmpty()
                && !phone.getText().toString().isEmpty()
                && !email.getText().toString().isEmpty()
                && !street.getText().toString().isEmpty()
                && !city.getText().toString().isEmpty()
                && !state.getText().toString().isEmpty()
                && !zip.getText().toString().isEmpty()) {
            String nameStr = name.getText().toString();
            String phoneStr = phone.getText().toString();
            String emailStr = email.getText().toString();
            String streetStr = street.getText().toString();
            String cityStr = city.getText().toString();
            String stateStr = state.getText().toString();
            String zipStr = zip.getText().toString();
            db.execSQL("Insert into Contacts values(?,?,?,?,?,?,?)", new String[]{nameStr, phoneStr, emailStr, streetStr, cityStr, stateStr, zipStr});
       showMessage("Success","Contact added successfully!");
            Intent intent =  new Intent(this,MainActivity2.class);
            startActivity(intent);
        }else{
showMessage("Error","Please enter all the informations!");
        }

    }
        public void showMessage(String title, String message){
            new AlertDialog.Builder(this)
                    .setCancelable(true)
                    .setTitle(title)
                    .setMessage(message)
                    .show();
        }
}
