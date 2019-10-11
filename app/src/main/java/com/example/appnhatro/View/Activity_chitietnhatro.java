package com.example.appnhatro.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapter.AdapterBinhLuan;
import com.example.appnhatro.Controller.ChiTietNhaTroController;
import com.example.appnhatro.Controller.LauController;
import com.example.appnhatro.Model.NhaTro;
import com.example.appnhatro.Model.TienIchModel;
import com.example.appnhatro.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Activity_chitietnhatro extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    TextView txtGiaCa, txtNgayDangWIFI, txtTenWIFI, txtMKWIFI, txtTenNhaTro, txtDiaChiNhaTro, txtThoigianHoatDong, txtTrangThaiHoatdong, txtSoBinhLuan, txtSoCheckIn, txtSoLuuLai, txtSoAnh, txtTieudeToolbar;
    ImageView imgAnhNhaTro, imgPlay;
    NhaTro nhaTro;
    Button btnBinhLuan;
    RecyclerView recyclerViewBinhLuan;
    Toolbar toolbar;
    AdapterBinhLuan adapterBinhLuan;
    GoogleMap googleMap;
    LinearLayout khungtienich, khungwifi;
    LinearLayout khungtinhnang;
    VideoView videoViewNhaTro;

    ChiTietNhaTroController chiTietNhaTroController;
    LauController lauController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietnhatro);

        nhaTro = getIntent().getParcelableExtra("nhatromodel");

        txtTenNhaTro = findViewById(R.id.txtTenNhatro);
        txtDiaChiNhaTro = findViewById(R.id.txtDiaChiNhaTroChiTiet);
        txtThoigianHoatDong = findViewById(R.id.txtThoiGianMoCua);
        txtTrangThaiHoatdong = findViewById(R.id.txtThoiGianDongCua);
        txtSoBinhLuan = findViewById(R.id.txtSoBinhLuan);
        txtSoCheckIn = findViewById(R.id.txtCheckIn);
        txtSoAnh = findViewById(R.id.txtSoAnh);
        txtSoLuuLai = findViewById(R.id.txtLuuLai);
        txtGiaCa = findViewById(R.id.txtGiaCa);
        imgAnhNhaTro = findViewById(R.id.imgAnhChiTietNhaTro);
        toolbar = findViewById(R.id.toolbar);
        txtTieudeToolbar = findViewById(R.id.txtTieudeToolBar);
        toolbar.setTitle("");
        recyclerViewBinhLuan = findViewById(R.id.recycle_binhluan);
        khungtienich = findViewById(R.id.khungTienTich);
        txtTenWIFI = findViewById(R.id.txtTenWIFI);
        txtMKWIFI = findViewById(R.id.txtMatKhauWIFI);
        khungwifi = findViewById(R.id.KhungWifi);
        txtNgayDangWIFI = findViewById(R.id.txtNgayDang);
        khungtinhnang = findViewById(R.id.khungtinhnang);
        btnBinhLuan = findViewById(R.id.btnBinhLuan);
        imgPlay = findViewById(R.id.imgPlay);
        videoViewNhaTro = findViewById(R.id.videoTrailer);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        khungtinhnang.setOnClickListener(this);
        btnBinhLuan.setOnClickListener(this);
        chiTietNhaTroController = new ChiTietNhaTroController();
        lauController = new LauController();

        mapFragment.getMapAsync(this);

        HienThiChiTiet();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void HienThiChiTiet() {
        Calendar calendar = Calendar.getInstance();
        String pattern = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dongcua = nhaTro.getGiodongcua();
        String mocua = nhaTro.getGiomocua();

        try {
            String giohientai = simpleDateFormat.format(calendar.getTime());
            Date hientai = simpleDateFormat.parse(giohientai);
            Date giodongcua = simpleDateFormat.parse(dongcua);
            Date giomocua = simpleDateFormat.parse(mocua);

            if (hientai.after(giomocua) && hientai.before(giodongcua)) {
                txtTrangThaiHoatdong.setText(getString(R.string.dangmocua));
            } else {
                txtTrangThaiHoatdong.setText(getString(R.string.dangdongcua));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        txtTieudeToolbar.setText(nhaTro.getTennhatro() + "");
        txtTenNhaTro.setText(nhaTro.getTennhatro() + "");
        txtDiaChiNhaTro.setText(nhaTro.getChiNhanhNhaTroModelList().get(0).getDiachi() + "");
        txtThoigianHoatDong.setText(nhaTro.getGiomocua() + "-" + nhaTro.getGiodongcua());
        txtSoAnh.setText(nhaTro.getListHinhAnhNhaTro().size() + "");
        txtSoBinhLuan.setText(nhaTro.getBinhLuanModelList().size() + "");
        txtThoigianHoatDong.setText(mocua + " - " + dongcua);

        //Download hinh tien ich
        getDownload();

        NumberFormat numberFormat = new DecimalFormat("###,###");
        String giatoida = numberFormat.format(nhaTro.getGiatoida());
        String giatoithieu = numberFormat.format(nhaTro.getGiatoithieu());
        if (!giatoida.isEmpty() && !giatoithieu.isEmpty()) {
            txtGiaCa.setText("Gia ca:" + giatoithieu + "d - " + giatoida + "d");
        } else {
            txtGiaCa.setVisibility(View.INVISIBLE);
        }
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanhnhatro").child(nhaTro.getListHinhAnhNhaTro().get(0));
        long ONEMEGABYTE = 1024 * 1024;
        storageReference.getBytes(ONEMEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imgAnhNhaTro.setImageBitmap(bitmap);
            }
        });
        if (nhaTro.getVideogioithieu() != null) {
            imgPlay.setVisibility(View.VISIBLE);
            videoViewNhaTro.setVisibility(View.VISIBLE);
            imgAnhNhaTro.setVisibility(View.GONE);
            StorageReference storageVideos = FirebaseStorage.getInstance().getReference().child("videos").child(nhaTro.getVideogioithieu());
            storageVideos.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    videoViewNhaTro.setVideoURI(uri);
                    videoViewNhaTro.seekTo(1);
                }
            });
            imgPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    videoViewNhaTro.start();
                    MediaController mediaController = new MediaController(Activity_chitietnhatro.this);
                    videoViewNhaTro.setMediaController(mediaController);
                    imgPlay.setVisibility(View.GONE);
                }
            });
        } else {
            imgPlay.setVisibility(View.GONE);
            videoViewNhaTro.setVisibility(View.GONE);
            imgAnhNhaTro.setVisibility(View.VISIBLE);
        }


        //Load anh user binh luan
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewBinhLuan.setLayoutManager(layoutManager);
        adapterBinhLuan = new AdapterBinhLuan(this, R.layout.custom_layoutbinhluan, nhaTro.getBinhLuanModelList());
        recyclerViewBinhLuan.setAdapter(adapterBinhLuan);
        adapterBinhLuan.notifyDataSetChanged();
        NestedScrollView nestedScrollView = findViewById(R.id.nestTedScrollChiTiet);
        nestedScrollView.smoothScrollTo(0, 0);

        chiTietNhaTroController.HienThiWiFiNhaTro(nhaTro.getManhatro(), txtTenWIFI, txtMKWIFI, txtNgayDangWIFI);
        khungwifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iDSWifi = new Intent(Activity_chitietnhatro.this, CapNhatDSWiFI.class);
                iDSWifi.putExtra("manhatro", nhaTro.getManhatro());
                startActivity(iDSWifi);
            }
        });

        lauController.getDSLauNhaTro(nhaTro.getManhatro());
    }

    private void getDownload() {
        for (String linkhinh : nhaTro.getTienich()) {
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("quanlytienichs").child(linkhinh);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TienIchModel tienIchModel = dataSnapshot.getValue(TienIchModel.class);
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhtienich").child(tienIchModel.getHinhtienich());
                    long ONEMEGABYTE = 1024 * 1024;
                    storageReference.getBytes(ONEMEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            ImageView imageTienich = new ImageView(Activity_chitietnhatro.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);
                            imageTienich.setLayoutParams(layoutParams);
                            imageTienich.setImageBitmap(bitmap);
                            imageTienich.setPadding(10, 5, 10, 10);
                            imageTienich.setScaleType(ImageView.ScaleType.FIT_XY);
                            khungtienich.addView(imageTienich);
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        double latitude = nhaTro.getChiNhanhNhaTroModelList().get(0).getLatitude();
        double longtitude = nhaTro.getChiNhanhNhaTroModelList().get(0).getLongtitude();

        LatLng latLng = new LatLng(latitude, longtitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(nhaTro.getTennhatro());
        googleMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        googleMap.moveCamera(cameraUpdate);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.khungtinhnang:
                Intent iDanDuong = new Intent(this, DanDuongNhaTro.class);
                iDanDuong.putExtra("latitude", nhaTro.getChiNhanhNhaTroModelList().get(0).getLatitude());
                iDanDuong.putExtra("longtitude", nhaTro.getChiNhanhNhaTroModelList().get(0).getLongtitude());
                startActivity(iDanDuong);
                break;
            case R.id.btnBinhLuan:
                Intent iBinhLuan = new Intent(this, BinhLuanActivity.class);
                iBinhLuan.putExtra("tennhatro", nhaTro.getTennhatro());
                iBinhLuan.putExtra("manhattro", nhaTro.getManhatro());
                iBinhLuan.putExtra("diachi", nhaTro.getChiNhanhNhaTroModelList().get(0).getDiachi());
                startActivity(iBinhLuan);
        }
    }
}
