package com.satpay.ecoshop;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.satpay.ecoshop.api.RetrofitClient;
import com.satpay.ecoshop.model.AddProduct;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {

    Button btnAdd, btnCancel;
    EditText edtTitle, edtDesc, edtCat, edtImage, edtPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initViews();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTitle.getText().toString().isEmpty() ||
                        edtDesc.getText().toString().isEmpty() ||
                        edtCat.getText().toString().isEmpty() ||
                        edtImage.getText().toString().isEmpty() ||
                        edtPrice.getText().toString().isEmpty()) {
                    Toast.makeText(AddProductActivity.this, getResources().getString(R.string.fill_fields), Toast.LENGTH_LONG).show();
                } else {
                    addProductReq();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void addProductReq() {
        AddProduct p = new AddProduct(
                Float.parseFloat(edtPrice.getText().toString()),
                edtTitle.getText().toString(),
                edtDesc.getText().toString(),
                edtImage.getText().toString(),
                edtCat.getText().toString());
        Call<List<String>> call = RetrofitClient.getInstance().getMyApi().addProduct(p);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.code() == 200)
                    Toast.makeText(AddProductActivity.this, getResources().getString(R.string.add_product_success), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(AddProductActivity.this, getResources().getString(R.string.add_product_error), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(AddProductActivity.this, getResources().getString(R.string.net_error), Toast.LENGTH_LONG).show();
                Log.e("AddProductActivity", t.toString());
            }
        });
    }

    private void initViews() {
        btnAdd = findViewById(R.id.btn_add);
        btnCancel = findViewById(R.id.btn_cancel);
        edtTitle = findViewById(R.id.edt_title);
        edtDesc = findViewById(R.id.edt_desc);
        edtCat = findViewById(R.id.edt_category);
        edtImage = findViewById(R.id.edt_img_url);
        edtPrice = findViewById(R.id.edt_price);
    }
}