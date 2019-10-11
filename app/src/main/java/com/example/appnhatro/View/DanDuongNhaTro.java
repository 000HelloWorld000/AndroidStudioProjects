package com.example.appnhatro.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Controller.DanDuongNhaTroController;
import com.example.appnhatro.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DanDuongNhaTro extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap googleMap;
    SupportMapFragment supportMapFragment;
    SharedPreferences sharedPreferences;
    double latitude = 0;
    double longtitude = 0;
    Location vitrihientai;
    DanDuongNhaTroController duongNhaTroController;
   String duongdan ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dan_duong_nha_tro);

        duongNhaTroController = new DanDuongNhaTroController();
        latitude = getIntent().getDoubleExtra("latitude",0);
        longtitude = getIntent().getDoubleExtra("longtitude",0);
        sharedPreferences = getSharedPreferences("toado", Context.MODE_PRIVATE);
        vitrihientai = new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longtitude","0")));
        duongdan = "https://maps.googleapis.com/maps/api/directions/json?origin="+vitrihientai.getLatitude()+","+vitrihientai.getLongitude()+"&destination="+latitude+","+longtitude+"&key=AIzaSyA25z_boaumfqN9WXkrkV1qXVqFBMIore0";
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.clear();
        LatLng latLng = new LatLng(vitrihientai.getLatitude(),vitrihientai.getLongitude() );
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);

        googleMap.addMarker(markerOptions);

        LatLng vitrinhatro = new LatLng(latitude,longtitude);
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(vitrinhatro);
        googleMap.addMarker(markerOptions1);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,15);
        googleMap.moveCamera(cameraUpdate);

        duongNhaTroController.HienThiQuangDuongToNhaTro(googleMap,duongdan);
    }
}
