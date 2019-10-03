package com.example.appnhatro.Controller;

import android.widget.TextView;

import com.example.appnhatro.Model.WifiNhaTro;

import java.util.ArrayList;
import java.util.List;

public class ChiTietNhaTroController {

    WifiNhaTro wifiNhaTro;
    List<WifiNhaTro> wifiNhaTroList;

    public ChiTietNhaTroController(){
        wifiNhaTro = new WifiNhaTro();
        wifiNhaTroList = new ArrayList<>();

    }

    public  void HienThiWiFiNhaTro(String maNhaTro, final TextView txtTenWIFI, final TextView txtWIFI, final TextView txtNgayDang){
        InterfaceChiTietNhaTro chiTietNhaTro = new InterfaceChiTietNhaTro() {
            @Override
            public void HienThiDSWifi(WifiNhaTro wifiNhaTro) {
                wifiNhaTroList.add(wifiNhaTro);
                txtTenWIFI.setText(wifiNhaTro.getTen());
                txtWIFI.setText(wifiNhaTro.getMatkhau());
                txtNgayDang.setText(wifiNhaTro.getNgaydang());
            }
        };
        wifiNhaTro.LayDSWifiNhaTro(maNhaTro,chiTietNhaTro);
    }
}
