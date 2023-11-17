package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.adapter.TODOADapter;
import com.example.myapplication.dao.TODODAO;
import com.example.myapplication.model.TODO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
     RecyclerView recyclerView;
     ArrayList<TODO> arrayList;
     TODODAO tododao;
     TODO todo;
     TODOADapter adapter;
     EditText edTitle,edContent,edDate,edType,edID;
     Button btnAdd;
     int a=-1;
     SimpleDateFormat spd = new SimpleDateFormat("yyyy-MM-dd");
     Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rycleView);
        edID = findViewById(R.id.edID);
        edTitle = findViewById(R.id.edTitle);
        edContent = findViewById(R.id.edContent);
        edDate = findViewById(R.id.edDate);
        edType = findViewById(R.id.edType);
        btnAdd  =findViewById(R.id.btnAdd);
        getData();

        edID.setEnabled(false);
        if(arrayList.size()==0){
            edID.setText("1");

        }else {
            todo = tododao.getAll().get(arrayList.size()-1);
            edID.setText(String.valueOf(todo.getId()+1));
        }

        String datetime = spd.format(calendar.getTime());
        edDate.setEnabled(false);
        edDate.setText(datetime);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (a == -1) {
                    todo.setTitle(edTitle.getText().toString());
                    todo.setContent(edContent.getText().toString());
                    todo.setDate(datetime);
                    todo.setType(edType.getText().toString());
                    todo.setStatus(0);
                    if (tododao.insert(todo) > 0) {
                        Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        getData();
                        resetFields();
                    } else {
                        Toast.makeText(MainActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    TODO selectedTodo = tododao.getAll().get(a);
                    selectedTodo.setTitle(edTitle.getText().toString());
                    selectedTodo.setContent(edContent.getText().toString());
                    selectedTodo.setDate(datetime);
                    selectedTodo.setType(edType.getText().toString());
                    if (tododao.update(selectedTodo) > 0) {
                        Toast.makeText(MainActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        getData();
                        resetFields();
                        a = -1;
                        btnAdd.setText("Thêm");
                    } else {
                        Toast.makeText(MainActivity.this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    private void resetFields() {
        edTitle.setText("");
        edContent.setText("");
        edType.setText("");
        
    }
    private void getData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        tododao = new TODODAO(getApplicationContext());
        arrayList = tododao.getAll();
        adapter = new TODOADapter(getApplicationContext(), arrayList, tododao);
        adapter.setOnclickRecycle(new interFaceRecycle() {
            @Override
            public void onItemClick(int positon) {
                a= positon;
               todo = tododao.getAll().get(positon);
               edID.setText(String.valueOf(todo.getId()));
               edTitle.setText(todo.getTitle());
               edContent.setText(todo.getContent());
               edType.setText(todo.getType());

            }
        });
        recyclerView.setAdapter(adapter);
    }

}