package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.adapter.TODOADapter;
import com.example.myapplication.dao.TODODAO;
import com.example.myapplication.model.TODO;
import com.google.android.material.textfield.TextInputLayout;

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
     TextInputLayout tilTitle,tilContent,tilType;
     Button btnAdd;
     int a=-1;
     int temp=0;
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
        tilTitle = findViewById(R.id.tilTitle);
        tilContent = findViewById(R.id.tilContent);
        tilType = findViewById(R.id.tilType);
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

        String[] mucDoCongviec = {"Kho", "Thuong", "De"};
        edType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Chọn mức độ công việc");
                builder.setItems(mucDoCongviec, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        edType.setText(mucDoCongviec[i]);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (a == -1) {
                    validate();
                    todo.setTitle(edTitle.getText().toString());
                    todo.setContent(edContent.getText().toString());
                    todo.setDate(datetime);
                    todo.setType(edType.getText().toString());
                    todo.setStatus(0);
                    if(temp==0){
                        if (tododao.insert(todo) > 0) {
                            Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            getData();
                            resetFields();
                        } else {
                            Toast.makeText(MainActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    validate();
                    TODO selectedTodo = tododao.getAll().get(a);
                    selectedTodo.setTitle(edTitle.getText().toString());
                    selectedTodo.setContent(edContent.getText().toString());
                    selectedTodo.setDate(datetime);
                    selectedTodo.setType(edType.getText().toString());
                    if(temp==0){
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
            }
        });

    }
    private void validate(){
        String title = edTitle.getText().toString();
        String content = edContent.getText().toString();
        String type = edType.getText().toString();
        if(title.length()==0){
            tilTitle.setError("Chua nhap title");
            temp++;
        }else {
            tilTitle.setError("");
            temp=0;
        }
        if(content.length()==0){
            tilContent.setError("chua nhap content");
            temp++;
        }else {
            tilContent.setError("");
            temp=0;
        }
        if(type.length()==0){
            tilType.setError("chua nhap type");
            temp++;
        }else {
            tilType.setError("");
            temp=0;
        }
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

        tododao = new TODODAO(MainActivity.this);
        arrayList = tododao.getAll();
        adapter = new TODOADapter(MainActivity.this, arrayList, tododao);
        adapter.setOnclickRecycle(new interFaceRecycle() {
            @Override
            public void onItemClick(int positon) {
                a= positon;
                btnAdd.setText("Sửa");
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