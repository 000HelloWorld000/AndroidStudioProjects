package com.example.appnhatro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Model.BinhLuanModel;
import com.example.appnhatro.Model.ChiNhanhNhaTroModel;
import com.example.appnhatro.Model.NhaTro;
import com.example.appnhatro.R;
import com.example.appnhatro.View.Activity_chitietnhatro;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AdapterOdau extends RecyclerView.Adapter<AdapterOdau.ViewHolder> {

    List<NhaTro> DSNhaTro;
    int resource;
    Context context;

    public AdapterOdau(List<NhaTro> DSNhaTro, int resource, Context context) {
        this.DSNhaTro = DSNhaTro;
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final NhaTro nhaTro = DSNhaTro.get(position);
        holder.txtTenNhaTro_ODau.setText(nhaTro.getTennhatro());
        if (!nhaTro.isControng()) {
            holder.btnDatCho.setVisibility(View.GONE);
        }
        if (nhaTro.getListHinhAnhNhaTro().size() > 0) {
            holder.imgHinhNhaTro.setImageBitmap(nhaTro.getBitmapList().get(0));
        }
        if (nhaTro.getBinhLuanModelList().size() > 0) {
            BinhLuanModel binhLuanModel = nhaTro.getBinhLuanModelList().get(0);
            holder.txtTieuDeBinhLuan.setText(binhLuanModel.getTieude());
            holder.txtNoiDungBinhLuan.setText(binhLuanModel.getNoidung());
            holder.txtDiem.setText(binhLuanModel.getChamdiem() + "");
            setHinhAnhBinhLuan(holder.imgBinhLuan, binhLuanModel.getThanhVienModel().getHinhAnh());
            if (nhaTro.getBinhLuanModelList().size() > 1) {
                BinhLuanModel binhLuanModel2 = nhaTro.getBinhLuanModelList().get(1);
                holder.txtTieuDeBinhLuan2.setText(binhLuanModel2.getTieude());
                holder.txtNoiDungBinhLuan2.setText(binhLuanModel2.getNoidung());
                holder.txtDiem2.setText(binhLuanModel2.getChamdiem() + "");
                setHinhAnhBinhLuan(holder.imgBinhLuan2, binhLuanModel2.getThanhVienModel().getHinhAnh());
            }
            holder.txtTongDiemBinhLuan.setText(nhaTro.getBinhLuanModelList().size() + "");
            int tongsobinhluan = 0;
            double DiemTBNhaTro = 0;
            for (BinhLuanModel binhLuanModel1 : nhaTro.getBinhLuanModelList()) {
                tongsobinhluan += binhLuanModel1.getHinhBinhLuanList().size();
                DiemTBNhaTro += binhLuanModel.getChamdiem();
            }

            double tongDiemTB = DiemTBNhaTro / nhaTro.getBinhLuanModelList().size();
            holder.txtDiemTB.setText(String.format("%.1f", tongDiemTB) + "");

            if (tongsobinhluan > 0) {
                holder.txtTongDiemAnh.setText(tongsobinhluan + "");
            } else {
                holder.txtTongDiemAnh.setText("0");
            }
        } else {
            holder.imgBinhLuan.setVisibility(View.GONE);
            holder.imgBinhLuan2.setVisibility(View.GONE);
            holder.txtNoiDungBinhLuan.setVisibility(View.GONE);
            holder.txtNoiDungBinhLuan2.setVisibility(View.GONE);
            holder.txtTieuDeBinhLuan.setVisibility(View.GONE);
            holder.txtTieuDeBinhLuan2.setVisibility(View.GONE);
            holder.txtDiem2.setVisibility(View.GONE);
            holder.txtDiem.setVisibility(View.GONE);
            holder.txtTongDiemBinhLuan.setText("0");
            holder.txtTongDiemAnh.setText("0");

        }
        //Lay chi nhanh nha tro va hien thi km
        if (nhaTro.getChiNhanhNhaTroModelList().size() > 0) {
            ChiNhanhNhaTroModel chiNhanhNhaTroModelTam = nhaTro.getChiNhanhNhaTroModelList().get(0);
            for (ChiNhanhNhaTroModel chiNhanhNhaTroModel : nhaTro.getChiNhanhNhaTroModelList()) {
                if (chiNhanhNhaTroModelTam.getKhoangcach() > chiNhanhNhaTroModel.getKhoangcach()) {
                    chiNhanhNhaTroModelTam = chiNhanhNhaTroModel;
                }
            }
            holder.txtDiaChi.setText(chiNhanhNhaTroModelTam.getDiachi());
            holder.txtKM.setText(String.format("%.1f", chiNhanhNhaTroModelTam.getKhoangcach()) + "km");
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentChiTietNhaTro = new Intent(context, Activity_chitietnhatro.class);
                intentChiTietNhaTro.putExtra("nhatromodel", nhaTro);
                context.startActivity(intentChiTietNhaTro);
            }
        });
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
        return DSNhaTro.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenNhaTro_ODau, txtTieuDeBinhLuan, txtNoiDungBinhLuan, txtTieuDeBinhLuan2, txtNoiDungBinhLuan2, txtDiem, txtDiem2, txtTongDiemBinhLuan, txtTongDiemAnh;
        TextView txtDiemTB, txtKM, txtDiaChi;
        Button btnDatCho;
        CardView cardView;
        ImageView imgHinhNhaTro, imgBinhLuan, imgBinhLuan2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenNhaTro_ODau = itemView.findViewById(R.id.txt_TenNhaTro_ODau);
            btnDatCho = itemView.findViewById(R.id.btnDatCho);
            imgHinhNhaTro = itemView.findViewById(R.id.imgAnhNhaTro);
            txtTieuDeBinhLuan = itemView.findViewById(R.id.txtTieuDeBinhLuan);
            txtTieuDeBinhLuan2 = itemView.findViewById(R.id.txtTieuDeBinhLuan2);
            txtNoiDungBinhLuan = itemView.findViewById(R.id.txtNoiDungBinhLuan);
            txtNoiDungBinhLuan2 = itemView.findViewById(R.id.txtNoiDungBinhLuan2);
            imgBinhLuan = itemView.findViewById(R.id.imgBinhLuan);
            imgBinhLuan2 = itemView.findViewById(R.id.imgBinhLuan2);
            txtDiem = itemView.findViewById(R.id.txtDiem);
            txtDiem2 = itemView.findViewById(R.id.txtDiem2);
            txtTongDiemAnh = itemView.findViewById(R.id.txtTongAnhBinhLuan);
            txtTongDiemBinhLuan = itemView.findViewById(R.id.txtTongDiemBinhLuan);
            txtDiemTB = itemView.findViewById(R.id.txtDiemNhaTro);
            txtKM = itemView.findViewById(R.id.txtKm);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChi);
            cardView = itemView.findViewById(R.id.cardview);
        }

    }
}
