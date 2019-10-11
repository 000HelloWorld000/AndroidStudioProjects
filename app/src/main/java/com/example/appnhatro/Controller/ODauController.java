package com.example.appnhatro.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.view.View;
import android.widget.ProgressBar;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapter.AdapterOdau;
import com.example.appnhatro.Model.NhaTro;
import com.example.appnhatro.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ODauController {

    NhaTro nhaTroModel;
    Context context;
    int itemdaco = 3;
    ProgressBar progressBar;

    public ODauController(Context context) {
        this.context = context;
        this.nhaTroModel = new NhaTro();
    }

    public void getDSQuanAnController(Context context, NestedScrollView nestedScrollView, final RecyclerView recyclerView_ODau, final ProgressBar progressBar, final Location vitrihientai) {
        final List<NhaTro> nhaTros = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView_ODau.setLayoutManager(layoutManager);


        final AdapterOdau adapterOdau = new AdapterOdau(nhaTros, R.layout.item_recycleview_odau, context);
        recyclerView_ODau.setAdapter(adapterOdau);
        progressBar.setVisibility(View.VISIBLE);
        final InterFaceODau interFaceODau = new InterFaceODau() {
            @Override
            public void getDSNhaTro(final NhaTro NhaTroModel) {
                final List<Bitmap> bitmaps = new ArrayList<>();
                for (String linkhinh : NhaTroModel.getListHinhAnhNhaTro()) {
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanhnhatro").child(linkhinh);
                    long ONEMEGABYTE = 1024 * 1024;
                    storageReference.getBytes(ONEMEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            bitmaps.add(bitmap);
                            NhaTroModel.setBitmapList(bitmaps);
                            if (NhaTroModel.getBitmapList().size() == NhaTroModel.getListHinhAnhNhaTro().size()) {
                                nhaTros.add(NhaTroModel);
                                adapterOdau.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }

            }
        };

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if (scrollY >= (v.getChildAt(v.getChildCount() - 1)).getMeasuredHeight() - v.getMeasuredHeight()) {
                        itemdaco += 3;
                        nhaTroModel.getDSNhaTro(interFaceODau, vitrihientai, itemdaco, itemdaco - 3);
                    }
                }
            }
        });

        nhaTroModel.getDSNhaTro(interFaceODau, vitrihientai, itemdaco, 0);
    }
}
