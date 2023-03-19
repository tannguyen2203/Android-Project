package com.example.productprojectprm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

public class CartActivity extends AppCompatActivity {
    private Button cartBackBtn, cartThanhToanBtn,cartTiepTucMuaBtn;
    ListView listCart;
    TextView cartThongBao, cartPrice;
    Button btnThanhToan, btnTiepTucMua;
    Toolbar toolGioHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartBackBtn = findViewById(R.id.btnCartBack);
        cartBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa(){
        /*listCart =*/
    }
}