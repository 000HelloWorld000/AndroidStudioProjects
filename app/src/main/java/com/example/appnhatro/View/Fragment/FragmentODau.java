package com.example.appnhatro.View.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Controller.ODauController;

import com.example.appnhatro.R;



public class FragmentODau extends Fragment {

    ODauController  oDauController;
    RecyclerView recyclerView_odau;
    ProgressBar progressBarODau;
    SharedPreferences sharedPreferences;
    NestedScrollView nestedScrollView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_odau,container,false);

        recyclerView_odau = view.findViewById(R.id.recycle_odau);
        progressBarODau = view.findViewById(R.id.progress_barODau);
        nestedScrollView = view.findViewById(R.id.nestTedScroll);

        sharedPreferences = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);
        String latitude = sharedPreferences.getString("latitude","0");
        String longtitude = sharedPreferences.getString("longtitude","0");

        Location vitrihientai = new Location("");
        vitrihientai.setLatitude(Double.parseDouble(latitude));
        vitrihientai.setLongitude(Double.parseDouble(longtitude));

        oDauController = new ODauController(getContext());
        oDauController.getDSQuanAnController(getContext(),nestedScrollView,recyclerView_odau,progressBarODau,vitrihientai);

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();

    }

}
