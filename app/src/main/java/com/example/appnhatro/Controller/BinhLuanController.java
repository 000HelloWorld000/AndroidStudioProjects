package com.example.appnhatro.Controller;

import com.example.appnhatro.Model.BinhLuanModel;

import java.util.List;

public class BinhLuanController {
    BinhLuanModel binhLuanModel;

    public BinhLuanController() {
        binhLuanModel = new BinhLuanModel();
    }

    public void ThemBinhLuan(String manhatro, BinhLuanModel binhLuanModel, List<String> listhinh) {
        binhLuanModel.ThemBinhLuan(binhLuanModel, listhinh, manhatro);
    }
}
