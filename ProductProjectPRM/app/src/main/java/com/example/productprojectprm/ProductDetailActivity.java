package com.example.productprojectprm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.productprojectprm.models.Cart;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView productImage;
    private TextView productDetailName, productDetailPrice, productDetailMaker, prodcutDescription;
    private Button btnDatMua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        getSupportActionBar().setTitle("Thông tin sản phẩm");
        btnDatMua = findViewById(R.id.btndatmua);

        String image = getIntent().getStringExtra("IMAGE");
        String productName = getIntent().getStringExtra("PRODUCT_NAME");
        String description = getIntent().getStringExtra("PRODUCT_DESCRIPTION");
        int price = getIntent().getIntExtra("PRODUCT_PRICE", 0);
        String maker = getIntent().getStringExtra("MAKER");

        productImage = findViewById(R.id.imageProductDetail);
        Glide.with(this)
                .load(image)
                .into(productImage);
        productDetailName = findViewById(R.id.nameProductDetail);
        prodcutDescription =findViewById(R.id.descriptionProductDetail);
        productDetailPrice = findViewById(R.id.priceProductDetail);
        productDetailMaker = findViewById(R.id.makerProductDetail);

        productDetailName.setText(productName);
        prodcutDescription.setText(description);
        productDetailPrice.setText(String.valueOf(price)+"₫");
        productDetailMaker.setText(maker);

    }

}