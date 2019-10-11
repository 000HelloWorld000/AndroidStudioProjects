package com.example.appnhatro.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Controller.CapNhatWiFiConTroller;
import com.example.appnhatro.R;

public class CapNhatDSWiFI extends AppCompatActivity {

    RecyclerView recyclerDSWifi;
    Button btnCapNhatWifi;
    CapNhatWiFiConTroller capNhatDSWiFI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_dswi_fi);

        btnCapNhatWifi = findViewById(R.id.btnCapnhatwifi);
        recyclerDSWifi = findViewById(R.id.recycle_dswifi);

        final String manhatro = getIntent().getStringExtra("manhatro");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerDSWifi.setLayoutManager(layoutManager);
        capNhatDSWiFI = new CapNhatWiFiConTroller(this);
        capNhatDSWiFI.HienThiCapNhatWiFiNhaTro(manhatro, recyclerDSWifi);
        btnCapNhatWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dswifi = new Intent(CapNhatDSWiFI.this, CapNhatWiFI.class);
                dswifi.putExtra("manhatro", manhatro);
                startActivity(dswifi);
            }
        });
    }
}
