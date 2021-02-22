package com.example.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "student_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "SURNAME";
    public static final String COL_4 = "MARKS";

    /**Equivale ao comando SQL:      [CREATE]
     * CREATE DATABASE Student.db;
     *
     */
    public DatabaseHelper(Context context) { // Quando este construtor for chamado o banco de dados será criado. Sendo este método equivalente ao comando 'create database'.
        super(context, DATABASE_NAME, null, 1);
      
    }

    @Override
    public void onCreate(SQLiteDatabase db) {// Passando os comandos SQL tanto poderia ser feito do modo tradicional, quanto fazendo a concatenação das variaveis declaradas acima.
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, SURNAME TEXT, MARKS INTEGER)");
    }

    /**Equivale ao comando SQL:                   [DROP]
     * DROP DATABASE IF EXISTS Student.db;
     *
     * Neste caso como não é chamada na MainActivity não será executado,
     * diferentemente dos demais métodos.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DATABASE_NAME);
        onCreate(db);
    }
    /**Este método equivale ao comando SQL:  [INSERT INTO]
    * INSERT INTO student_table
    * (name, surname, marks)
    * VALUES
    * ('Ricardo', 'Souza', '34'); **/
    public boolean insertData(String name, String surname, String marks) {
        SQLiteDatabase db = this.getWritableDatabase(); //linha útil
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, marks);

        // este código só é possível pq o metodo .insert é do tipo long e retorana -1 caso falhe, e o numero de linhas afetadas caso de certo.
        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1)
            return false;
        else
            return true;

    }
    /**Equivalente ao comando SQL :        [SELECT]
     * SELECT * FROM student_table; */
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select *  from "+ TABLE_NAME, null);
        return res;
    }
    /**Equivalente ao comando SQL:       [UPDATE]
     * UPDATE student_table SET name='', surname='', marks='' WHERE id='' ;*/
    public boolean updateData(String id, String name, String surname, String marks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, marks);

        db.update(TABLE_NAME, contentValues, "id = ?", new String[] { id });

        return true;

    }
    /**Equivalente ao comando SQL :             [DELETE]
     * DELETE FROM student_table  WHERE id='' ; */
    public  Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(TABLE_NAME, "id = ?", new String[] { id });
    }

}
