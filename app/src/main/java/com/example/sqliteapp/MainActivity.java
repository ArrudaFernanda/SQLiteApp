package com.example.sqliteapp;

import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb; //Para usar o banco de dados esta variavel da classe deve ser iniciada

    EditText editName, editSurname, editMarks, editTextId;
    Button btnAddData, btnvewAll, btnviewUpdate, btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this); // e aqui é chamado o banco de dados criado, podendo ser utilizado.

        editName = findViewById(R.id.editText_name);
        editSurname = findViewById(R.id.editText_surname);
        editMarks = findViewById(R.id.editText_Marks);
        editTextId = findViewById(R.id.editTextId);
        btnAddData = findViewById(R.id.button_add);
        btnvewAll  = findViewById(R.id.button_viewAll);
        btnviewUpdate = findViewById(R.id.button_update);
        btnDelete = findViewById(R.id.button_delete);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }

    public  void AddData(){
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean isInserted = myDb.insertData(editName.getText().toString(), editSurname.getText().toString(), editMarks.getText().toString());

               if (isInserted == true)
                   Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
               else
                   Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public  void viewAll(){
        btnvewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();

                if(res.getCount() == 0){ // Significa que não há dados para mostrar.
                    //Show message
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Id :"+ res.getString(0)+ "\n");
                    buffer.append("Name :"+ res.getString(1)+ "\n");
                    buffer.append("Surname :"+ res.getString(2)+ "\n");
                    buffer.append("Marks :"+ res.getString(3)+ "\n\n");
                }
                // Show all data
                showMessage("Data", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public  void UpdateData(){
        btnviewUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.updateData(editTextId.getText().toString(), editName.getText().toString(),
                        editSurname.getText().toString(), editMarks.getText().toString());

                if(isUpdate == true) {
                    Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public  void DeleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(editTextId.getText().toString());

                if(deletedRows > 0)  {
                    Toast.makeText(MainActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Data not deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}