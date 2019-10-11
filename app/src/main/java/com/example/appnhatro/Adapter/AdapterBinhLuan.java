package com.example.appnhatro.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Model.BinhLuanModel;
import com.example.appnhatro.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AdapterBinhLuan extends RecyclerView.Adapter<AdapterBinhLuan.ViewHolderBinhLuan> {
    Context context;
    int layout;
    List<BinhLuanModel> binhLuanModelList;


    public AdapterBinhLuan(Context context, int layout, List<BinhLuanModel> binhLuanModelList) {
        this.context = context;
        this.layout = layout;
        this.binhLuanModelList = binhLuanModelList;

    }

    @NonNull
    @Override
    public AdapterBinhLuan.ViewHolderBinhLuan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolderBinhLuan viewHolder = new ViewHolderBinhLuan(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderBinhLuan holder, int position) {
        final List<Bitmap> bitmapList;
        bitmapList = new ArrayList<>();
        final BinhLuanModel binhLuanModel = binhLuanModelList.get(position);
        holder.txtTieudebinhluan.setText(binhLuanModel.getTieude());
        holder.txtNoiDungBInhluan.setText(binhLuanModel.getNoidung());
        holder.txtDiemBinhLuan.setText(binhLuanModel.getChamdiem() + "");
        setHinhAnhBinhLuan(holder.AnhbDaiDienBinhLuan, binhLuanModel.getThanhVienModel().getHinhAnh());

        for (String linkhinh : binhLuanModel.getHinhBinhLuanList()) {

            StorageReference storageAnhUser = FirebaseStorage.getInstance().getReference().child("hinhbinhluan").child(linkhinh);
            long ONEMEGABYTE = 1024 * 1024;
            storageAnhUser.getBytes(ONEMEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmapList.add(bitmap);
                    if (bitmapList.size() == binhLuanModel.getHinhBinhLuanList().size()) {
                        AdapterRecycleAnhBinhLuan adapterRecycleAnhBinhLuan = new AdapterRecycleAnhBinhLuan(context, R.layout.item_anhbinhluan, bitmapList, binhLuanModel, false);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
                        holder.recyclerViewHinhBinhLuan.setLayoutManager(layoutManager);
                        holder.recyclerViewHinhBinhLuan.setAdapter(adapterRecycleAnhBinhLuan);
                        adapterRecycleAnhBinhLuan.notifyDataSetChanged();
                    }
                }
            });
        }

    }

    private void setHinhAnhBinhLuan(final ImageView imageView, String linkuser) {
        StorageReference storageAnhUser = FirebaseStorage.getInstance().getReference().child("anhuser").child(linkuser);
        long ONEMEGABYTE = 1024 * 1024;
        storageAnhUser.getBytes(ONEMEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public int getItemCount() {
        int sobinhluan = binhLuanModelList.size();
        if (sobinhluan > 5) {
            return 5;
        } else {
            return binhLuanModelList.size();
        }
    }

    public class ViewHolderBinhLuan extends RecyclerView.ViewHolder {
        ImageView AnhbDaiDienBinhLuan;
        RecyclerView recyclerViewHinhBinhLuan;
        TextView txtTieudebinhluan, txtNoiDungBInhluan, txtDiemBinhLuan;

        public ViewHolderBinhLuan(@NonNull View itemView) {
            super(itemView);
            txtTieudebinhluan = itemView.findViewById(R.id.txtTieuDeBinhLuan);
            txtNoiDungBInhluan = itemView.findViewById(R.id.txtNoiDungBinhLuan);
            txtDiemBinhLuan = itemView.findViewById(R.id.txtDiem);
            AnhbDaiDienBinhLuan = itemView.findViewById(R.id.imgBinhLuan);
            recyclerViewHinhBinhLuan = itemView.findViewById(R.id.recycle_anhbinhluan);

        }
    }


}
