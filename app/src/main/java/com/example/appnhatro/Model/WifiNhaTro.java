package com.example.appnhatro.Model;

import androidx.annotation.NonNull;

import com.example.appnhatro.Controller.InterfaceChiTietNhaTro;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WifiNhaTro {
    String ten,matkhau,ngaydang;

    public WifiNhaTro() {
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getNgaydang() {
        return ngaydang;
    }

    public void setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
    }

    private DatabaseReference nodeWifiNhaTro;
    public  void LayDSWifiNhaTro(String maNT, final InterfaceChiTietNhaTro interfaceChiTietNhaTro)
    {
        nodeWifiNhaTro = FirebaseDatabase.getInstance().getReference().child("wifinhatro").child(maNT);
        nodeWifiNhaTro.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot value : dataSnapshot.getChildren()) {
                    WifiNhaTro wifiNhaTro = value.getValue(WifiNhaTro.class);
                    interfaceChiTietNhaTro.HienThiDSWifi(wifiNhaTro);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
