package com.example.appnhatro.Model;

import androidx.annotation.NonNull;

import com.example.appnhatro.Controller.LauInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LauModel {
    String malau,tenlau;
    List<PhongModel> phongModelList;

    public List<PhongModel> getPhongModelList() {
        return phongModelList;
    }

    public void setPhongModelList(List<PhongModel> phongModelList) {
        this.phongModelList = phongModelList;
    }

    public String getMalau() {
        return malau;
    }

    public void setMalau(String malau) {
        this.malau = malau;
    }

    public String getTenlau() {
        return tenlau;
    }

    public void setTenlau(String tenlau) {
        this.tenlau = tenlau;
    }

    public void getDSThucDonTheoMa(String manhatro, final LauInterface lauInterface) {
        final DatabaseReference dataLau = FirebaseDatabase.getInstance().getReference().child("danhsachphongnhatros").child(manhatro);
        dataLau.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                final List<LauModel> lauModels = new ArrayList<>();
                for (final DataSnapshot valueLau : dataSnapshot.getChildren()) {
                    final LauModel lauModel = new LauModel();
                    DatabaseReference nodeLau = FirebaseDatabase.getInstance().getReference().child("danhsachlaus").child(valueLau.getKey());
                    nodeLau.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshotLau) {
                            String maLau = dataSnapshotLau.getKey();
                            lauModel.setMalau(dataSnapshotLau.getKey());
                            lauModel.setTenlau(dataSnapshotLau.getValue(String.class));
                            List<PhongModel> phongModelList = new ArrayList<>();
                            for (DataSnapshot value : dataSnapshot.child(maLau).getChildren()) {
                                PhongModel phongModel = value.getValue(PhongModel.class);
                                phongModel.setMaphong(value.getKey());
                                phongModelList.add(phongModel);
                            }
                            lauModel.setPhongModelList(phongModelList);
                            lauModels.add(lauModel);
                            lauInterface.getLauTheoMa(lauModels);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
