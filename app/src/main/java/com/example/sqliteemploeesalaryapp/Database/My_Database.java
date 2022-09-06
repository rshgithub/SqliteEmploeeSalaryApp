package com.example.sqliteemploeesalaryapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class My_Database extends SQLiteOpenHelper {

    public static final String DB_NAME = "employees.db";
    public static final int DB_VERSION = 2 ;

    public static final String  EMPLOYEES_TB = "employees",
                EMP_TB_ID = "id" , EMP_TB_NAME = "name" ,  EMP_TB_SALARY = "salary" ;

    public My_Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlEmployerTable = "create table "
                + EMPLOYEES_TB + " ("
                + EMP_TB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EMP_TB_NAME + " TEXT NOT NULL ,"
                + EMP_TB_SALARY + " INT NOT NULL "
                + " )";

        db.execSQL(sqlEmployerTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + EMPLOYEES_TB + "");
        onCreate(db);
    }


    //-------------------------------------------------------------------------



}
