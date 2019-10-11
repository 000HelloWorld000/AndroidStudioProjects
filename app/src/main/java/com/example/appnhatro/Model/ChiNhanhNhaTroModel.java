package com.example.appnhatro.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ChiNhanhNhaTroModel implements Parcelable {
    public static final Creator<ChiNhanhNhaTroModel> CREATOR = new Creator<ChiNhanhNhaTroModel>() {
        @Override
        public ChiNhanhNhaTroModel createFromParcel(Parcel in) {
            return new ChiNhanhNhaTroModel(in);
        }

        @Override
        public ChiNhanhNhaTroModel[] newArray(int size) {
            return new ChiNhanhNhaTroModel[size];
        }
    };
    String diachi, machinhanh;
    double latitude, longtitude, khoangcach;

    protected ChiNhanhNhaTroModel(Parcel in) {
        diachi = in.readString();
        //machinhanh = in.readString();
        latitude = in.readDouble();
        longtitude = in.readDouble();
        khoangcach = in.readDouble();
    }

    public ChiNhanhNhaTroModel() {
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getKhoangcach() {
        return khoangcach;
    }

    public void setKhoangcach(double khoangcach) {
        this.khoangcach = khoangcach;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(diachi);
        //parcel.writeString(machinhanh);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longtitude);
        parcel.writeDouble(khoangcach);
    }
}
