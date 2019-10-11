package com.example.appnhatro.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.List;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, FirebaseAuth.AuthStateListener {

    public static int CODE_LOGIN_GOOGLE = 100;
    public static int CHECK_AUTH_PROVIDER_SIGN_IN = 1;
    Button btnDangNhapGoogle, btnLogin;
    GoogleApiClient apiClient;
    FirebaseAuth firebaseAuth;
    LoginManager loginManager;
    CallbackManager mCallBackFacebook;
    List<String> permissionFacebook = Arrays.asList("email", "public_profile");
    Button btnLoginFacebook;
    TextView txtDangKy, txtQuenMK;
    EditText edtEmail, edtPassword;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        mCallBackFacebook = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        btnDangNhapGoogle = findViewById(R.id.btnSignInGoogle);
        btnLoginFacebook = findViewById(R.id.btnLoginFacebook);
        txtDangKy = findViewById(R.id.txtDangKy);
        txtQuenMK = findViewById(R.id.txtQuenMk);
        btnLogin = findViewById(R.id.btnLogin);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        //Buoc 1
        btnLogin.setOnClickListener(this);
        btnDangNhapGoogle.setOnClickListener(this);
        btnLoginFacebook.setOnClickListener(this);
        txtQuenMK.setOnClickListener(this);
        txtDangKy.setOnClickListener(this);


        CreateClientLoginGoogle();

        sharedPreferences = getSharedPreferences("luuDangNhap", MODE_PRIVATE);
    }

    private void LoginFacebook() {
        loginManager.logInWithReadPermissions(this, permissionFacebook);
        loginManager.registerCallback(mCallBackFacebook, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                CHECK_AUTH_PROVIDER_SIGN_IN = 2;
                String tokenID = loginResult.getAccessToken().getToken();
                ChungThucDangNhapFireBase(tokenID);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void CreateClientLoginGoogle() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();
    }

    //Buoc 3
    private void LoginGoogle(GoogleApiClient apiClient) {
        Intent intentGoogle = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(intentGoogle, CODE_LOGIN_GOOGLE);
    }

    //Buoc 5
    private void ChungThucDangNhapFireBase(String tokenID) {

        switch (CHECK_AUTH_PROVIDER_SIGN_IN) {
            case 1:
                AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenID, null);//mo chung thuc google
                firebaseAuth.signInWithCredential(authCredential);
                break;
            case 2:
                AuthCredential authCredential1 = FacebookAuthProvider.getCredential(tokenID);
                firebaseAuth.signInWithCredential(authCredential1);
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    //Buoc 4
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_LOGIN_GOOGLE) {
            if (resultCode == RESULT_OK) {
                GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount account = signInResult.getSignInAccount();//laasy account
                String tokenID = account.getIdToken();//lay token tu account
                ChungThucDangNhapFireBase(tokenID);//chung thuc cai token dang nhap
            }
        } else {
            if (resultCode == RESULT_OK) {
                mCallBackFacebook.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //Buoc 2
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnSignInGoogle:
                LoginGoogle(apiClient);
                break;
            case R.id.btnLoginFacebook:
                LoginFacebook();
                break;
            case R.id.txtDangKy:
                Intent intentDK = new Intent(LoginActivity.this, Register.class);
                startActivity(intentDK);
                break;
            case R.id.btnLogin:
                progressDialog.setMessage("Dang kiem tra");
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                DangNhap();
                break;
            case R.id.txtQuenMk:
                Intent intentkhoiphucmk = new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(intentkhoiphucmk);
        }
    }

    private void DangNhap() {
        String email = edtEmail.getText().toString();
        String matkhau = edtPassword.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email, matkhau).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Dang nhap that bai", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            user.getUid();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("mauser", user.getUid());
            editor.commit();
            Intent Home = new Intent(LoginActivity.this, com.example.appnhatro.View.Home.class);
            startActivity(Home);
        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();
        }
    }
}
