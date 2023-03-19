package com.example.productprojectprm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderDetailActivity extends AppCompatActivity  {


    private ImageView productImageText, productImage2Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Anhxa();

        int orderId = getIntent().getIntExtra("ORDER_ID", 0 );
        String productName = getIntent().getStringExtra("ORDER_NAME");
        String productName2 = getIntent().getStringExtra("ORDER_NAME2");
        String productImage = getIntent().getStringExtra("PRODUCT_IMAGE");
        String productImage2 = getIntent().getStringExtra("PRODUCT_IMAGE2");
        int productQuantity = getIntent().getIntExtra("PRODUCT_QUANTITY", 0);
        int productQuantity2 = getIntent().getIntExtra("PRODUCT_QUANTITY2", 0);
        int userId = getIntent().getIntExtra("USER_ID", 0);
        int productPrice = getIntent().getIntExtra("PRODUCT_PRICE", 0);
        int productPrice2 = getIntent().getIntExtra("PRODUCT_PRICE2", 0);
        int orderTotal = getIntent().getIntExtra("ORDER_TOTAL", 0);
        String orderStatus = getIntent().getStringExtra("ORDER_STATUS");
        /*String orderDate = getIntent().getStringExtra("ORDER_DATE");*/

        TextView orderIdText = findViewById(R.id.orderId);
        TextView productNameText = findViewById(R.id.productName1);
        TextView productNameText2 = findViewById(R.id.productName2);
        TextView productQuantityText = findViewById(R.id.productQuantity1);
        TextView productQuantityText2 = findViewById(R.id.productQuantity2);
        TextView userIdText = findViewById(R.id.userId);
        TextView orderStatusText = findViewById(R.id.orderStatus);
        TextView orderDateText = findViewById(R.id.orderDate);
        TextView productPriceText = findViewById(R.id.productPrice1);
        TextView productPriceText2 = findViewById(R.id.productPrice2);
        TextView total = findViewById(R.id.totalPrice);

        productImageText = findViewById(R.id.productImage1);
        Glide.with(this)
                .load(productImage)
                .into(productImageText);
        productImage2Text = findViewById(R.id.productImage2);
        Glide.with(this)
                .load(productImage2)
                .into(productImage2Text);

        productNameText.setText(productName);
        productNameText2.setText(productName2);
        productQuantityText.setText("Số lượng: "+String.valueOf(productQuantity));
        productQuantityText2.setText("Số lượng: "+ String.valueOf(productQuantity2));
        orderIdText.setText(String.valueOf("ID đơn hàng: "+orderId));
        userIdText.setText(String.valueOf("User ID: " + userId));

        orderStatusText.setText("Tình trạng đơn hàng: "+orderStatus);
       /* orderDateText.setText(dateString);*/
        productPriceText.setText("Giá tiền: "+String.valueOf(productPrice) + "đ");
        productPriceText2.setText("Giá tiền: "+String.valueOf(productPrice2) + "đ");
        total.setText(String.valueOf(orderTotal) +"đ");

    }

    private void Anhxa(){
        Button btnback = findViewById(R.id.btnAdmin);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
    }

}