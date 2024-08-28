package com.example.roadrescue;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetNearbyPlaces extends AsyncTask<Object, String, String> {

    private String googleplaceData, url;
    private GoogleMap mMap;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0]; // Đối tượng GoogleMap để cập nhật bản đồ
        url = (String) objects[1]; // URL để lấy dữ liệu các địa điểm gần đây

        DownloadUrl downloadUrl = new DownloadUrl(); // Lớp phụ trợ để tải dữ liệu
        try {
            googleplaceData = downloadUrl.ReadTheURL(url); // Đọc URL và lấy dữ liệu
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleplaceData; // Trả dữ liệu để sử dụng trong onPostExecute
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("API Response", s); // Ghi log phản hồi từ API để kiểm tra

        List<HashMap<String, String>> nearByPlacesList = null;
        DataParser dataParser = new DataParser(); // Lớp phụ trợ để phân tích dữ liệu
        nearByPlacesList = dataParser.parse(s); // Phân tích dữ liệu thành danh sách các địa điểm
        Log.d("MarkerDebug", "Nearby Places List Size: " + nearByPlacesList.size());
        DisplayNearbyPlaces(nearByPlacesList); // Hiển thị các địa điểm đã phân tích lên bản đồ
    }

    private void DisplayNearbyPlaces(List<HashMap<String, String>> nearByPlacesList) {
        for (int i = 0; i < nearByPlacesList.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions(); // Tạo tùy chọn cho marker
            HashMap<String, String> googlenearByPlace = nearByPlacesList.get(i); // Lấy dữ liệu địa điểm
            String nameOfPlace = googlenearByPlace.get("place_name");
            String vicinity = googlenearByPlace.get("vicinity");

            // Kiểm tra xem lat và lng có khác null trước khi phân tích
            String latStr = googlenearByPlace.get("Lat");
            String lngStr = googlenearByPlace.get("lng");
            if (latStr != null && lngStr != null) {
                double lat = Double.parseDouble(latStr);
                double lng = Double.parseDouble(lngStr);

                LatLng latLng = new LatLng(lat, lng); // Tạo đối tượng LatLng

                markerOptions.position(latLng); // Đặt vị trí cho marker
                markerOptions.title(nameOfPlace + ": " + vicinity); // Đặt tiêu đề cho marker
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)); // Đặt biểu tượng cho marker

                mMap.addMarker(markerOptions); // Thêm marker vào bản đồ
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng)); // Di chuyển camera đến marker
                mMap.animateCamera(CameraUpdateFactory.zoomTo(10)); // Zoom vào marker

                Log.d("MarkerDebug", "Name of Place: " + nameOfPlace);
                Log.d("MarkerDebug", "Latitude: " + lat);
                Log.d("MarkerDebug", "Longitude: " + lng);
            } else {
                Log.e("MarkerDebug", "Latitude or Longitude is null for place " + nameOfPlace);
            }
        }
    }
}
