package com.example.productprojectprm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productprojectprm.api.ApiService;
import com.example.productprojectprm.models.Order;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryActivity extends AppCompatActivity implements OnOrderClickListener {
    private ImageView homePage;
    private RecyclerView rcvOrder;
    private List<Order> mListOrder = new ArrayList<>();
    private Order order;
    private TextView userNameText, userPhoneText, userAddressText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        userNameText = findViewById(R.id.userOrderName);
        userPhoneText = findViewById(R.id.userOrderPhone);
        userAddressText = findViewById(R.id.userOrderAddress);


        homePage = (ImageView) findViewById(R.id.homepageOrder);
        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderHistoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        rcvOrder = findViewById(R.id.rvc_orderHistory);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvOrder.setLayoutManager(gridLayoutManager);

        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userid", -1);
        String username = sharedPreferences.getString("username", null);
        String userphone = sharedPreferences.getString("userphone", null);
        String useraddress = sharedPreferences.getString("useraddress", null);

        userNameText.setText(username);
        userPhoneText.setText(userphone);
        userAddressText.setText(useraddress);
        callApiGetOrdersByUserId(userId);

    }

    private void callApiGetOrdersByUserId(int userID) {
        ApiService.apiServce.getOrdersByUserId(userID).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {

                if (response.isSuccessful()) {
                    // Hiển thị danh sách đơn hàng lên RecyclerView
                    mListOrder = response.body();
                    OrderAdapter orderAdapter = new OrderAdapter(mListOrder, OrderHistoryActivity.this);
                    rcvOrder.setAdapter(orderAdapter);

                    rcvOrder.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rcvOrder, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Order order = mListOrder.get(position);
                            Intent intent = new Intent(OrderHistoryActivity.this, OrderHistoryDetailActivity.class);
                            intent.putExtra("ORDER_NAME", order.getProductName());
                            intent.putExtra("ORDER_NAME2", order.getProductName2());
                            intent.putExtra("PRODUCT_IMAGE", order.getProductImage());
                            intent.putExtra("PRODUCT_IMAGE2", order.getProductImage2());
                            intent.putExtra("PRODUCT_QUANTITY", order.getQuantity());
                            intent.putExtra("PRODUCT_QUANTITY2", order.getQuantity2());
                            intent.putExtra("PRODUCT_PRICE", order.getPrice());
                            intent.putExtra("PRODUCT_PRICE2", order.getPrice2());
                            intent.putExtra("ORDER_TOTAL", order.getTotalPrice());
                            intent.putExtra("ORDER_STATUS", order.getOrderStatus());
                            intent.putExtra("ORDER_ID", order.getOrderId());
                            intent.putExtra("USER_ID", order.getUserId());
                            /*intent.putExtra("ORDER_DATE", order.getOrderDate());*/
                            startActivity(intent);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                } else {
                    Toast.makeText(OrderHistoryActivity.this, "Failed to get orders", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onOrderClick(Order order) {

    }
}