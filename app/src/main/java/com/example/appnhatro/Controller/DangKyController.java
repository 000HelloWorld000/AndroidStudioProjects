package com.example.appnhatro.Controller;

import com.example.appnhatro.Model.ThanhVienModel;

public class DangKyController {
    ThanhVienModel thanhVienModel;

    public DangKyController() {
    }

    public void ThemThanhVienController(ThanhVienModel thanhVienModel, String uid) {
        this.thanhVienModel.ThemThongTinThanhVien(thanhVienModel, uid);
    }
}
