package com.example.appnhatro.Controller;

import com.example.appnhatro.Model.DownloadPolyLine;
import com.example.appnhatro.Model.ParsePoluLine;
import com.example.appnhatro.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DanDuongNhaTroController {
    ParsePoluLine parsePoluLine;
    DownloadPolyLine downloadPolyLine;

    public DanDuongNhaTroController() {
    }

    public void HienThiQuangDuongToNhaTro(GoogleMap googleMap, String duongDan) {
        parsePoluLine = new ParsePoluLine();


        downloadPolyLine = new DownloadPolyLine();
        downloadPolyLine.execute(duongDan);

        try {
            String dataJSON = downloadPolyLine.get();
            List<LatLng> listlatln = ParsePoluLine.LayDanhSachToaDO(dataJSON);

            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.color(R.color.blue);
            for (LatLng calue : listlatln) {
                polylineOptions.add(calue);
            }

            Polyline polyline = googleMap.addPolyline(polylineOptions);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
