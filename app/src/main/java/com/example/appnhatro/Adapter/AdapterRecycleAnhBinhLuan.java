package com.example.appnhatro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Model.BinhLuanModel;
import com.example.appnhatro.R;
import com.example.appnhatro.View.HinhChiTietBinhLuan;

import java.util.List;

public class AdapterRecycleAnhBinhLuan extends RecyclerView.Adapter<AdapterRecycleAnhBinhLuan.ViewHolderAnhBinhLuan> {

    Context context;
    int layout;
    List<Bitmap> listHinh;
    BinhLuanModel binhLuanModel;
    boolean isChitietBinhluan;

    public AdapterRecycleAnhBinhLuan(Context context, int layout, List<Bitmap> listHinh, BinhLuanModel binhLuanModel, boolean isChiTietBinhLuan) {
        this.context = context;
        this.layout = layout;
        this.listHinh = listHinh;
        this.binhLuanModel = binhLuanModel;
        this.isChitietBinhluan = isChiTietBinhLuan;
    }

    @NonNull
    @Override
    public ViewHolderAnhBinhLuan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolderAnhBinhLuan viewHolderAnhBinhLuan = new ViewHolderAnhBinhLuan(view);

        return viewHolderAnhBinhLuan;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderAnhBinhLuan holder, int position) {
        holder.imganhBinhluan.setImageBitmap(listHinh.get(position));
        if (!isChitietBinhluan) {
            if (position == 3) {
                int sohinhconlai = listHinh.size() - 4;
                if (sohinhconlai > 0) {
                    holder.khunghinhbinhluan.setVisibility(View.VISIBLE);
                    holder.txtSoAnhBinhLuan.setText("+" + sohinhconlai);
                    holder.imganhBinhluan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent hinhchitiet = new Intent(context, HinhChiTietBinhLuan.class);
                            hinhchitiet.putExtra("binhluanmodel", binhLuanModel);
                            context.startActivity(hinhchitiet);
                        }
                    });
                }
            }

        }

    }

    @Override
    public int getItemCount() {
        if (!isChitietBinhluan) {
            if (listHinh.size() < 4)
                return listHinh.size();
            else {
                return 4;
            }
        } else
            return listHinh.size();
    }

    public class ViewHolderAnhBinhLuan extends RecyclerView.ViewHolder {
        ImageView imganhBinhluan;
        TextView txtSoAnhBinhLuan;
        FrameLayout khunghinhbinhluan;

        public ViewHolderAnhBinhLuan(@NonNull View itemView) {
            super(itemView);
            imganhBinhluan = itemView.findViewById(R.id.imgAnhBinhLuan);
            txtSoAnhBinhLuan = itemView.findViewById(R.id.txtSoHinhBinhLuan);
            khunghinhbinhluan = itemView.findViewById(R.id.khunghinhbinhluan);
        }
    }


}
