package com.example.roadrescue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo và cấu hình ImageSlider
        ImageSlider imageSlider = findViewById(R.id.imageslider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.image1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image2, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        // Đăng ký Context Menu cho ImageView
        ImageView imageView = findViewById(R.id.option);
        registerForContextMenu(imageView);

        // Khởi tạo DrawerLayout và NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        // Khởi tạo các CardView và thiết lập sự kiện nhấp
        CardView soscontact = findViewById(R.id.cardSoscontact);
        CardView repairTutorial = findViewById(R.id.cardRepairTutorial);
        CardView weather = findViewById(R.id.cardWeather);
        CardView NearByPlaces = findViewById(R.id.cardNearByPlaces);
        CardView Hospital = findViewById(R.id.cardHospital);
        CardView ChatBot = findViewById(R.id.cardChatbot);
        CardView carpooling = findViewById(R.id.cardCarpooling);

        // Khi nhấp vào ChatBot card, mở Activity ChatBotActivity
        ChatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ChatBotActivity.class));
            }
        });

        // Khi nhấp vào Hospital card, mở Activity HospitalActivity
        Hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HospitalActivity.class));
            }
        });

        // Khi nhấp vào Carpooling card, mở Activity Carpooling
        carpooling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Carpooling.class));
            }
        });

        // Khi nhấp vào NearByPlaces card, mở Activity CurrentMapsActivity
        NearByPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CurrentMapsActivity.class));
            }
        });

        // Khi nhấp vào RepairTutorial card, mở Activity HomeRepair
        repairTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HomeRepair.class));
            }
        });

        // Khi nhấp vào Weather card, mở Activity Weather
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Weather.class));
            }
        });

        // Khi nhấp vào Soscontact card, mở Activity SosContactActivity
        soscontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SosContactActivity.class));
            }
        });

        // Khi nhấp vào ImageView hamburgerMenu, mở Navigation Drawer
        ImageView hamburgerMenu = findViewById(R.id.option);
        hamburgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở Navigation Drawer
                if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        // Thiết lập sự kiện khi người dùng chọn một mục trong Navigation Drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_profile) {
                    // Mở Activity LogoutActivity khi chọn mục "Profile"
                    Intent profileIntent = new Intent(getApplicationContext(), LogoutActivity.class);
                    startActivity(profileIntent);
                } else if (itemId == R.id.menu_logout) {
                    // Xử lý đăng xuất
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.menu_location) {
                    // Mở Activity CurrentLocationActivity khi chọn mục "Location"
                    Intent intent = new Intent(MainActivity.this, CurrentLocationActivity.class);
                    startActivity(intent);
                }
                // Đóng Navigation Drawer sau khi chọn mục
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
}
