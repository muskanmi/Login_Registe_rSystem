package com.example.loginregistersystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_register extends AppCompatActivity {
    EditText Email, Password, Name;
    Button Register;
    String NameHolder, EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDatabaseQueryHolder;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String F_Result = "Not_Found";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Register = (Button)findViewById(R.id.buttonRegister);
        Email=(EditText)findViewById(R.id.editEmail);
        Password =(EditText)findViewById(R.id.editPassword);
        Name = (EditText)findViewById(R.id.editName);
        sqLiteHelper = new SQLiteHelper(this);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabaseBuild();
                SQLiteTableBuild();
                CheckEditTextStatus();
                CheckingEmailAlreadyExistsOrNot();
                EmptyEditTextAfterDataInsert();
            }
        });
    }
    public void SQLiteDatabaseBuild() {
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
    public void SQLiteTableBuild() {
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + SQLiteHelper.TABLE_NAME + "(" + SQLiteHelper.TABLE_Column_ID + " PRIMARY KEY AUTOINCREMENT NOT NULL, " + SQLiteHelper.TABLE_Column_1_Name + " VARCHAR, " + SQLiteHelper.TABLE_Column_2_Email+ " VARCHAR, " + SQLiteHelper.TABLE_Column_3_Password + " VARCHAR);");
    }
    public void InsertDataIntoSQLiteDatabase() {
        if(EditTextEmptyHolder == true) {
            SQLiteDatabaseQueryHolder = " INSERT INTO "+SQLiteHelper.TABLE_NAME+" (name,email,password) VALUES('"+NameHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"');";
            sqLiteDatabaseObj.execSQL(SQLiteDatabaseQueryHolder);
            sqLiteDatabaseObj.close();
            Toast.makeText(activity_register.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(activity_register.this, "PLEASE FILL All THE REQUIRED FIELDS.", Toast.LENGTH_SHORT).show();
        }
    }
    public void EmptyEditTextAfterDataInsert() {
        Name.getText().clear();
        Email.getText().clear();
        Password.getText().clear();
    }
    public void CheckEditTextStatus() {
        NameHolder = Name.getText().toString();
        EmailHolder = Email.getText().toString();
        PasswordHolder= Password.getText().toString();

        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder) || TextUtils.isEmpty(PasswordHolder)) {
            EditTextEmptyHolder = false;
        }
        else {
            EditTextEmptyHolder = true;
        }
    }
    public void CheckingEmailAlreadyExistsOrNot() {
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.TABLE_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);
        while (cursor.moveToNext()) {
            if(cursor.isFirst()) {
                cursor.moveToFirst();
                F_Result = "Email Found";
                cursor.close();
            }
        }
        CheckFinalResult();
    }
    public void CheckFinalResult() {
        if(F_Result.equalsIgnoreCase("Email Found")) {
            Toast.makeText(activity_register.this, "Email Already Exists", Toast.LENGTH_SHORT).show();
        }
        else {
            InsertDataIntoSQLiteDatabase();
        }
        F_Result = "Not_Found";
    }
}
