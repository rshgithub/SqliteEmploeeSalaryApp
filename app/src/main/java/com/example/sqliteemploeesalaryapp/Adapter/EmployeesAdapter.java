package com.example.sqliteemploeesalaryapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.sqliteemploeesalaryapp.Database.Database_Access;
import com.example.sqliteemploeesalaryapp.Model.EmployeeModel;
import com.example.sqliteemploeesalaryapp.R;

import java.util.ArrayList;

public class EmployeesAdapter extends BaseAdapter {

    private Context c;
    private int resource;
    private ArrayList<EmployeeModel> employees;
    private Database_Access DBA = Database_Access.getInstance(c);

    public EmployeesAdapter(Context c, int resource, ArrayList<EmployeeModel> employees) {
        this.c = c;
        this.resource = resource;
        this.employees = employees;
    }

    public void addEmployee(EmployeeModel employee) {
        this.employees.add(employee);
        notifyDataSetChanged();
    }

    public void deleteEmployee(EmployeeModel employee) {
        this.employees.remove(employee);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return employees.size();
    }

    @Override
    public EmployeeModel getItem(int i) {
        return employees.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;
        if (v == null) {
            v = LayoutInflater.from(c).inflate(resource, null, false);
        }


        TextView name = v.findViewById(R.id.emp_name);
        TextView salary = v.findViewById(R.id.emp_salary);
        LinearLayoutCompat container = v.findViewById(R.id.emp_info);

        EmployeeModel e = getItem(i);

        name.setText(e.getName());
        salary.setText(Integer.toString(e.getSalary()));

        if (e.getSalary() >= 1000) {
            container.setBackgroundColor(Color.GREEN);
        } else {
            container.setBackgroundColor(Color.RED);
        }

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBA.open();
                int id = DBA.getEmployeeId(e.getName(), e.getSalary());

                Boolean result = DBA.DeleteEmployee(id);
                if (result) {
                    deleteEmployee(e);
                    Toast.makeText(c, e.getName()+" employee deleted successfully From DataBase ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(c, e.getName()+" employee failed to be deleted From DataBase ", Toast.LENGTH_SHORT).show();
                }

                DBA.close();
                notifyDataSetChanged();
            }
        });


        return v;
    }
}
