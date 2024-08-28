package com.example.roadrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class ContactDetail extends AppCompatActivity {

    // Các biến thành viên để lưu các thành phần giao diện người dùng
    private TextView nameTv, phoneTv, emailTv;

    // Biến để lưu ID của liên hệ
    private String id;
    // Đối tượng trợ giúp để tương tác với cơ sở dữ liệu SQLite
    private SavedContactsHelper savedContactsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        // Khởi tạo đối tượng SavedContactsHelper
        savedContactsHelper = new SavedContactsHelper(this);

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        id = intent.getStringExtra("contactId");

        // Khởi tạo các thành phần giao diện người dùng
        nameTv = findViewById(R.id.nameTv);
        phoneTv = findViewById(R.id.phone_tv);
        emailTv = findViewById(R.id.email_tv);

        // Tải dữ liệu của liên hệ theo ID
        LoadDataById();
    }

    private void LoadDataById() {
        // Tạo câu truy vấn để lấy dữ liệu liên hệ theo ID
        String selectQuery = "SELECT * FROM " + SavedContacts.TABLE_NAME + " WHERE " + SavedContacts.CONTACT_ID + "=\"" + id + "\"";

        // Mở cơ sở dữ liệu ở chế độ đọc
        SQLiteDatabase db = savedContactsHelper.getReadableDatabase();
        // Thực hiện câu truy vấn
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Kiểm tra nếu có kết quả
        if (cursor.moveToFirst()) {
            do {
                // Lấy dữ liệu từ con trỏ
                String name = cursor.getString(cursor.getColumnIndexOrThrow(SavedContacts.CONTACT_NAME));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(SavedContacts.CONTACT_PHONE));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(SavedContacts.CONTACT_EMAIL));

                // Hiển thị dữ liệu lên giao diện người dùng
                nameTv.setText(name);
                phoneTv.setText(phone);
                emailTv.setText(email);
            } while (cursor.moveToNext()); // Di chuyển đến bản ghi tiếp theo
        }
        cursor.close(); // Đóng con trỏ khi không còn sử dụng
        db.close(); // Đóng cơ sở dữ liệu khi không còn sử dụng
    }
}
