package com.example.roadrescue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class chatWin extends AppCompatActivity {

    // Các biến thành viên để lưu thông tin người nhận và giao diện người dùng
    String reciverimg, reciverUid, reciverName, SenderUID;
    CircleImageView profile;
    TextView reciverNName;
    CardView sendbtn;
    EditText textmsg;

    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    public static String senderImg;
    public static String reciverIImg;
    String senderRoom, reciverRoom;
    RecyclerView messageAdpter;
    ArrayList<msgModelclass> messagesArrayList;
    messagesAdpter mmessagesAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_win);

        // Khởi tạo các thành phần Firebase
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        // Khởi tạo các thành phần giao diện người dùng
        messageAdpter = findViewById(R.id.msgadpter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);  // Đặt RecyclerView để cuộn tự động xuống dưới khi có tin nhắn mới
        messageAdpter.setLayoutManager(linearLayoutManager);

        reciverName = getIntent().getStringExtra("nameeee");  // Lấy thông tin từ Intent
        reciverimg = getIntent().getStringExtra("reciverImg");
        reciverUid = getIntent().getStringExtra("uid");

        messagesArrayList = new ArrayList<>();

        sendbtn = findViewById(R.id.sendbtnn);
        textmsg = findViewById(R.id.textmsg);
        profile = findViewById(R.id.profileimgg);
        reciverNName = findViewById(R.id.recivername);

        // Tải ảnh người nhận và đặt tên cho người nhận
        Picasso.get().load(reciverimg).into(profile);
        reciverNName.setText(reciverName);

        // Kiểm tra người dùng đã đăng nhập
        if (firebaseAuth.getCurrentUser() != null) {
            SenderUID = firebaseAuth.getUid();  // Lấy ID của người gửi
            senderRoom = SenderUID + reciverUid;  // Tạo phòng trò chuyện của người gửi
            reciverRoom = reciverUid + SenderUID;  // Tạo phòng trò chuyện của người nhận

            // Tham chiếu đến phòng trò chuyện và lấy các tin nhắn
            DatabaseReference reference = database.getReference().child("user").child(SenderUID);
            DatabaseReference chatreference = database.getReference().child("chats").child(senderRoom).child("messages");

            chatreference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    messagesArrayList.clear();  // Xóa danh sách tin nhắn cũ
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        msgModelclass messages = dataSnapshot.getValue(msgModelclass.class);
                        messagesArrayList.add(messages);  // Thêm tin nhắn vào danh sách
                    }
                    mmessagesAdpter = new messagesAdpter(chatWin.this, messagesArrayList);  // Cập nhật Adapter
                    messageAdpter.setAdapter(mmessagesAdpter);
                    mmessagesAdpter.notifyDataSetChanged();  // Thông báo Adapter để làm mới giao diện
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Xử lý lỗi nếu cần
                }
            });

            // Lấy thông tin ảnh của người gửi
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    senderImg = snapshot.child("profilepic").getValue().toString();
                    reciverIImg = reciverimg;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Xử lý lỗi nếu cần
                }
            });

            // Xử lý sự kiện nhấp vào nút gửi
            sendbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = textmsg.getText().toString();
                    if (message.isEmpty()) {
                        Toast.makeText(chatWin.this, "Enter The Message First", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    textmsg.setText("");  // Xóa nội dung của EditText
                    Date date = new Date();
                    msgModelclass messagess = new msgModelclass(message, SenderUID, date.getTime());

                    // Thêm tin nhắn vào phòng trò chuyện của người gửi và người nhận
                    DatabaseReference senderReference = database.getReference().child("chats")
                            .child(senderRoom)
                            .child("messages");
                    DatabaseReference reciverReference = database.getReference().child("chats")
                            .child(reciverRoom)
                            .child("messages");

                    senderReference.push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            reciverReference.push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    // Xử lý hoàn tất nếu cần
                                }
                            });
                        }
                    });
                }
            });
        }
    }
}
