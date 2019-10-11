package com.example.appnhatro.Controller;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapter.AdapterDSWifi;
import com.example.appnhatro.Model.WifiNhaTro;
import com.example.appnhatro.R;

import java.util.ArrayList;
import java.util.List;

public class CapNhatWiFiConTroller {

    WifiNhaTro wifiNhaTro;
    Context context;
    List<WifiNhaTro> wifiNhaTroList;

    public CapNhatWiFiConTroller(Context context) {
        wifiNhaTro = new WifiNhaTro();
        this.context = context;
    }

    public void HienThiCapNhatWiFiNhaTro(String maNhaTro, final RecyclerView recyclerViewDSWIFI) {
        wifiNhaTroList = new ArrayList<>();
        InterfaceChiTietNhaTro chiTietNhaTro = new InterfaceChiTietNhaTro() {
            @Override
            public void HienThiDSWifi(WifiNhaTro wifiNhaTro) {
                wifiNhaTroList.add(wifiNhaTro);
                AdapterDSWifi adapterDSWifi = new AdapterDSWifi(context, R.layout.layout_wifi_chitietnhatro, wifiNhaTroList);
                recyclerViewDSWIFI.setAdapter(adapterDSWifi);
                adapterDSWifi.notifyDataSetChanged();
            }
        };
        wifiNhaTro.LayDSWifiNhaTro(maNhaTro, chiTietNhaTro);
    }

    public void ThemWIFI(Context context, WifiNhaTro wifiNhaTro, String manhatro) {
        wifiNhaTro.ThemWIFI(context, wifiNhaTro, manhatro);
    }
}
