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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductManagementActivity extends AppCompatActivity {
    private RecyclerView rcvProduct;
    private List<Product> productList = new ArrayList<>();
    private Button quanlysuserTextBtn, quanlyOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_management);
        rcvProduct = findViewById(R.id.product_management);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvProduct.setLayoutManager(gridLayoutManager);
        productList = new ArrayList<>();
        CallApiGetProductList();
        search();

        quanlysuserTextBtn = findViewById(R.id.quanlyuserBtn);
        quanlyOrderBtn = findViewById(R.id.quanlyorderBtn);
        quanlysuserTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductManagementActivity.this, UserManagementActivity.class);
                startActivity(intent);
            }
        });
        quanlyOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductManagementActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        TextView logoutBtn = findViewById(R.id.productIdLogout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

    }

    private void CallApiGetProductList(){
        ApiService.apiServce.getProductList().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();
                ProductAdapter productAdapter = new ProductAdapter(productList);
                rcvProduct.setAdapter(productAdapter);
                /*Toast.makeText(ProductManagementActivity.this, "Ket noi api thanh cong", Toast.LENGTH_LONG).show();*/

                rcvProduct.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rcvProduct, new RecyclerTouchListener.ClickListener(){
                    @Override
                    public void onClick(View view, int position) {
                        Product product = productList.get(position);

                        Intent intent = new Intent(ProductManagementActivity.this, ProductManagementDetailActivity.class);
                        intent.putExtra("PRODUCT_NAME", product.getProductName());
                        intent.putExtra("IMAGE", product.getImgId());
                        intent.putExtra("PRODUCT_PRICE", product.getPrice());
                        intent.putExtra("PRODUCT_DESCRIPTION", product.getDescription());
                        intent.putExtra("MAKER", product.getMaker());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                    }
                }));
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(ProductManagementActivity.this, "onFailure", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void search(){
        EditText searchText = findViewById(R.id.searchTextProduct);
        Button searchButton = findViewById(R.id.searchProductBtn);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = searchText.getText().toString();
                Call<List<Product>> call = ApiService.apiServce.searchProducts(productName);

                call.enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        productList = response.body();
                        if(productList.isEmpty()){
                            Toast.makeText(ProductManagementActivity.this, "Không tìm thấy sản phẩm", Toast.LENGTH_LONG).show();
                        }else {
                            ProductAdapter productAdapter = new ProductAdapter(productList);
                            rcvProduct.setAdapter(productAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Toast.makeText(ProductManagementActivity.this, "lỗi search", Toast.LENGTH_LONG).show();

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

        Intent intent = new Intent(ProductManagementActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}