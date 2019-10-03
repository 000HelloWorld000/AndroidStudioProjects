package com.example.appnhatro.Model;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.appnhatro.Controller.InterFaceODau;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NhaTro implements Parcelable {
    boolean controng;
    String giodongcua,giomocua,tennhatro,videogioithieu,manhatro;
    List<String> tienich,listHinhAnhNhaTro;
    Long luotthich,giatoithieu,giatoida;
    List<BinhLuanModel> binhLuanModelList;
    List<ChiNhanhNhaTroModel> chiNhanhNhaTroModelList;
    List<Bitmap> bitmapList;

    protected NhaTro(Parcel in) {
        controng = in.readByte() != 0;
        giodongcua = in.readString();
        giomocua = in.readString();
        tennhatro = in.readString();
        videogioithieu = in.readString();
        manhatro = in.readString();
        tienich = in.createStringArrayList();
        listHinhAnhNhaTro = in.createStringArrayList();
        luotthich = in.readLong();
        giatoida = in.readLong();
        giatoithieu = in.readLong();
        chiNhanhNhaTroModelList = new ArrayList<ChiNhanhNhaTroModel>();
        in.readTypedList(chiNhanhNhaTroModelList,ChiNhanhNhaTroModel.CREATOR);
        binhLuanModelList = new ArrayList<BinhLuanModel>();
        in.readTypedList(binhLuanModelList,BinhLuanModel.CREATOR);


    }

    public Long getGiatoithieu() {
        return giatoithieu;
    }

    public void setGiatoithieu(Long giatoithieu) {
        this.giatoithieu = giatoithieu;
    }

    public Long getGiatoida() {
        return giatoida;
    }

    public void setGiatoida(Long giatoida) {
        this.giatoida = giatoida;
    }

    public static final Creator<NhaTro> CREATOR = new Creator<NhaTro>() {
        @Override
        public NhaTro createFromParcel(Parcel in) {
            return new NhaTro(in);
        }

        @Override
        public NhaTro[] newArray(int size) {
            return new NhaTro[size];
        }
    };

    public List<Bitmap> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    public List<ChiNhanhNhaTroModel> getChiNhanhNhaTroModelList() {
        return chiNhanhNhaTroModelList;
    }

    public void setChiNhanhNhaTroModelList(List<ChiNhanhNhaTroModel> chiNhanhNhaTroModelList) {
        this.chiNhanhNhaTroModelList = chiNhanhNhaTroModelList;
    }

    public List<BinhLuanModel> getBinhLuanModelList() {
        return binhLuanModelList;
    }

    public void setBinhLuanModelList(List<BinhLuanModel> binhLuanModelList) {
        this.binhLuanModelList = binhLuanModelList;
    }

    private DatabaseReference databaseNhatTro ;

    public NhaTro() {
        databaseNhatTro = FirebaseDatabase.getInstance().getReference();
    }

    public List<String> getListHinhAnhNhaTro() {
        return listHinhAnhNhaTro;
    }

    public void setListHinhAnhNhaTro(List<String> listHinhAnhNhaTro) {
        this.listHinhAnhNhaTro = listHinhAnhNhaTro;
    }

    public Long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(Long luotthich) {
        this.luotthich = luotthich;
    }

    public boolean isControng() {
        return controng;
    }

    public void setControng(boolean controng) {
        this.controng = controng;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTennhatro() {
        return tennhatro;
    }

    public void setTennhatro(String tennhatro) {
        this.tennhatro = tennhatro;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public String getManhatro() {
        return manhatro;
    }

    public void setManhatro(String manhatro) {
        this.manhatro = manhatro;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    private DataSnapshot dataroot;
    public void getDSNhaTro(final InterFaceODau interFaceODau, final Location vitrihientai, final int itemtieptheo, final int itembandau ){
        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataroot = dataSnapshot;
                LayDSQuanAn(dataSnapshot,interFaceODau,vitrihientai,itemtieptheo,itembandau);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        if(dataroot != null)
        {
            LayDSQuanAn(dataroot,interFaceODau,vitrihientai,itemtieptheo,itembandau);
        }
        else {
            databaseNhatTro.addListenerForSingleValueEvent(valueEventListener);
        }


    }
    private void LayDSQuanAn(DataSnapshot dataSnapshot,InterFaceODau interFaceODau, Location vitrihientai,int itemtieptheo,int itembandau)
    {
        DataSnapshot dataNhaTro = dataSnapshot.child("nhatros");
        //DS nha tro
        int i=0;//Dem load dc bao nhieu nha tro r
        for(DataSnapshot dataSnapshotValue: dataNhaTro.getChildren())
        {
            if(i == itemtieptheo)
            {
                break;
            }
            if(i < itembandau)
            {
                i++;
                continue;
            }
            i++;
            NhaTro nhaTroModel = dataSnapshotValue.getValue(NhaTro.class);
            nhaTroModel.setManhatro(dataSnapshotValue.getKey());

            //Lay DS hinh anh cua quan an theo ma
            DataSnapshot dataHinhAnh = dataSnapshot.child("hinhanhnhatros").child(dataSnapshotValue.getKey());
            List<String> dsHinh =new ArrayList<>();
            for (DataSnapshot valueHinhAnh : dataHinhAnh.getChildren())
            {
                dsHinh.add(valueHinhAnh.getValue(String.class));
            }

            nhaTroModel.setListHinhAnhNhaTro(dsHinh);

            //lay ds binh luan cua quan an
            DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(nhaTroModel.getManhatro());
            List<BinhLuanModel> binhLuanModels = new ArrayList<>();
            for(DataSnapshot vaueBinhLuan : snapshotBinhLuan.getChildren())
            {
                BinhLuanModel binhLuanModel = vaueBinhLuan.getValue(BinhLuanModel.class);
                binhLuanModel.setMabinhluan(vaueBinhLuan.getKey());
                ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                binhLuanModel.setThanhVienModel(thanhVienModel);
                List<String> hinhBinhLuans = new ArrayList<>();

                DataSnapshot snapshotHinhAnhBinhLuan = dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getMabinhluan());
                for (DataSnapshot anhBinhLuans : snapshotHinhAnhBinhLuan.getChildren())
                {
                    hinhBinhLuans.add(anhBinhLuans.getValue(String.class));
                }
                binhLuanModel.setHinhBinhLuanList(hinhBinhLuans);
                binhLuanModels.add(binhLuanModel);
            }
            nhaTroModel.setBinhLuanModelList(binhLuanModels);

            //lay chi nhanh nha tro
            DataSnapshot chinhanhnhatro = dataSnapshot.child("chinhanhnhatros").child(nhaTroModel.getManhatro());
            List<ChiNhanhNhaTroModel> chiNhanhNhaTroModels = new ArrayList<>();
            for(DataSnapshot valueChiNhanhNhatro : chinhanhnhatro.getChildren())
            {
                ChiNhanhNhaTroModel chiNhanhNhaTroModel = valueChiNhanhNhatro.getValue(ChiNhanhNhaTroModel.class);
                Location vitrinhatro = new Location("");
                vitrinhatro.setLatitude(chiNhanhNhaTroModel.getLatitude());
                vitrinhatro.setLongitude(chiNhanhNhaTroModel.getLongtitude());

                double khoangcach = vitrihientai.distanceTo(vitrinhatro)/10000;
                chiNhanhNhaTroModel.setKhoangcach(khoangcach);

                chiNhanhNhaTroModels.add(chiNhanhNhaTroModel);
            }
            nhaTroModel.setChiNhanhNhaTroModelList(chiNhanhNhaTroModels);

            interFaceODau.getDSNhaTro(nhaTroModel);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (controng ? 1 : 0));
        parcel.writeString(giodongcua);
        parcel.writeString(giomocua);
        parcel.writeString(tennhatro);
        parcel.writeString(videogioithieu);
        parcel.writeString(manhatro);
        parcel.writeStringList(tienich);
        parcel.writeStringList(listHinhAnhNhaTro);
        parcel.writeLong(luotthich);
        parcel.writeLong(giatoida);
        parcel.writeLong(giatoithieu);
        parcel.writeTypedList(chiNhanhNhaTroModelList);
        parcel.writeTypedList(binhLuanModelList);

    }
}
