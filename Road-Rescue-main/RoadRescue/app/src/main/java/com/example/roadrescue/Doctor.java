package com.example.roadrescue;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        List<Item> items = new ArrayList<Item>();
        items.add(new Item("Dr. Hong Anh","Chuyên gia phục hồi chức năng (0868347367)", R.drawable.zahan));
        items.add(new Item("Dr. Tuan","Chuyên gia phẫu thuật nhi khoa (trẻ em) (039456574)",R.drawable.milon));
        items.add(new Item("Dr. Luan","Chuyên gia Y học & Tiểu đường (234226619)",R.drawable.ahsan));
        items.add(new Item("Dr. Minh","Chuyên gia về bệnh gan & y học (234576679)",R.drawable.rahman));
        items.add(new Item("Dr. Vu","Chuyên gia Phẫu thuật Cọc & Thoát vị (234446611)",R.drawable.paik));





        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter3(getApplicationContext(),items));

    }
}