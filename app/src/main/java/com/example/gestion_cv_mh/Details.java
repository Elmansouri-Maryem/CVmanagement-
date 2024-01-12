package com.example.gestion_cv_mh;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Details  extends AppCompatActivity {
    private DatabaseHelper Database;
    private TextView fullname, field, phone, email, competences, diplomes, experiences;

    Button buttonSupprimer , buttonModifier;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_eleve);
        Database = new DatabaseHelper(this);
        Intent intent = getIntent();
        int eleveId = intent.getIntExtra("eleveId", 0);

        Cursor eleve = Database.getEleveAndCVInfos(String.valueOf(eleveId));

// Initialize sets to store unique values
        HashSet<String> Diplomes = new HashSet<>();
        HashSet<String> Competences = new HashSet<>();
        HashSet<String> Experiences = new HashSet<>();

        if (eleve.moveToFirst()) {

            String Elevename = eleve.getString(eleve.getColumnIndex("Elevename"));
            String Elevefield = eleve.getString(eleve.getColumnIndex("Elevefield"));
            String Elevemail = eleve.getString(eleve.getColumnIndex("Elevemail"));
            String Elevephone = eleve.getString(eleve.getColumnIndex("Elevephone"));


            do {
                String DiplomeNom = eleve.getString(eleve.getColumnIndex("DiplomeNom"));
                Diplomes.add(DiplomeNom);
                String CompetenceNom = eleve.getString(eleve.getColumnIndex("CompeNom"));
                Competences.add(CompetenceNom);
                String ExperienceNom = eleve.getString(eleve.getColumnIndex("ExperienceNom"));
                Experiences.add(ExperienceNom);

            } while (eleve.moveToNext());


            String Diplomesinput = String.join("\n", Diplomes);
            String Competenceinput = String.join("\n", Competences);
            String Experienceinput = String.join("\n", Experiences);

            // Find TextView elements
            fullname = findViewById(R.id.textViewName);
            field = findViewById(R.id.textViewfield);
            email = findViewById(R.id.textViewEmail);
            phone = findViewById(R.id.textViewPhone);
            diplomes = findViewById(R.id.textViewDiplomes);
            competences = findViewById(R.id.textViewCompetences);
            experiences = findViewById(R.id.textViewExperiences);

            // Set TextView values
            fullname.setText(Elevename);
            field.setText(Elevefield);
            email.setText(Elevemail);
            phone.setText(Elevephone);
            diplomes.setText(Diplomesinput);
            competences.setText(Competenceinput);
            experiences.setText(Experienceinput);

        } else {
            System.out.println("No data found");
        }


        buttonSupprimer=findViewById(R.id.buttonSupprimer);
        buttonSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                int eleveId = intent.getIntExtra("eleveId", 0);
                Database.DeleteEleve(String.valueOf(eleveId));
                Intent intent2 = new Intent(Details.this, MainActivity.class);
                startActivity(intent2);
            }
        });
        buttonModifier=findViewById(R.id.buttonModifier);
        buttonModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                int studentId = intent.getIntExtra("eleveId", 0);
                Intent intent2 = new Intent(Details.this, UpdateEleve.class);
                intent2.putExtra("eleveId", studentId);
                startActivity(intent2);
            }

        });
    }

}


