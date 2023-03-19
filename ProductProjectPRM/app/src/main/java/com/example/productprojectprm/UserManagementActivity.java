package com.example.productprojectprm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productprojectprm.api.ApiService;
import com.example.productprojectprm.models.Product;
import com.example.productprojectprm.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserManagementActivity extends AppCompatActivity {

    private RecyclerView rcvUser;
    private List<User> mListUser = new ArrayList<>();
    private Button quanlyorderBtn, quanlyProductBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        rcvUser = findViewById(R.id.user_management);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvUser.setLayoutManager(gridLayoutManager);
        CallApiGetUser();
        search();

        quanlyorderBtn = findViewById(R.id.quanlyorderBtn);
        quanlyorderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManagementActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        quanlyProductBtn = findViewById(R.id.produManagement);
        quanlyProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManagementActivity.this, ProductManagementActivity.class);
                startActivity(intent);
            }
        });

        TextView logoutUserBtn = findViewById(R.id.logoutBtnUser);
        logoutUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


    }

    private void CallApiGetUser(){
        ApiService.apiServce.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                mListUser = response.body();
                UserAdapter userAdapter = new UserAdapter(mListUser);
                rcvUser.setAdapter(userAdapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(UserManagementActivity.this, "Get user failed", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void search(){
        EditText searchText = findViewById(R.id.searchTextUser);
        Button searchButton = findViewById(R.id.searchUserBtn);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = searchText.getText().toString();
                Call<List<User>> call = ApiService.apiServce.searchUser(userName);
                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        mListUser = response.body();
                        UserAdapter userAdapter = new UserAdapter(mListUser, UserManagementActivity.this);
                        rcvUser.setAdapter(userAdapter);
                    }
                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Toast.makeText(UserManagementActivity.this, "lỗi search ", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }

    private void logout(){
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.apply();

        Intent intent = new Intent(UserManagementActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}