package com.example.sqliteemploeesalaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.sqliteemploeesalaryapp.Adapter.EmployeesAdapter;
import com.example.sqliteemploeesalaryapp.Database.Database_Access;
import com.example.sqliteemploeesalaryapp.Model.EmployeeModel;
import com.example.sqliteemploeesalaryapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Database_Access DBA;
    private EmployeeModel employee;
    private ArrayList<EmployeeModel> employees;
    private EmployeesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        employees = new ArrayList<EmployeeModel>();
        DBA = Database_Access.getInstance(this);


        // get selected radio button from radioGroup
        int selectedId = binding.searchRadioGroup.getCheckedRadioButtonId();
        if (selectedId == binding.mainActivityAllRadioBtn.getId()) {
            DBA.open();
            displayEmployeesList(DBA.getAllEmployees());
            DBA.close();
        } else if (selectedId == binding.mainActivityMoreOrEqualRadioBtn.getId()) {
            DBA.open();
            displayEmployeesList(DBA.getSalaryMoreOrEqual());
            DBA.close();
        } else if (selectedId == binding.mainActivityLessThanRadioBtn.getId()) {
            DBA.open();
            displayEmployeesList(DBA.getSalaryLessThan());
            DBA.close();
        }

        binding.mainActivityAllRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBA.open();
                displayEmployeesList(DBA.getAllEmployees());
                DBA.close();
            }
        });

        binding.mainActivityMoreOrEqualRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBA.open();
                displayEmployeesList(DBA.getSalaryMoreOrEqual());
                DBA.close();
            }
        });


        binding.mainActivityLessThanRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBA.open();
                displayEmployeesList(DBA.getSalaryLessThan());
                DBA.close();
            }
        });


        binding.mainActivityInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.mainActivityNameEt.getText().toString();
                String salary = binding.mainActivitySalaryEt.getText().toString();

                if (name.equals("")) {
                    Toast.makeText(MainActivity.this, "Please Enter employee name", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(salary) == 0) {
                    Toast.makeText(MainActivity.this, "Please Enter employee salary", Toast.LENGTH_SHORT).show();
                } else {

                    DBA.open();
                    employee = new EmployeeModel(name, Integer.parseInt(salary));

                    Boolean result = DBA.insertEmployee(employee);
                    // if process done successfully
                    if (result) {

                        Log.e("inserting result", result + "");
                        adapter.addEmployee(employee);

                    } else {
                        Toast.makeText(getBaseContext(), "employee Addition failed ", Toast.LENGTH_SHORT).show();

                    }

                    DBA.close();

                }
            }
        });




    }

    private void displayEmployeesList(ArrayList<EmployeeModel> employees){

        adapter = new EmployeesAdapter(this, R.layout.list_item, employees);
        binding.mainActivityEmployeesList.setAdapter(adapter);

    }

}