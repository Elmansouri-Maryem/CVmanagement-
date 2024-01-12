package com.example.gestion_cv_mh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EleveCV.db";
    private static final int DATABASE_VERSION = 1;
    private String TABLE_NAME = "Eleve";
    private String COLUMN_ID = "id";
    private String COLUMN_FULLName = "fullname";
    private String COLUMN_FIELD = "field";
    private String COLUMN_EMAIL = "email";
    private String COLUMN_PHONE = "phone";
    public String COLUMN_DIPLOME = "Diplômes";
    public String COLUMN_COMPETENCES = "Competences";
    public String COLUMN_EXPERIENCES = "Experiences";

    //Table Diplomes
    public static final String TABLE_NAMEX = "Diplômes";
    public static final String COLUMN_D1 = "ID";
    public static final String COLUMN_D2 = "Nom";
    public static final String COLUMN_D3 = "description";

    //Table Competences
    public static final String TABLE_NAMEY = "Competences";
    public static final String COLUMN_C1 = "CompID";
    public static final String COLUMN_C2 = "CompNom";
    public static final String COLUMN_C3 = "CompDescription";

    //Table Experiences
    public static final String TABLE_NAMEEX = "Experiences";
    public static final String COLUMN_EX1 = "ExperienceID";
    public static final String COLUMN_EX2 = "ExperienceNom";
    public static final String COLUMN_EX3 = "ExperienceDescription";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String EleveTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FULLName + " TEXT, " +
                COLUMN_FIELD + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PHONE + " TEXT);";
        db.execSQL(EleveTable);

        String DiplomeTable = "CREATE TABLE " + TABLE_NAMEX + " (" +
                COLUMN_D1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_D2 + " TEXT, " +
                COLUMN_D3 + " TEXT);";

        db.execSQL(DiplomeTable);

        String EleveDiplomeTable = "CREATE TABLE EleveDiplomeTable (" +
                "EleveID INTEGER, " +
                "DiplomeID INTEGER, " +
                "FOREIGN KEY(EleveID) REFERENCES Eleve(id), " +
                "FOREIGN KEY(DiplomeID) REFERENCES Diplômes(" + COLUMN_D1 + "));";
        db.execSQL(EleveDiplomeTable);

        String CompetencesTable = "CREATE TABLE " + TABLE_NAMEY + " (" +
                COLUMN_C1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_C2 + " TEXT, " +
                COLUMN_C3 + " TEXT);";

        db.execSQL(CompetencesTable);
        String EleveCompetencesTable = "CREATE TABLE EleveCompetences (" +
                "EleveID INTEGER, " +
                "CompetencesID INTEGER, " +
                "FOREIGN KEY(EleveID) REFERENCES Eleve(id), " +
                "FOREIGN KEY(CompetencesID) REFERENCES Competences(" + COLUMN_C1 + "));";
        db.execSQL(EleveCompetencesTable);

        String ExperiencesTable = "CREATE TABLE " + TABLE_NAMEEX + " (" +
                COLUMN_EX1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EX2 + " TEXT, " +
                COLUMN_EX3 + " TEXT);";
        db.execSQL(ExperiencesTable);

        String EleveExperiencesTable = "CREATE TABLE EleveExperiences (" +
                "EleveID INTEGER, " +
                "ExperienceID INTEGER, " +
                "FOREIGN KEY(EleveID) REFERENCES Eleve(id), " +
                "FOREIGN KEY(ExperienceID) REFERENCES Experiences(" + COLUMN_EX1 + "));";
        db.execSQL(EleveExperiencesTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMEX);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMEY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMEEX);
        onCreate(db);
    }

    public boolean EleveInsertion(String fullname, String field, String email, String phone) {
        Log.d("Insertion", "EleveInsertion called");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FULLName, fullname);
        contentValues.put(COLUMN_FIELD, field);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PHONE, phone);

        long result = db.insert(TABLE_NAME, null, contentValues);
        // Insert a random Diplome name

        for(int i=0;i<3;i++){
            int randomID = (int) (Math.random() * 4)+1;
            if (result != -1) {
                contentValues = new ContentValues();
                contentValues.put("EleveID", result);
                contentValues.put("DiplomeID", randomID);
                db.insert("EleveDiplomeTable", null, contentValues);

                contentValues = new ContentValues();
                contentValues.put("EleveID", result);
                contentValues.put("CompetencesID", randomID);
                db.insert("EleveCompetences", null, contentValues);

                contentValues = new ContentValues();
                contentValues.put("EleveID", result);
                contentValues.put("ExperienceID", randomID);
                db.insert("EleveExperiences", null, contentValues);

            }
        }

        return result != -1;
    }


    public Cursor readAllEleves() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT id as _id, fullname, field, email, phone FROM " + TABLE_NAME, null);
    }

    public Cursor getEleveAndCVInfos(String ID) {
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT " +
                TABLE_NAME + "." + COLUMN_ID + " AS EleID, " +
                COLUMN_FULLName + " AS Elevename, " +
                COLUMN_FIELD + " AS Elevefield, " +
                COLUMN_EMAIL + " AS Elevemail, " +
                COLUMN_PHONE + " AS Elevephone, " +
                TABLE_NAMEX + "." + COLUMN_D1 + " AS DiplomeID, " +
                COLUMN_D2 + " AS DiplomeNom, " +
                COLUMN_D3 + " AS DiplomeDescription, " +
                TABLE_NAMEY + "." + COLUMN_C1 + " AS CompeID, " +
                COLUMN_C2 + " AS CompeNom, " +
                COLUMN_C3 + " AS CompDescription, " +
                TABLE_NAMEEX + "." + COLUMN_EX1 + " AS ExperienceID, " +
                COLUMN_EX2 + " AS ExperienceNom, " +
                COLUMN_EX3 + " AS ExperienceDescription " +
                "FROM " + TABLE_NAME + " " +
                "LEFT JOIN EleveDiplomeTable ON " + TABLE_NAME + "." + COLUMN_ID + " = EleveDiplomeTable.EleveID " +
                "LEFT JOIN " + TABLE_NAMEX + " ON EleveDiplomeTable.DiplomeID = " + TABLE_NAMEX + "." + COLUMN_D1 + " " +
                "LEFT JOIN EleveCompetences ON " + TABLE_NAME + "." + COLUMN_ID + " = EleveCompetences.EleveID " +
                "LEFT JOIN " + TABLE_NAMEY + " ON EleveCompetences.CompetencesID = " + TABLE_NAMEY + "." + COLUMN_C1 + " " +
                "LEFT JOIN EleveExperiences ON " + TABLE_NAME + "." + COLUMN_ID + " = EleveExperiences.EleveID " +
                "LEFT JOIN " + TABLE_NAMEEX + " ON EleveExperiences.ExperienceID = " + TABLE_NAMEEX + "." + COLUMN_EX1 + " " +
                "WHERE " + TABLE_NAME + "." + COLUMN_ID + " = ?";

        String[] selectionArgs = {ID};
        return database.rawQuery(query, selectionArgs);
    }
    public void DeleteEleve(String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[]{ID});
    }
    public boolean updateEleveDetails(String ID,String fullname,String field ,String email, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(COLUMN_FULLName,fullname);
        contentValues.put(COLUMN_FIELD,field);
        contentValues.put(COLUMN_EMAIL,email);
        contentValues.put(COLUMN_PHONE,phone);
        db.update(TABLE_NAME,contentValues,"id = ?",new String[]{ID});
        return true;
    }
    public Cursor getEleve(String ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " +
                TABLE_NAME + "." + COLUMN_ID + " AS EleID, " +
                COLUMN_FULLName + " AS Elevename, " +
                COLUMN_FIELD + " AS Elevefield, " +
                COLUMN_EMAIL + " AS Elevemail, " +
                COLUMN_PHONE + " AS Elevephone " +
                "FROM " + TABLE_NAME + " " +
                "WHERE " + TABLE_NAME + "." + COLUMN_ID + " = ?";
        String[] selectionArgs = {ID};
        return db.rawQuery(query, selectionArgs);
    }
}
