package com.example.roadrescue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HospitalActivity extends AppCompatActivity {
    CardView DocCard, TestCard, HosCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital); // Gán layout cho Activity

        // Khởi tạo các CardView
        TestCard = findViewById(R.id.TestCard);
        HosCard = findViewById(R.id.HosCard);
        DocCard = findViewById(R.id.DocCard);

        // Thiết lập sự kiện khi người dùng nhấp vào DocCard
        DocCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở Activity Doctor khi nhấp vào DocCard
                Intent intent = new Intent(HospitalActivity.this, Doctor.class);
                startActivity(intent);
            }
        });

        // Thiết lập sự kiện khi người dùng nhấp vào TestCard
        TestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở Activity TestActivity khi nhấp vào TestCard
                Intent intent = new Intent(HospitalActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });

        // Thiết lập sự kiện khi người dùng nhấp vào HosCard
        HosCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở Activity NearByHospital khi nhấp vào HosCard
                Intent intent = new Intent(HospitalActivity.this, NearByHospital.class);
                startActivity(intent);
            }
        });
    }
}
