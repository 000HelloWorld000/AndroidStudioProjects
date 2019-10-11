package com.example.appnhatro.Model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParsePoluLine {

    public static List<LatLng> LayDanhSachToaDO(String dataJSON) {
        List<LatLng> listlatng = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(dataJSON);
            JSONArray routes = jsonObject.getJSONArray("routes");
            for (int i = 0; i < routes.length(); i++) {
                JSONObject jsonarray = routes.getJSONObject(i);
                JSONObject overviewPolyline = jsonarray.getJSONObject("overview_polyline");
                String point = overviewPolyline.getString("points");

                listlatng = PolyUtil.decode(point);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listlatng;
    }
}
