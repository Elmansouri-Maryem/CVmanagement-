package com.example.gestion_cv_mh;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateEleve extends AppCompatActivity {

    private DatabaseHelper database;
    private int eleveId;

    private EditText fullname, field, email, phone,id;
    private Button btnModifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        database = new DatabaseHelper(this);
        id = findViewById(R.id.id_input2);
        fullname = findViewById(R.id.name_input2);
        field = findViewById(R.id.field_input2);
        email = findViewById(R.id.email_input2);
        phone = findViewById(R.id.phone_input2);

        Intent intent = getIntent();
        eleveId = intent.getIntExtra("eleveId", 0);
        Cursor eleve = database.getEleve(String.valueOf(eleveId));

        if (eleve.moveToFirst()){
            fullname.setText(eleve.getString(eleve.getColumnIndex("Elevename")));
            field.setText(eleve.getString(eleve.getColumnIndex("Elevefield")));
            email.setText(eleve.getString(eleve.getColumnIndex("Elevemail")));
            phone.setText(eleve.getString(eleve.getColumnIndex("Elevephone")));
        }
        btnModifier= findViewById(R.id.btn_Modifier);
        btnModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEleve();
            }
        });
    }
    public void updateEleve() {
        String fullName = fullname.getText().toString();
        String Field = field.getText().toString();
        String Email = email.getText().toString();
        String Phone = phone.getText().toString();

        if (database.updateEleveDetails(String.valueOf(eleveId), fullName, Field, Email, Phone)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

}
