package com.example.appnhatro.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.*;

import com.example.appnhatro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgetPassword extends AppCompatActivity implements View.OnClickListener {

    TextView txtDangKyKp;
    Button btnGuiEmail;
    EditText edtEmailKP;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        firebaseAuth = firebaseAuth.getInstance();
        txtDangKyKp = findViewById(R.id.txtRegisterForget);
        btnGuiEmail = findViewById(R.id.btnSendEmail);
        edtEmailKP = findViewById(R.id.edtEmailForget);

        btnGuiEmail.setOnClickListener(this);
        txtDangKyKp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.txtRegisterForget:
                Intent intentDangky = new Intent(ForgetPassword.this,Register.class);
                startActivity(intentDangky);
                break;
            case R.id.btnSendEmail:
                boolean ktemail = KTemail(edtEmailKP.getText().toString());
                if(ktemail == true)
                {
                    firebaseAuth.sendPasswordResetEmail(edtEmailKP.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ForgetPassword.this,getString(R.string.success),Toast.LENGTH_LONG).show();
                                Intent itentdn = new Intent(ForgetPassword.this,LoginActivity.class);
                                startActivity(itentdn);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(ForgetPassword.this,getString(R.string.emailkhonghople),Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private boolean KTemail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
