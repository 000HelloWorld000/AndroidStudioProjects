package com.example.appnhatro.View;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Controller.DangKyController;
import com.example.appnhatro.Model.ThanhVienModel;
import com.example.appnhatro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText edtEmailRegister, edtPasswordRegister, edtPasswordConfirm;
    Button btnRegister;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    DangKyController dangKyController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        edtEmailRegister = findViewById(R.id.edtEmailRegister);
        edtPasswordRegister = findViewById(R.id.edtPasswordRegister);
        edtPasswordConfirm = findViewById(R.id.edtPasswordConfirm);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        progressDialog.setMessage(getString(R.string.DangXuly));
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        final String email = edtEmailRegister.getText().toString();
        String matkhau = edtPasswordRegister.getText().toString();
        String nhaplaimatkhau = edtPasswordConfirm.getText().toString();
        String thongbaoloi = getString(R.string.thongbaoloi);
        if (email.trim().isEmpty()) {
            thongbaoloi += " email";
            Toast.makeText(this, thongbaoloi, Toast.LENGTH_LONG).show();
        } else if (matkhau.trim().isEmpty()) {
            thongbaoloi += " mat khau";
            Toast.makeText(this, thongbaoloi, Toast.LENGTH_LONG).show();
        } else if (nhaplaimatkhau.trim().isEmpty()) {
            thongbaoloi += " nhap xac nhan mat khau";
            Toast.makeText(this, thongbaoloi, Toast.LENGTH_LONG).show();
        } else if (!nhaplaimatkhau.equals(matkhau)) {
            thongbaoloi = "Mat khau xac nhan khong dung";
            Toast.makeText(this, thongbaoloi, Toast.LENGTH_LONG).show();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        ThanhVienModel thanhVienModel = new ThanhVienModel();
                        thanhVienModel.setHoTen(email);
                        thanhVienModel.setHinhAnh("users.png");
                        dangKyController = new DangKyController();
                        dangKyController.ThemThanhVienController(thanhVienModel, task.getResult().getUser().getUid());
                        Toast.makeText(Register.this, getString(R.string.thanhcong), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
