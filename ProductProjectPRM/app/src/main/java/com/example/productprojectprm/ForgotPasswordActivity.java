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

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText accountEditText, passwordEditText;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        accountEditText = findViewById(R.id.nameReset);
        passwordEditText = findViewById(R.id.passwordReset);
        updateButton = findViewById(R.id.resetBtn);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(accountEditText.getText().toString());
                String password = passwordEditText.getText().toString();
                updateUserPassword(id, password);
            }
        });
    }

    private void updateUserPassword(int userId, String newPassword) {
        User updatedUser = new User();
        updatedUser.setUserPassword(newPassword);

        ApiService.apiServce.updateUserPassword(userId, updatedUser)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "Password updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(ForgotPasswordActivity.this, "Ket noi khong thanh cong", Toast.LENGTH_SHORT).show();
                    }
                });
}
}