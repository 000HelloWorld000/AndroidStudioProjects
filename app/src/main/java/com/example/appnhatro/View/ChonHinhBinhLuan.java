package com.example.appnhatro.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapter.Adapter_ChonHinhBinhLuan;
import com.example.appnhatro.Model.CHonHinhModel;
import com.example.appnhatro.R;

import java.util.ArrayList;
import java.util.List;

public class ChonHinhBinhLuan extends AppCompatActivity implements View.OnClickListener{

    List<CHonHinhModel> listBinhLuan;
    RecyclerView recyclerViewChonHinh;
    ImageView imgShootCamera;
    TextView txtXong;
    Adapter_ChonHinhBinhLuan adapter_chonHinhBinhLuan;
    List<String> listhinhChon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_hinh_binh_luan);

        recyclerViewChonHinh = findViewById(R.id.recycle_ChonHinhBinhLuan);
        txtXong = findViewById(R.id.txtXong);
        imgShootCamera = findViewById(R.id.imgCameraShoot);


        listBinhLuan = new ArrayList<>();
        listhinhChon = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        adapter_chonHinhBinhLuan = new Adapter_ChonHinhBinhLuan(this,R.layout.item_chonhinhbinhluan,listBinhLuan);
        recyclerViewChonHinh.setLayoutManager(layoutManager);
        recyclerViewChonHinh.setAdapter(adapter_chonHinhBinhLuan);

        int checkPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
        if(checkPermission != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
        }
        else
        {
            getAnh();
        }

        txtXong.setOnClickListener(this);
        imgShootCamera.setOnClickListener(this);

    }
    public void getAnh()
    {
        String[] protection ={MediaStore.Images.Media.DATA};
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri,protection,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            String duongdan = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            CHonHinhModel cHonHinhModel = new CHonHinhModel(duongdan,false);
            listBinhLuan.add(cHonHinhModel);
            adapter_chonHinhBinhLuan.notifyDataSetChanged();
            cursor.moveToNext();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 2)
        {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                getAnh();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.txtXong:
                for(CHonHinhModel cHonHinhModel : listBinhLuan)
                {
                    if(cHonHinhModel.isIscheck())
                    {
                        listhinhChon.add(cHonHinhModel.getDuongDan());
                    }
                }
                Intent intent = new Intent(ChonHinhBinhLuan.this, BinhLuanActivity.class);
                intent.putStringArrayListExtra("ListHinhChon",(ArrayList<String>) listhinhChon);
                setResult(RESULT_OK,intent);
               finish();
                break;
            case R.id.imgCameraShoot:
                break;
        }
    }
}
