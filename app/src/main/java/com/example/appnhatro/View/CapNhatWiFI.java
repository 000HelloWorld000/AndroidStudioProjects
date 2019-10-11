package com.example.appnhatro.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Controller.CapNhatWiFiConTroller;
import com.example.appnhatro.Model.WifiNhaTro;
import com.example.appnhatro.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CapNhatWiFI extends AppCompatActivity implements View.OnClickListener {
    EditText edtTenWifi, edtPassWifi;
    Button btnCapNhat;
    String manhatro;
    CapNhatWiFiConTroller capNhatWiFiConTroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_wi_fi);
        edtPassWifi = findViewById(R.id.edtPassWifi);
        edtTenWifi = findViewById(R.id.edtTenWifi);
        btnCapNhat = findViewById(R.id.btnCapNhat);

        manhatro = getIntent().getStringExtra("manhatro");
        btnCapNhat.setOnClickListener(this);
        capNhatWiFiConTroller = new CapNhatWiFiConTroller(this);
    }

    @Override
    public void onClick(View view) {
        String tenwifi = edtTenWifi.getText().toString();
        String passwifi = edtPassWifi.getText().toString();

        if (!tenwifi.isEmpty() && !passwifi.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            String pattern = "dd/MM/YYYY";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String ngaydang = simpleDateFormat.format(calendar.getTime());

            WifiNhaTro wifiNhaTro = new WifiNhaTro();
            wifiNhaTro.setMatkhau(passwifi);
            wifiNhaTro.setTen(tenwifi);
            wifiNhaTro.setNgaydang(ngaydang);

            capNhatWiFiConTroller.ThemWIFI(this, wifiNhaTro, manhatro);
        } else {
            Toast.makeText(this, "Vui long nhap day du", Toast.LENGTH_LONG).show();
        }
    }
}
