package com.example.roadrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddUpdateContact extends AppCompatActivity implements View.OnClickListener {

    private ImageView profiledp;
    private EditText nameEdittxt, phoneEdittxt, emailEdittxt;
    private FloatingActionButton fab;

    private String id, contactName, contactPhone, contactEmail;
    private boolean EditMode;

    SavedContactsHelper savedContactsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_contact);

        // Khởi tạo SavedContactsHelper để quản lý cơ sở dữ liệu
        savedContactsHelper = new SavedContactsHelper(this);
        SQLiteDatabase sqLiteDatabase = savedContactsHelper.getWritableDatabase();

        // Tìm và gán các thành phần giao diện từ layout
        ImageView backicon = findViewById(R.id.backicon);
        profiledp = findViewById(R.id.profiledp);
        nameEdittxt = findViewById(R.id.nameEdittxt);
        phoneEdittxt = findViewById(R.id.phoneEdittxt);
        emailEdittxt = findViewById(R.id.emailEdittxt);
        fab = findViewById(R.id.fab);

        // Xử lý sự kiện nhấp vào nút quay lại
        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Quay lại Activity SosContactActivity
                startActivity(new Intent(AddUpdateContact.this, SosContactActivity.class));
            }
        });

        // Lấy Intent và dữ liệu từ Activity trước đó
        Intent intent = getIntent();
        EditMode = intent.getBooleanExtra("EditMode", false);

        // Nếu ở chế độ chỉnh sửa, lấy dữ liệu của liên hệ và hiển thị lên các trường
        if (EditMode) {
            id = intent.getStringExtra("ID");
            contactName = intent.getStringExtra("NAME");
            contactPhone = intent.getStringExtra("PHONE");
            contactEmail = intent.getStringExtra("EMAIL");

            nameEdittxt.setText(contactName);
            phoneEdittxt.setText(contactPhone);
            emailEdittxt.setText(contactEmail);
        }

        // Xử lý sự kiện nhấp vào FloatingActionButton
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy dữ liệu từ các trường nhập liệu
                contactName = nameEdittxt.getText().toString();
                contactPhone = phoneEdittxt.getText().toString();
                contactEmail = emailEdittxt.getText().toString();

                // Kiểm tra xem các trường nhập liệu có bị bỏ trống không
                if ((!contactName.isEmpty() || !contactPhone.isEmpty() || !contactEmail.isEmpty())) {
                    if (EditMode) {
                        // Nếu ở chế độ chỉnh sửa, cập nhật liên hệ trong cơ sở dữ liệu
                        savedContactsHelper.editContact(id, contactName, contactPhone, contactEmail);
                        Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // Nếu ở chế độ thêm mới, thêm liên hệ vào cơ sở dữ liệu
                        long id = savedContactsHelper.addContact(contactName, contactPhone, contactEmail);
                        Toast.makeText(getApplicationContext(), "Contact Saved" + id, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Hiển thị thông báo lỗi nếu các trường bị bỏ trống
                    Toast.makeText(AddUpdateContact.this, "Please fill out the details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        // Phương thức trống vì lớp không thực hiện hành động gì trong sự kiện nhấp chuột
    }
}
