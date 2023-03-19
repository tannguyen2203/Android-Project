package com.example.productprojectprm;

import static com.example.productprojectprm.api.ApiService.apiServce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productprojectprm.api.ApiService;
import com.example.productprojectprm.models.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private TextView createAccount, forgotPassword;
    private EditText edtUserAccount, edtUserPassword;
    private String loggedInUserName, loggedInRole, loggedInUserPhone,loggedInUserAddress;
    private int logginInUserId;
    private SharedPreferences.Editor editor;
    private ImageView adminPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Trang Đăng nhập");

        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        edtUserAccount = findViewById(R.id.username);
        edtUserPassword = findViewById(R.id.password);


        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUserAccount.getText().toString().trim();
                String password = edtUserPassword.getText().toString().trim();
                login();
            }
        });

        createAccount = findViewById(R.id.create_account);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        forgotPassword = findViewById(R.id.forgot_password);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    private void login() {
        final String[] username = {edtUserAccount.getText().toString()};
        String password = edtUserPassword.getText().toString();

        if (username[0].isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter user and password", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(username[0], password);

        Call<List<User>> call = apiServce.getUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    /*Toast.makeText(LoginActivity.this, "Ket noi api thanh cong", Toast.LENGTH_LONG).show();*/

                    List<User> userList = response.body();
                    boolean isLoggedIn = false;
                    boolean isAdmin = false;
                    for (User u : userList) {
                        if (u.getUserAccount().equals(user.getUserAccount()) && u.getUserPassword().equals(user.getUserPassword())) {
                            isLoggedIn = true;
                            loggedInUserName = u.getUserName();
                            loggedInRole = u.getUserRole();
                            logginInUserId = u.getUserId();
                            loggedInUserPhone = u.getPhone();
                            loggedInUserAddress = u.getAddress();
                            if (u.getUserRole().equals("admin")) {
                                isAdmin = true;
                            }
                            break;
                        }
                    }
                    if (isLoggedIn) {
                        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", loggedInUserName);
                        editor.putString("userrole", loggedInRole);
                        editor.putInt("userid", logginInUserId);
                        editor.putString("useraddress", loggedInUserAddress);
                        editor.putString("userphone", loggedInUserPhone);
                        editor.apply();
                        if(isAdmin){
                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid account or password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}