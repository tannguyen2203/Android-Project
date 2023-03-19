package com.example.productprojectprm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.productprojectprm.api.ApiService;
import com.example.productprojectprm.models.Cart;
import com.example.productprojectprm.models.Product;
import com.example.productprojectprm.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvProduct;
    private ViewFlipper viewFlipper;
    private ImageView profileInfo, cart, homePage, adminOrder;
    private List<Product> productList;
    private TextView tulanhBtn, maylanhBtn, dientuBtn, maygiatBtn, giadungBtn, tvBtn, allBtn, tvLogin, tvLogout, sort;
    public static ArrayList<Cart> manggiohang;
    private SearchView searchView;
    private ProductAdapter productAdapter;
    private List<Product> filteredProductList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Shopping");
        rcvProduct = findViewById(R.id.rcv_product);
        tvLogin = findViewById(R.id.tv_login);
        tvLogout = findViewById(R.id.tv_logout);

        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        int userId = sharedPreferences.getInt("userid", 0);

        if (username != null) {
            // Nếu có, hiển thị tên người dùng trên giao diện
            tvLogin.setText(username);

            // Hiển thị nút Logout
            tvLogout.setVisibility(View.VISIBLE);
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvProduct.setLayoutManager(gridLayoutManager);
        productList = new ArrayList<>();
        CallApiGetProductList();
        /*CallApiGetProduct();*/
        search();
        Ads();
        Anhxa();
        ItemClickEvent();
    }

    private void Anhxa(){
        tulanhBtn = findViewById(R.id.textView3);
        maylanhBtn = findViewById(R.id.textView4);
        dientuBtn = findViewById(R.id.textView6);
        maygiatBtn = findViewById(R.id.textView5);
        giadungBtn = findViewById(R.id.textView7);
        tvBtn = findViewById(R.id.textView8);
        allBtn = findViewById(R.id.textView2);
        sort = findViewById(R.id.textView10);
        if(manggiohang != null){

        }else{
            manggiohang = new ArrayList<>();
        }
    }

    private void CallApiGetProductList(){
        ApiService.apiServce.getProductList().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();
                filteredProductList.clear();
                for (Product product : productList) {
                    if (product.getModelYear() == 2023) {
                        filteredProductList.add(product);
                    }
                }

                Collections.sort(filteredProductList, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        return p1.getProductName().compareToIgnoreCase(p2.getProductName());
                    }
                });

                onClickCategory();
                ProductAdapter productAdapter = new ProductAdapter(filteredProductList);
                rcvProduct.setAdapter(productAdapter);
              /*  Toast.makeText(MainActivity.this, "Ket noi api thanh cong", Toast.LENGTH_LONG).show();*/

                rcvProduct.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rcvProduct, new RecyclerTouchListener.ClickListener(){
                    @Override
                    public void onClick(View view, int position) {
                        Product product = filteredProductList.get(position);

                        Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
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
                Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void ItemClickEvent(){

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Cart
        cart = (ImageView) findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        profileInfo = (ImageView) findViewById(R.id.profileLoginDetail);
        profileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            }
        });

        homePage = (ImageView) findViewById(R.id.homePage);
        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

    }

    private void Ads(){
       int images[] = {R.drawable.ads_0,R.drawable.ads_1,R.drawable.ads_2,R.drawable.ads_3};
       viewFlipper = findViewById(R.id.viewFlipper);
        for (int image: images) {
            flipperImages(image);
        }
    }

    public void flipperImages(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        //animation
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }


    private void onClickCategory(){
        tulanhBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filteredProductList.clear();
                for (Product product : productList) {
                    if (product.getCategory() == 1) {
                        filteredProductList.add(product);
                    }
                }

                Collections.sort(filteredProductList, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        return p1.getProductName().compareToIgnoreCase(p2.getProductName());
                    }
                });

                ProductAdapter productAdapter = new ProductAdapter(filteredProductList);
                rcvProduct.setAdapter(productAdapter);

                rcvProduct.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rcvProduct, new RecyclerTouchListener.ClickListener(){
                    @Override
                    public void onClick(View view, int position) {
                        Product product = filteredProductList.get(position);

                        Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
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
        });

        maylanhBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filteredProductList.clear();
                for (Product product : productList) {
                    if (product.getCategory() == 4) {
                        filteredProductList.add(product);
                    }
                }

                Collections.sort(filteredProductList, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        return p1.getProductName().compareToIgnoreCase(p2.getProductName());
                    }
                });

                ProductAdapter productAdapter = new ProductAdapter(filteredProductList);
                rcvProduct.setAdapter(productAdapter);

                rcvProduct.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rcvProduct, new RecyclerTouchListener.ClickListener(){
                    @Override
                    public void onClick(View view, int position) {
                        Product product = filteredProductList.get(position);

                        Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
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
        });

        dientuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filteredProductList.clear();
                for (Product product : productList) {
                    if (product.getCategory() == 6) {
                        filteredProductList.add(product);
                    }
                }

                Collections.sort(filteredProductList, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        return p1.getProductName().compareToIgnoreCase(p2.getProductName());
                    }
                });

                ProductAdapter productAdapter = new ProductAdapter(filteredProductList);
                rcvProduct.setAdapter(productAdapter);

                rcvProduct.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rcvProduct, new RecyclerTouchListener.ClickListener(){
                    @Override
                    public void onClick(View view, int position) {
                        // Lấy thông tin sản phẩm từ danh sách sản phẩm đã lọc
                        Product product = filteredProductList.get(position);

                        Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
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
        });

        maygiatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filteredProductList.clear();
                for (Product product : productList) {
                    if (product.getCategory() == 2) {
                        filteredProductList.add(product);
                    }
                }

                Collections.sort(filteredProductList, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        return p1.getProductName().compareToIgnoreCase(p2.getProductName());
                    }
                });

                ProductAdapter productAdapter = new ProductAdapter(filteredProductList);
                rcvProduct.setAdapter(productAdapter);

                rcvProduct.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rcvProduct, new RecyclerTouchListener.ClickListener(){
                    @Override
                    public void onClick(View view, int position) {
                        Product product = filteredProductList.get(position);

                        Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
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
        });

        giadungBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filteredProductList.clear();
                for (Product product : productList) {
                    if (product.getCategory() == 5) {
                        filteredProductList.add(product);
                    }
                }

                Collections.sort(filteredProductList, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        return p1.getProductName().compareToIgnoreCase(p2.getProductName());
                    }
                });

                ProductAdapter productAdapter = new ProductAdapter(filteredProductList);
                rcvProduct.setAdapter(productAdapter);

                rcvProduct.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rcvProduct, new RecyclerTouchListener.ClickListener(){
                    @Override
                    public void onClick(View view, int position) {
                        Product product = filteredProductList.get(position);

                        Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
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
        });

        tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filteredProductList.clear();
                for (Product product : productList) {
                    if (product.getCategory() == 3) {
                        filteredProductList.add(product);
                    }
                }

                Collections.sort(filteredProductList, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        return p1.getProductName().compareToIgnoreCase(p2.getProductName());
                    }
                });

                ProductAdapter productAdapter = new ProductAdapter(filteredProductList);
                rcvProduct.setAdapter(productAdapter);

                rcvProduct.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rcvProduct, new RecyclerTouchListener.ClickListener(){
                    @Override
                    public void onClick(View view, int position) {
                        Product product = filteredProductList.get(position);

                        Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
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
        });

        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filteredProductList.clear();
                for (Product product : productList) {
                    filteredProductList.add(product);
                }

                // Sắp xếp danh sách sản phẩm theo tên sản phẩm
                Collections.sort(filteredProductList, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        return p1.getProductName().compareToIgnoreCase(p2.getProductName());
                    }
                });

                // Hiển thị danh sách sản phẩm đã lọc trên RecyclerView
                ProductAdapter productAdapter = new ProductAdapter(filteredProductList);
                rcvProduct.setAdapter(productAdapter);

                // Xử lý sự kiện click item trên RecyclerView
                rcvProduct.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rcvProduct, new RecyclerTouchListener.ClickListener(){
                    @Override
                    public void onClick(View view, int position) {
                        // Lấy thông tin sản phẩm từ danh sách sản phẩm đã lọc
                        Product product = filteredProductList.get(position);

                        Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                        intent.putExtra("PRODUCT_NAME", product.getProductName());
                        intent.putExtra("IMAGE", product.getImgId());
                        intent.putExtra("PRODUCT_PRICE", product.getPrice());
                        intent.putExtra("PRODUCT_DESCRIPTION", product.getDescription());
                        intent.putExtra("MAKER", product.getMaker());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        // Không xử lý sự kiện long click
                    }
                }));
            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filteredProductList.clear();
                for (Product product : productList) {
                    filteredProductList.add(product);
                }

                // Sắp xếp danh sách sản phẩm theo tên sản phẩm
                Collections.sort(filteredProductList, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        return Double.compare(p2.getPrice(), p1.getPrice());
                    }
                });

                // Hiển thị danh sách sản phẩm đã lọc trên RecyclerView
                ProductAdapter productAdapter = new ProductAdapter(filteredProductList);
                rcvProduct.setAdapter(productAdapter);

                // Xử lý sự kiện click item trên RecyclerView
                rcvProduct.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rcvProduct, new RecyclerTouchListener.ClickListener(){
                    @Override
                    public void onClick(View view, int position) {
                        // Lấy thông tin sản phẩm từ danh sách sản phẩm đã lọc
                        Product product = filteredProductList.get(position);
                        Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                        intent.putExtra("PRODUCT_NAME", product.getProductName());
                        intent.putExtra("IMAGE", product.getImgId());
                        intent.putExtra("PRODUCT_PRICE", product.getPrice());
                        intent.putExtra("PRODUCT_DESCRIPTION", product.getDescription());
                        intent.putExtra("MAKER", product.getMaker());
                        startActivity(intent);
                    }
                    @Override
                    public void onLongClick(View view, int position) {
                        // Không xử lý sự kiện long click
                    }
                }));
            }
        });
    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.apply();

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
        // Cập nhật giao diện
        tvLogin.setText("Login");
        tvLogout.setVisibility(View.GONE);
        finish();
    }
    private void search(){
        EditText searchText = findViewById(R.id.searchText);
        Button searchButton = findViewById(R.id.searchButton);

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
                            Toast.makeText(MainActivity.this, "Không tìm thấy sản phẩm", Toast.LENGTH_LONG).show();
                        }else {
                            ProductAdapter productAdapter = new ProductAdapter(productList);
                            rcvProduct.setAdapter(productAdapter);
                        }

                        // Xử lý danh sách sản phẩm trả về ở đây
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "lỗi search", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }

}