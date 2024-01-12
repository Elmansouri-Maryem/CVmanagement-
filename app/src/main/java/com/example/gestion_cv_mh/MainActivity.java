package com.example.gestion_cv_mh;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ListView listViewEleve;
    private Button addBtn;
    private DatabaseHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addBtn = findViewById(R.id.add_btn);
            DB = new DatabaseHelper(this);
            Cursor students =DB.readAllEleves();
            listViewEleve =  findViewById(R.id.listViewEleve);

        String[] fromColumns = { "fullname", "field", "email", "phone" };
        int[] toViews = { R.id.textViewName, R.id.textViewfield , R.id.textViewEmail, R.id.textViewPhone};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.student_template, students, fromColumns, toViews, 0);
        listViewEleve.setAdapter(adapter);


        listViewEleve.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) parent.getItemAtPosition(position);
        int eleveId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        Intent intent = new Intent(MainActivity.this, Details.class);
        intent.putExtra("eleveId", eleveId);
        startActivity(intent);
                                                    }
                                                }

        );

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddElev.class);
                startActivity(intent);
            }
        });
    }

}
