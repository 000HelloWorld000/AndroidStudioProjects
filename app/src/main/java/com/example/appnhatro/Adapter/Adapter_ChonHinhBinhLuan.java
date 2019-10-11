package com.example.appnhatro.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Model.CHonHinhModel;
import com.example.appnhatro.R;

import java.util.List;

public class Adapter_ChonHinhBinhLuan extends RecyclerView.Adapter<Adapter_ChonHinhBinhLuan.ViewHolder> {
    Context context;
    int layout;
    List<CHonHinhModel> duongdanlist;


    public Adapter_ChonHinhBinhLuan(Context context, int layout, List<CHonHinhModel> duongdanlist) {
        this.context = context;
        this.layout = layout;
        this.duongdanlist = duongdanlist;
    }

    @NonNull
    @Override
    public Adapter_ChonHinhBinhLuan.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_ChonHinhBinhLuan.ViewHolder holder, final int position) {
        final CHonHinhModel duongdan = duongdanlist.get(position);
        Uri uri = Uri.parse(duongdan.getDuongDan());
        holder.imgAnhBinhLuan.setImageURI(uri);
        holder.chkChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                duongdanlist.get(position).setIscheck(checkBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return duongdanlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAnhBinhLuan;
        CheckBox chkChonHinh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnhBinhLuan = itemView.findViewById(R.id.imgAnhBinhLuan);
            chkChonHinh = itemView.findViewById(R.id.chkChonHinh);
        }
    }
}
