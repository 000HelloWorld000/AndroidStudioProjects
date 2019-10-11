package com.example.appnhatro.Controller;

import com.example.appnhatro.Model.LauModel;

import java.util.List;

public class LauController {

    LauModel lauModel;

    public LauController() {
        lauModel = new LauModel();
    }

    public void getDSLauNhaTro(String manhatro) {
        LauInterface lauInterface = new LauInterface() {
            @Override
            public void getLauTheoMa(List<LauModel> lauModels) {
                for (LauModel lauModel : lauModels) {

                }
            }
        };
        lauModel.getDSThucDonTheoMa(manhatro, lauInterface);
    }
}
