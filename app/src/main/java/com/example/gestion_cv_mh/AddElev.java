package com.example.gestion_cv_mh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class AddElev extends AppCompatActivity {
    private DatabaseHelper Database;
    private EditText  nameInput, fieldInput, emailInput, phoneInput;
    private Button ajouterButton, listerButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Database = new DatabaseHelper(this);
        //idInput = findViewById(R.id.id_input);
        nameInput = findViewById(R.id.name_input);
        fieldInput = findViewById(R.id.field_input);
        emailInput = findViewById(R.id.email_input);
        phoneInput = findViewById(R.id.phone_input);
        ajouterButton = findViewById(R.id.btn_Ajouter);
        AjoutEleve();
    }
    public void AjoutEleve() {
        Log.d("AjoutEleve", "AjoutEleve method called");
        ajouterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("AjoutEleve", "Ajouter button clicked");

                boolean inserted = Database.EleveInsertion(
                        nameInput.getText().toString(),
                        fieldInput.getText().toString(),
                        emailInput.getText().toString(),
                        phoneInput.getText().toString()
                );
                Log.d("AjoutEleve", "Database insertion result: " + inserted);
                if (inserted) {
                    Toast.makeText(AddElev.this, "Ajout Réussi!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddElev.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddElev.this, "Error lors de l'ajout!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}



