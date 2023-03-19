package com.example.productprojectprm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.productprojectprm.api.ApiService;
import com.example.productprojectprm.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountActivity extends AppCompatActivity {
    private Button backLoginBtn;
    private EditText etAccount,etName,etPassword, etPhone, etAddress;

    private Button btnAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        getSupportActionBar().setTitle("Trang Đăng Ký");
        backLoginBtn = findViewById(R.id.dangkybackBtn);
        backLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        etAccount = findViewById(R.id.usernamedangky);
        etName = findViewById(R.id.diachi2);
        etPassword = findViewById(R.id.matkhaudangky);
        etPhone = findViewById(R.id.username3);
        etAddress = findViewById(R.id.diachi);
        btnAddUser = findViewById(R.id.dangkyBtn);
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();

            }
        });

    }

    private void addUser(){
        // Lấy giá trị từ các trường EditText
        String account = etAccount.getText().toString();
        String name = etName.getText().toString();
        String password = etPassword.getText().toString();
        String phone = etPhone.getText().toString();
        String address = etAddress.getText().toString();

        // Kiểm tra các trường không được để trống
        if (account.trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên tài khoản người dùng", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu người dùng", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone.trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }

        if (name.trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên người dùng", Toast.LENGTH_SHORT).show();
            return;
        }

        if (address.trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng User mới với các thông tin vừa lấy được
        User user = new User(account, password,phone, name,address,"user");

        // Gọi API để thêm user mới
        Call<User> call = ApiService.apiServce.addUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Nếu thành công, trở về trang trước đó và thông báo thêm user thành công
                    Toast.makeText(CreateAccountActivity.this, "Thêm người dùng thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // Nếu thất bại, thông báo lỗi
                    Toast.makeText(CreateAccountActivity.this, "Lỗi thêm người dùng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(CreateAccountActivity.this, "Lỗi thêm người dùng", Toast.LENGTH_SHORT).show();
            }
        });
    }
}