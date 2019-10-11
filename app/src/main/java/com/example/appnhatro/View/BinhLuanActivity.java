package com.example.appnhatro.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapter.AdapterHienThiHinhDuocChon;
import com.example.appnhatro.Controller.BinhLuanController;
import com.example.appnhatro.Model.BinhLuanModel;
import com.example.appnhatro.R;

import java.util.ArrayList;
import java.util.List;

public class BinhLuanActivity extends AppCompatActivity implements View.OnClickListener {
    String tennhatro, diachi;
    Toolbar toolbar;
    TextView txtTenNhaTro, txtDiaChi, txtDang;
    ImageView imgHinh;
    int REQUEST_IMAGE = 1;
    EditText edtTieuDeBinhLuan, edtNoiDungBinhLuan;
    AdapterHienThiHinhDuocChon adapterHienThiHinhDuocChon;
    RecyclerView recyclerViewHienThiHinhChon;
    String manhatro;
    BinhLuanModel binhLuanModel;
    SharedPreferences sharedPreferences;
    BinhLuanController binhLuanController;
    List<String> listHinhChon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binhluan);
        tennhatro = getIntent().getStringExtra("tennhatro");
        diachi = getIntent().getStringExtra("diachi");
        manhatro = getIntent().getStringExtra("manhattro");

        txtTenNhaTro = findViewById(R.id.txtTenNhaTro);
        txtDiaChi = findViewById(R.id.txtDiaChiNhaTro);
        toolbar = findViewById(R.id.ToolBar);
        imgHinh = findViewById(R.id.imgTakeAnh);
        txtDang = findViewById(R.id.txtDang);
        edtNoiDungBinhLuan = findViewById(R.id.edtNoiDungBinhLuan);
        edtTieuDeBinhLuan = findViewById(R.id.edtTieuDeBinhLuan);
        recyclerViewHienThiHinhChon = findViewById(R.id.recycle_anhchon);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHienThiHinhChon.setLayoutManager(layoutManager);

        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtDiaChi.setText(diachi);
        txtTenNhaTro.setText(tennhatro);

        imgHinh.setOnClickListener(this);
        txtDang.setOnClickListener(this);
        binhLuanController = new BinhLuanController();
        listHinhChon = new ArrayList<>();

        sharedPreferences = getSharedPreferences("luuDangNhap", MODE_PRIVATE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgTakeAnh:
                Intent intent = new Intent(this, ChonHinhBinhLuan.class);
                startActivityForResult(intent, REQUEST_IMAGE);
                break;
            case R.id.txtDang:
                binhLuanModel = new BinhLuanModel();
                String tieude = edtTieuDeBinhLuan.getText().toString();
                String noidung = edtNoiDungBinhLuan.getText().toString();
                binhLuanModel.setTieude(tieude);
                binhLuanModel.setNoidung(noidung);
                binhLuanModel.setChamdiem(0);
                String maUser = sharedPreferences.getString("mauser", "");
                binhLuanModel.setLuotthich(0);
                binhLuanModel.setMauser(maUser);

                binhLuanController.ThemBinhLuan(manhatro, binhLuanModel, listHinhChon);
                finish();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                listHinhChon = data.getStringArrayListExtra("ListHinhChon");
                adapterHienThiHinhDuocChon = new AdapterHienThiHinhDuocChon(this, R.layout.layout_hienthihinhduochon, listHinhChon);
                recyclerViewHienThiHinhChon.setAdapter(adapterHienThiHinhDuocChon);
                adapterHienThiHinhDuocChon.notifyDataSetChanged();
                for (String value : listHinhChon) {

                }
            }
        }

    }
}
