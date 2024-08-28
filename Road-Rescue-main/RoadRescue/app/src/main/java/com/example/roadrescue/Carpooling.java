package com.example.roadrescue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Carpooling extends AppCompatActivity {

    FirebaseAuth auth;  // Đối tượng FirebaseAuth để quản lý xác thực người dùng
    RecyclerView mainUserRecyclerView;  // RecyclerView để hiển thị danh sách người dùng
    UserAdpter adapter;  // Adapter để liên kết dữ liệu với RecyclerView
    FirebaseDatabase database;  // Đối tượng FirebaseDatabase để truy cập cơ sở dữ liệu Firebase
    ArrayList<Users> usersArrayList;  // Danh sách người dùng được lấy từ Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpooling);

        // Khởi tạo đối tượng FirebaseDatabase và FirebaseAuth
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        // Lấy tham chiếu đến nút "user" trong cơ sở dữ liệu Firebase
        DatabaseReference reference = database.getReference().child("user");

        // Khởi tạo danh sách người dùng
        usersArrayList = new ArrayList<>();

        // Đăng ký lắng nghe sự thay đổi dữ liệu từ Firebase
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Xóa danh sách người dùng hiện tại để đảm bảo không bị trùng lặp
                usersArrayList.clear();
                // Duyệt qua các dữ liệu con của snapshot
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Chuyển đổi dữ liệu từ snapshot thành đối tượng Users
                    Users users = dataSnapshot.getValue(Users.class);
                    // Thêm đối tượng Users vào danh sách
                    usersArrayList.add(users);
                }
                // Cập nhật RecyclerView khi dữ liệu đã được tải xong
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi khi không thể truy cập cơ sở dữ liệu Firebase
            }
        });

        // Khởi tạo RecyclerView và thiết lập LayoutManager
        mainUserRecyclerView = findViewById(R.id.mainUserRecyclerView);
        mainUserRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo Adapter và gán cho RecyclerView
        adapter = new UserAdpter(Carpooling.this, usersArrayList);
        mainUserRecyclerView.setAdapter(adapter);
    }
}
