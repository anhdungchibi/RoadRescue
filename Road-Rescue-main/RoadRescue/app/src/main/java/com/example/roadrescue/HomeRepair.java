package com.example.roadrescue;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeRepair extends AppCompatActivity {
    RecyclerView recyclerView;
    List<DataClass> dataList;
    MyAdapter adapter;
    DataClass androidData;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_repair);

        recyclerView = findViewById(R.id.recyclerView); // Khởi tạo RecyclerView
        searchView = findViewById(R.id.search); // Khởi tạo SearchView

        searchView.clearFocus(); // Xoá trạng thái focus của SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Xử lý khi người dùng gửi truy vấn tìm kiếm (hiện tại không thực hiện hành động nào)
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Xử lý khi văn bản tìm kiếm thay đổi
                searchList(newText); // Tìm kiếm trong danh sách khi văn bản thay đổi
                return true;
            }
        });

        // Thiết lập GridLayoutManager với 2 cột cho RecyclerView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(HomeRepair.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        dataList = new ArrayList<>(); // Khởi tạo danh sách dữ liệu

        // Thêm các đối tượng DataClass vào danh sách dữ liệu
        androidData = new DataClass("Engine", R.string.Engine, R.drawable.engine);
        dataList.add(androidData);
        androidData = new DataClass("Tire", R.string.Tire, R.drawable.tire);
        dataList.add(androidData);
        androidData = new DataClass("AC", R.string.AC, R.drawable.carac);
        dataList.add(androidData);
        androidData = new DataClass("Car Wash", R.string.CarWash, R.drawable.carwash);
        dataList.add(androidData);

        // Khởi tạo adapter và gán cho RecyclerView
        adapter = new MyAdapter(HomeRepair.this, dataList);
        recyclerView.setAdapter(adapter);
    }

    // Phương thức tìm kiếm trong danh sách dữ liệu
    private void searchList(String text) {
        List<DataClass> dataSearchList = new ArrayList<>();
        for (DataClass data : dataList) {
            // Kiểm tra xem tiêu đề dữ liệu có chứa văn bản tìm kiếm không
            if (data.getDataTitle().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data); // Thêm dữ liệu vào danh sách tìm kiếm nếu khớp
            }
        }

        if (dataSearchList.isEmpty()) {
            // Hiển thị thông báo nếu không tìm thấy dữ liệu khớp
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            // Cập nhật adapter với danh sách tìm kiếm
            adapter.setSearchList(dataSearchList);
        }
    }
}
