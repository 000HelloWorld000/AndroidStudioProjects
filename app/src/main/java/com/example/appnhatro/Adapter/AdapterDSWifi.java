package com.example.appnhatro.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Model.WifiNhaTro;
import com.example.appnhatro.R;

import java.util.List;

public class AdapterDSWifi extends RecyclerView.Adapter<AdapterDSWifi.ViewHolderDSWifi> {

    Context context;
    int layout;
    List<WifiNhaTro> wifiNhaTroList;

    public AdapterDSWifi(Context context, int layout, List<WifiNhaTro> wifiNhaTroList) {
        this.context = context;
        this.layout = layout;
        this.wifiNhaTroList = wifiNhaTroList;
    }

    @NonNull
    @Override
    public AdapterDSWifi.ViewHolderDSWifi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        ViewHolderDSWifi viewHolderDSWifi = new ViewHolderDSWifi(view);
        return viewHolderDSWifi;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDSWifi.ViewHolderDSWifi holder, int position) {
        WifiNhaTro wifiNhaTro = wifiNhaTroList.get(position);
        holder.txtTenwifi.setText(wifiNhaTro.getTen());
        holder.txtTenMatKhauWiFi.setText(wifiNhaTro.getMatkhau());
        holder.txtNgayDang.setText(wifiNhaTro.getNgaydang());
    }

    @Override
    public int getItemCount() {
        return wifiNhaTroList.size();
    }

    public class ViewHolderDSWifi extends RecyclerView.ViewHolder{
        TextView txtTenwifi,txtTenMatKhauWiFi,txtNgayDang;
        public ViewHolderDSWifi(@NonNull View itemView) {
            super(itemView);
            txtNgayDang = itemView.findViewById(R.id.txtNgayDang);
            txtTenMatKhauWiFi = itemView.findViewById(R.id.txtMatKhauWIFI);
            txtTenwifi = itemView.findViewById(R.id.txtTenWIFI);
        }
    }
}
