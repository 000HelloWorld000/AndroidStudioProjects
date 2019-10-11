package com.example.appnhatro.Model;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appnhatro.Controller.InterfaceChiTietNhaTro;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class WifiNhaTro {
    String ten, matkhau, ngaydang;
    private DatabaseReference nodeWifiNhaTro;

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

    public void LayDSWifiNhaTro(String maNT, final InterfaceChiTietNhaTro interfaceChiTietNhaTro) {
        Query querynodeWifiNhaTro = FirebaseDatabase.getInstance().getReference().child("wifinhatro").child(maNT);
        querynodeWifiNhaTro.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot value : dataSnapshot.getChildren()) {
                    WifiNhaTro wifiNhaTro = value.getValue(WifiNhaTro.class);
                    interfaceChiTietNhaTro.HienThiDSWifi(wifiNhaTro);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void ThemWIFI(final Context context, WifiNhaTro wifiNhaTro, String manhatro) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("wifinhatro").child(manhatro);
        database.push().setValue(wifiNhaTro, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(context, "Them thanh cong", Toast.LENGTH_LONG).show();
            }
        });
    }
}
