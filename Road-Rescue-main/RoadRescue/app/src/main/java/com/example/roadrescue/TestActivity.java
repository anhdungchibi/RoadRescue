package com.example.roadrescue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        int[] imageId = {R.drawable.mri_test,R.drawable.blood_tests,R.drawable.cbc_test,R.drawable.endoscopy_test,R.drawable.kidneyfunction_test,R.drawable.liverfunction_test,R.drawable.thyroid_test,R.drawable.ultrasound_test,
                R.drawable.urinalysis_test,R.drawable.xray_test};
        String[] name = {"MRI-Lubana","MRI-Labaid","CBC Test-Labaid","CBC Test-Squre","Kiểm tra chức năng thận","Kiểm tra chức năng gan","Xét nghiệm tuyến giáp","Xét nghiệm siêu âm"," Xét nghiệm phân tích nước tiểu","Xray"};
        String[] phoneNo = {"500000","600000","550000","600000","450000","700000","840000","360000","250000","480000"};
        String[] country = {"Bệnh viện Bạch Mai","Bệnh viện E Hà Nội","Bệnh viện Việt- Đức","Bệnh viện Bắc Thăng Long","Bệnh viện Hữu Nghị", "Lao và bệnh phổi Trung ương","Bện viện Đống Đa","Bệnh viện Xanh Pôn" ,"Bệnh viện K"," Bệnh viện Hai Bà Trưng"};

        ArrayList<User> userArrayList = new ArrayList<>();

        for (int i = 0; i < imageId.length; i++) {
            User user = new User(name[i], "", phoneNo[i], country[i], imageId[i]);
            userArrayList.add(user);
        }

        ListAdapter listAdapter = new ListAdapter(TestActivity.this, R.layout.list_item, userArrayList);


        listView = findViewById(R.id.listView);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(TestActivity.this, UserActivity.class);
                i.putExtra("name", name[position]);
                i.putExtra("phone", phoneNo[position]);
                i.putExtra("country", country[position]);
                i.putExtra("imageId", imageId[position]); // Ensure imageId[position] is a valid resource ID.

                startActivity(i);
            }
        });
    }
}
