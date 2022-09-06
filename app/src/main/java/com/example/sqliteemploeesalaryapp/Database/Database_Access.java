package com.example.sqliteemploeesalaryapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqliteemploeesalaryapp.Model.EmployeeModel;

import java.util.ArrayList;

public class Database_Access {

    public SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;  
    private static Database_Access instance;


    private Database_Access(Context context) {
        this.openHelper = new My_Database(context);
    }

    public static Database_Access getInstance(Context context) {
        if (instance == null) {
            instance = new Database_Access(context);
        }

        return instance;
    }


    public void open() {
        this.database = this.openHelper.getWritableDatabase();
    }

    public void close() {

        if (this.database != null) {
            this.database.close();
        }
    }

//-------------------------------------------------------------------------
//        Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);


    public boolean insertEmployee(EmployeeModel employee) {

        ContentValues CV = new ContentValues();

        CV.put(My_Database.EMP_TB_NAME, employee.getName());
        CV.put(My_Database.EMP_TB_SALARY, employee.getSalary()); 

        long result = database.insert(My_Database.EMPLOYEES_TB, null, CV); 
        return result != -1;
    }


//    public boolean DeleteEmployee(EmployeeModel employee) {
//        String args[] = {String.valueOf(employee.getId())};
//        long result = database.delete(My_Database.EMPLOYEES_TB, "id=?", args);
//        return result > 0;
//    }

    public ArrayList<EmployeeModel> getAllEmployees() { 
        ArrayList<EmployeeModel> EmployeesList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + My_Database.EMPLOYEES_TB, null); 
 
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String emp_name = cursor.getString(cursor.getColumnIndex(My_Database.EMP_TB_NAME));
                @SuppressLint("Range") int emp_salary = cursor.getInt(cursor.getColumnIndex(My_Database.EMP_TB_SALARY));

                // call constructor :
                EmployeeModel Employee = new EmployeeModel(emp_name,emp_salary);

                EmployeesList.add(Employee);

            } while (cursor.moveToNext());
            cursor.close();
        }

        return EmployeesList;
    }

    //------------------------------------------------------------------------------------------------------------------------

    // get item details in details activity from posts activity
    public EmployeeModel getEmployee(int empId) {

        Cursor cursor = database.rawQuery("SELECT * FROM " + My_Database.EMPLOYEES_TB + " WHERE " + My_Database.EMP_TB_ID + " =? ", new String[]{String.valueOf(empId)});


        // to know if cursor contains data or not :
        if (cursor != null && cursor.moveToFirst()) {

            @SuppressLint("Range") String emp_name = cursor.getString(cursor.getColumnIndex(My_Database.EMP_TB_NAME));
            @SuppressLint("Range") int emp_salary = cursor.getInt(cursor.getColumnIndex(My_Database.EMP_TB_SALARY));

            // call constructor :
            EmployeeModel Employee = new EmployeeModel(emp_name,emp_salary);

            cursor.close();
            return Employee;
        }

        return null;
    }

    //------------------------------------------------------------------------------------------------------------------------


    @SuppressLint("Range")
    public ArrayList<EmployeeModel> getSalaryMoreOrEqual() {

        ArrayList<EmployeeModel> EmployeesList = new ArrayList<>();
        String args [] = {"1000"};
//        String columns [] = {My_Database.EMP_TB_NAME,My_Database.EMP_TB_SALARY};
        Cursor cursor = database.query( My_Database.EMPLOYEES_TB,null,"salary  >= ? ",args,null,null,null,null);


        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String emp_name = cursor.getString(cursor.getColumnIndex(My_Database.EMP_TB_NAME));
                @SuppressLint("Range") int emp_salary = cursor.getInt(cursor.getColumnIndex(My_Database.EMP_TB_SALARY));

                // call constructor :
                EmployeeModel Employee = new EmployeeModel(emp_name,emp_salary);

                EmployeesList.add(Employee);

            } while (cursor.moveToNext());
            cursor.close();
        }

        return EmployeesList;
    }


    @SuppressLint("Range")
    public ArrayList<EmployeeModel> getSalaryLessThan() {

        ArrayList<EmployeeModel> EmployeesList = new ArrayList<>();
        String args [] = {"1000"};
//        String columns [] = {My_Database.EMP_TB_NAME,My_Database.EMP_TB_SALARY};
        Cursor cursor = database.query( My_Database.EMPLOYEES_TB,null,"salary < ? ",args,null,null,null,null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String emp_name = cursor.getString(cursor.getColumnIndex(My_Database.EMP_TB_NAME));
                @SuppressLint("Range") int emp_salary = cursor.getInt(cursor.getColumnIndex(My_Database.EMP_TB_SALARY));

                // call constructor :
                EmployeeModel Employee = new EmployeeModel(emp_name,emp_salary);

                EmployeesList.add(Employee);

            } while (cursor.moveToNext());
            cursor.close();
        }

        return EmployeesList;
    }



    @SuppressLint("Range")
    public int getEmployeeId(String name , int salary) {

        String args [] = {name,String.valueOf(salary)};
        String columns [] = {My_Database.EMP_TB_ID};
        Cursor cursor = database.query( My_Database.EMPLOYEES_TB,columns,"name=? AND salary=? ",args,null,null,null,null);

        if (cursor != null && cursor.moveToFirst()) {
            return Integer.parseInt(cursor.getString(cursor.getColumnIndex(My_Database.EMP_TB_ID)));
        }

        return 0;
    }

    public boolean DeleteEmployee(int employeeId) {
        String args[] = {String.valueOf(employeeId)};
        long result = database.delete(My_Database.EMPLOYEES_TB, "id=?", args);
        return result > 0;
    }
}
