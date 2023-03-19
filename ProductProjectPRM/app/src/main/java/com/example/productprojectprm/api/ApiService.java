package com.example.productprojectprm.api;

import com.example.productprojectprm.models.Order;
import com.example.productprojectprm.models.Product;
import com.example.productprojectprm.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    ApiService apiServce = new Retrofit.Builder()
            .baseUrl("https://640ae97481d8a32198d3dba4.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("products")
    Call<List<Product>> getProductList();
    @GET("users")
    Call<List<User>> getUsers();
    @GET("products")
    Call<List<Product>> searchProducts(@Query("product_name") String productName);
    @GET("users")
    Call<List<User>> searchUser(@Query("user_name") String userName);
    @POST("users")
    Call<User> addUser(@Body User user);
    @PUT("users/{id}")
    Call<User> updateUserPassword(@Path("id") int userId, @Body User user);
    @GET("orders")
    Call<List<Order>> getOrders();
    @GET("orders")
    Call<List<Order>> searchOrder(@Query("id") int orderId);
    @GET("orders")
    Call<List<Order>> getOrdersByUserId(@Query("user_id") int userId);
}
