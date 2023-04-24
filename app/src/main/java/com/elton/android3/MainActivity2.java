package com.elton.android3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    SQLiteDatabase db;
    ListView listView;
EditText search;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

search = findViewById(R.id.editTextTextPersonName8);
        listView = findViewById(R.id.listView);

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
showAll();

    }
    public void showAll(){
        Cursor cursor = db.rawQuery("SELECT * FROM Contacts", null);
        if (cursor.getCount() == 0){
            showMessage("Error","No contacts found in the address book!");
            return;
        }

        ArrayList<String> contactsList = new ArrayList<>();
        while(cursor.moveToNext()){
            String name = cursor.getString(0);
            String phone = cursor.getString(1);
            String email = cursor.getString(2);
            String street = cursor.getString(3);
            String city = cursor.getString(4);
            String state = cursor.getString(5);
            String zip = cursor.getString(6);

            String contact = "Name: " + name + "\nPhone: " + phone + "\nEmail: " + email + "\nAddress: " + street + ", " + city + ", " + state + " - " + zip;
            contactsList.add(contact);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactsList);
        listView.setAdapter(arrayAdapter);
    }
public void goToAddContact(View view){
    Intent intent = new Intent(this,MainActivity.class);
    startActivity(intent);
}
public void searchContact(View view){
        String s = search.getText().toString();
       Cursor  cursor = db.rawQuery("Select * from Contacts where NAME like ?",new String[]{s});
    StringBuilder sb= new StringBuilder();
while(cursor.moveToNext()){
    sb.append("Name: "+cursor.getString(0) + "\n")
            .append("Phone: "+cursor.getString(1)+"\n")
            .append("Email: "+cursor.getString(2))
            .append("Address: " + cursor.getString(3) + "\n\n");
}
showMessage("Result",sb.toString());
}
    public void deleteContact(View view){
        String s = search.getText().toString();
        db.execSQL("DELETE FROM Contacts WHERE NAME LIKE ?", new String[]{s});
        showMessage("Result", "Contact deleted successfully!");
        showAll();
    }
    public void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
}