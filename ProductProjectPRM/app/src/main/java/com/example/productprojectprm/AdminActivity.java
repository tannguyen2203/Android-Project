package com.example.productprojectprm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productprojectprm.api.ApiService;
import com.example.productprojectprm.models.Order;
import com.example.productprojectprm.models.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity implements OnOrderClickListener{

    private RecyclerView rcvOrder;
    private List<Order> mListOrder = new ArrayList<>();
    private Button quanlyuserBtn, productManagementAdminBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        quanlyuserBtn = findViewById(R.id.quanlyuserbtn);
        quanlyuserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, UserManagementActivity.class );
                startActivity(intent);
            }
        });

        productManagementAdminBtn = findViewById(R.id.productManagementAdmin);
        productManagementAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ProductManagementActivity.class );
                startActivity(intent);

            }
        });

         TextView logoutBtn = findViewById(R.id.logoutAdminBtn);
         logoutBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 logout();

             }
         });

        rcvOrder = findViewById(R.id.orders_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvOrder.setLayoutManager(gridLayoutManager);

        callApiGetOrders();
        search();

    }

    private void callApiGetOrders(){
       ApiService.apiServce.getOrders().enqueue(new Callback<List<Order>>() {
           @Override
           public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
               mListOrder = response.body();
               OrderAdapter orderAdapter = new OrderAdapter(mListOrder, AdminActivity.this);
               rcvOrder.setAdapter(orderAdapter);

               rcvOrder.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rcvOrder, new RecyclerTouchListener.ClickListener() {
                   @Override
                   public void onClick(View view, int position) {
                       Order order = mListOrder.get(position);
                       Intent intent = new Intent(AdminActivity.this, OrderDetailActivity.class);
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
           }

           @Override
           public void onFailure(Call<List<Order>> call, Throwable t) {
               Toast.makeText(AdminActivity.this, "Get order failed", Toast.LENGTH_SHORT).show();
           }
       });
    }

    private void search(){
        EditText searchText = findViewById(R.id.searchTextAdminOrder);
        Button searchButton = findViewById(R.id.searchOrderBtn);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int orderId = Integer.parseInt(searchText.getText().toString());
                Call<List<Order>> call = ApiService.apiServce.searchOrder(orderId);

                call.enqueue(new Callback<List<Order>>() {
                    @Override
                    public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                        mListOrder = response.body();
                        OrderAdapter orderAdapter = new OrderAdapter(mListOrder, AdminActivity.this);
                        rcvOrder.setAdapter(orderAdapter);
                        // Xử lý danh sách sản phẩm trả về ở đây
                    }
                    @Override
                    public void onFailure(Call<List<Order>> call, Throwable t) {
                        Toast.makeText(AdminActivity.this, "lỗi search", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public void onOrderClick(Order order) {

    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.apply();

        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
        startActivity(intent);

        finish();
    }
}


