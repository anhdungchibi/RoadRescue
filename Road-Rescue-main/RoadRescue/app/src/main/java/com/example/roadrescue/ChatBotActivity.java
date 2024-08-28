package com.example.roadrescue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatBotActivity extends AppCompatActivity {

    RecyclerView recyclerView;  // RecyclerView để hiển thị các tin nhắn
    TextView welcomeTextView;   // TextView hiển thị lời chào
    EditText messageEditText;   // EditText cho người dùng nhập tin nhắn
    ImageButton sendButton;     // Nút gửi tin nhắn

    List<Message> messageList;  // Danh sách các tin nhắn

    MessageAdapter messageAdapter;  // Adapter để liên kết dữ liệu với RecyclerView

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .build();  // Khởi tạo OkHttpClient với thời gian đọc tối đa là 60 giây

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        // Khởi tạo danh sách tin nhắn và các thành phần giao diện
        messageList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyler_view);
        welcomeTextView = findViewById(R.id.welcome_text);
        messageEditText = findViewById(R.id.message_edit_text);
        sendButton = findViewById(R.id.send_btn);

        // Khởi tạo Adapter và gán cho RecyclerView
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);  // Đặt RecyclerView để cuộn tự động xuống dưới khi có tin nhắn mới
        recyclerView.setLayoutManager(llm);

        // Xử lý sự kiện nhấp vào nút gửi
        sendButton.setOnClickListener((v) -> {
            String question = messageEditText.getText().toString().trim();  // Lấy tin nhắn từ EditText
            if (!question.isEmpty()) {  // Kiểm tra nếu tin nhắn không trống
                addToChat(question, Message.SENT_BY_ME);  // Thêm tin nhắn vào chat
                messageEditText.setText("");  // Xóa nội dung của EditText
                callAPI(question);  // Gọi API để gửi tin nhắn
                welcomeTextView.setVisibility(View.GONE);  // Ẩn TextView chào mừng
            } else {
                Toast.makeText(ChatBotActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();  // Hiển thị thông báo nếu tin nhắn trống
            }
        });
    }

    void addToChat(String message, String sentBy) {
        runOnUiThread(() -> {
            // Thêm tin nhắn vào danh sách và cập nhật RecyclerView
            messageList.add(new Message(message, sentBy));
            messageAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());  // Cuộn xuống dưới cùng của RecyclerView
        });
    }

    void addResponse(String response) {
        addToChat(response, Message.SENT_BY_BOT);  // Thêm phản hồi của bot vào chat
    }

    void callAPI(String question) {
        // Tạo đối tượng JSON cho yêu cầu
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "gpt-3.5-turbo");

            JSONArray messageArr = new JSONArray();
            JSONObject obj = new JSONObject();
            obj.put("role", "user");
            obj.put("content", question);
            messageArr.put(obj);

            jsonBody.put("messages", messageArr);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Tạo yêu cầu HTTP
        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")  // URL của API
                .header("Authorization", "Bearer API Key")
                .post(body)
                .build();

        // Gửi yêu cầu và xử lý phản hồi
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to load Response due to " + e.getMessage());  // Thêm thông báo lỗi vào chat nếu yêu cầu thất bại
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        // Xử lý phản hồi từ API
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0)
                                .getJSONObject("message")
                                .getString("content");

                        addResponse(result.trim());  // Thêm phản hồi vào chat
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    addResponse("Failed to load response due to " + response.body().toString());  // Thêm thông báo lỗi nếu phản hồi không thành công
                }
            }
        });
    }
}
