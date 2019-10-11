package com.example.appnhatro.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.R;

import java.util.List;

public class AdapterHienThiHinhDuocChon extends RecyclerView.Adapter<AdapterHienThiHinhDuocChon.ViewHolder> {
    Context context;
    int layout;
    List<String> list;

    public AdapterHienThiHinhDuocChon(Context context, int layout, List<String> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterHienThiHinhDuocChon.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHienThiHinhDuocChon.ViewHolder holder, final int position) {
        Uri uri = Uri.parse(list.get(position));
        holder.imgView.setImageURI(uri);
        holder.imgXoa.setTag(position);
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int vitri = (int) view.getTag();
                list.remove(vitri);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgXoa, imgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.imgAnhDuocChon);
            imgXoa = itemView.findViewById(R.id.imgDelete);
        }
    }
}
