package com.example.roadrescue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class CurrentLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    // Biến để lưu thông tin vị trí hiện tại
    Location currentLocation;
    // Đối tượng cung cấp vị trí
    FusedLocationProviderClient fusedclient;
    // Mã yêu cầu quyền truy cập vị trí
    private static final int REQUEST_CODE = 101;
    // Thành phần giao diện người dùng để chứa bản đồ
    FrameLayout map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);

        // Khởi tạo FrameLayout chứa bản đồ
        map = findViewById(R.id.map);

        // Khởi tạo đối tượng FusedLocationProviderClient để lấy vị trí
        fusedclient = LocationServices.getFusedLocationProviderClient(this);
        // Lấy vị trí hiện tại
        getLocation();
    }

    private void getLocation() {
        // Kiểm tra xem ứng dụng đã được cấp quyền truy cập vị trí chưa
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Yêu cầu quyền truy cập vị trí nếu chưa được cấp
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        // Lấy vị trí cuối cùng từ FusedLocationProviderClient
        Task<Location> task = fusedclient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Kiểm tra nếu có vị trí và khởi tạo bản đồ
                if (location != null) {
                    currentLocation = location;
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(CurrentLocationActivity.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // Tạo đối tượng LatLng từ vị trí hiện tại
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        // Tạo đối tượng MarkerOptions để hiển thị đánh dấu trên bản đồ
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Current Location");
        // Cập nhật camera của bản đồ để hiển thị vị trí hiện tại
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        // Thêm đánh dấu trên bản đồ
        googleMap.addMarker(markerOptions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Kiểm tra kết quả yêu cầu quyền truy cập
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Nếu quyền được cấp, lấy lại vị trí
                getLocation();
            }
        }
    }
}
