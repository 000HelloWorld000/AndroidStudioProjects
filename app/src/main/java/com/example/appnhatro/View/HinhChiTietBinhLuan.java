package com.example.appnhatro.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appnhatro.Adapter.AdapterRecycleAnhBinhLuan;
import com.example.appnhatro.Model.BinhLuanModel;
import com.example.appnhatro.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HinhChiTietBinhLuan extends AppCompatActivity {

    ImageView AnhbDaiDienBinhLuan;
    RecyclerView recyclerViewHinhBinhLuan;
    BinhLuanModel binhLuanModel;
    TextView txtTieudebinhluan,txtNoiDungBInhluan,txtDiemBinhLuan;
    List<Bitmap> bitmapList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layoutbinhluan);
        txtTieudebinhluan = findViewById(R.id.txtTieuDeBinhLuan);
        txtNoiDungBInhluan = findViewById(R.id.txtNoiDungBinhLuan);
        txtDiemBinhLuan = findViewById(R.id.txtDiem);
        AnhbDaiDienBinhLuan = findViewById(R.id.imgBinhLuan);
        recyclerViewHinhBinhLuan =  findViewById(R.id.recycle_anhbinhluan);

        bitmapList = new ArrayList<>();

        binhLuanModel = getIntent().getParcelableExtra("binhluanmodel");
        txtTieudebinhluan.setText(binhLuanModel.getTieude());
        txtNoiDungBInhluan.setText(binhLuanModel.getNoidung());
        txtDiemBinhLuan.setText(binhLuanModel.getChamdiem()+"");
        setHinhAnhBinhLuan(AnhbDaiDienBinhLuan,binhLuanModel.getThanhVienModel().getHinhAnh());

        for(String linkhinh : binhLuanModel.getHinhBinhLuanList())
        {

            StorageReference storageAnhUser= FirebaseStorage.getInstance().getReference().child("hinhbinhluan").child(linkhinh);
            long ONEMEGABYTE =1024*1024;
            storageAnhUser.getBytes(ONEMEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    bitmapList.add(bitmap);
                    if(bitmapList.size() == binhLuanModel.getHinhBinhLuanList().size())
                    {
                        AdapterRecycleAnhBinhLuan adapterRecycleAnhBinhLuan = new AdapterRecycleAnhBinhLuan(HinhChiTietBinhLuan.this,R.layout.item_anhbinhluan,bitmapList,binhLuanModel,true);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(HinhChiTietBinhLuan.this,2);
                        recyclerViewHinhBinhLuan.setLayoutManager(layoutManager);
                        recyclerViewHinhBinhLuan.setAdapter(adapterRecycleAnhBinhLuan);
                        adapterRecycleAnhBinhLuan.notifyDataSetChanged();
                    }
                }
            });
        }


    }
    private void setHinhAnhBinhLuan(final ImageView imageView, String linkuser)
    {
        StorageReference storageAnhUser= FirebaseStorage.getInstance().getReference().child("anhuser").child(linkuser);
        long ONEMEGABYTE =1024*1024;
        storageAnhUser.getBytes(ONEMEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}
