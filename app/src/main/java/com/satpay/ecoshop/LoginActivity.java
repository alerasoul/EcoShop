package com.satpay.ecoshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.satpay.ecoshop.api.RetrofitClient;
import com.satpay.ecoshop.model.Login;
import com.satpay.ecoshop.model.LoginResponse;
import com.satpay.ecoshop.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtUsername.getText().toString().equals("") || edtPassword.getText().toString().equals(""))
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.empty_login), Toast.LENGTH_LONG).show();
                else {
                    loginReq();
                }
            }
        });
    }

    private void initViews() {
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
    }

    public void loginReq() {
        Call<LoginResponse> callLogin = RetrofitClient.getInstance().getMyApi().login(
                new Login(
                        edtUsername.getText().toString(),
                        edtPassword.getText().toString())
        );
        callLogin.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        Utils.token = response.body().getToken();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                } else if (response.code() == 401)
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_fail), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.net_error), Toast.LENGTH_LONG).show();
                Log.e("LoginActivity", t.toString());
            }
        });
    }
}