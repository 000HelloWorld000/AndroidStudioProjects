package com.example.appnhatro.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThanhVienModel implements Parcelable {
    public static final Creator<ThanhVienModel> CREATOR = new Creator<ThanhVienModel>() {
        @Override
        public ThanhVienModel createFromParcel(Parcel in) {
            return new ThanhVienModel(in);
        }

        @Override
        public ThanhVienModel[] newArray(int size) {
            return new ThanhVienModel[size];
        }
    };
    String hoten, hinhanh, maThanhVien;
    private DatabaseReference dataThanhVien;

    public ThanhVienModel() {
        dataThanhVien = FirebaseDatabase.getInstance().getReference().child("thanhviens");
    }

    protected ThanhVienModel(Parcel in) {
        hoten = in.readString();
        hinhanh = in.readString();
        maThanhVien = in.readString();
    }

    public String getHoTen() {
        return hoten;
    }

    public void setHoTen(String hoTen) {
        this.hoten = hoTen;
    }

    public String getHinhAnh() {
        return hinhanh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhanh = hinhAnh;
    }

    public void ThemThongTinThanhVien(ThanhVienModel thanhVienModel, String uid) {
        dataThanhVien.child(uid).setValue(thanhVienModel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(hoten);
        parcel.writeString(hinhanh);
        parcel.writeString(maThanhVien);
    }
}
