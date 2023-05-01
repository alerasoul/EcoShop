package com.satpay.ecoshop.api;

import com.satpay.ecoshop.model.AddProduct;
import com.satpay.ecoshop.model.Login;
import com.satpay.ecoshop.model.LoginResponse;
import com.satpay.ecoshop.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("products")
    Call<List<Product>> getProducts();

    @GET("products/{productId}")
    Call<Product> getProductById(
            @Path("productId") int productId
    );

    @GET("products/categories")
    Call<List<String>> getCategories();

    @GET("products/category/{category}")
    Call<List<String>> getProductsByCategory(
            @Path("category") String category
    );

    @POST("auth/login")
    Call<LoginResponse> login(
            @Body Login login
    );

    @POST("products")
    Call<List<String>> addProduct(
            @Body AddProduct product
    );

    @GET("products")
    Call<List<Product>> sortProducts(
            @Query("sort") String sort
    );

}
