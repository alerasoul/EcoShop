package com.satpay.ecoshop.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.satpay.ecoshop.R;
import com.satpay.ecoshop.ShoppingActivity;
import com.satpay.ecoshop.adapter.ProductAdapter;
import com.satpay.ecoshop.api.RetrofitClient;
import com.satpay.ecoshop.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RecyclerView recProducts;
    TextView txtAsc, txtDesc;
    ProductAdapter adapter = new ProductAdapter();
    ProductAdapter.OnClickListener onProductClick;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recProducts = view.findViewById(R.id.rec_products);
        txtAsc = view.findViewById(R.id.txt_asc);
        txtDesc = view.findViewById(R.id.txt_desc);

        recProducts.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, true));
        recProducts.setHasFixedSize(true);
        recProducts.setAdapter(adapter);

        onProductClick = p -> {
            final Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialog_product_detail);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

            TextView txtName, txtDesc, txtPrice, txtScore;
            ConstraintLayout constAddCard;
            ImageView imgProduct;
            txtName = dialog.findViewById(R.id.txt_name);
            txtDesc = dialog.findViewById(R.id.txt_desc);
            txtPrice = dialog.findViewById(R.id.txt_price);
            txtScore = dialog.findViewById(R.id.txt_score);
            imgProduct = dialog.findViewById(R.id.img_product);
            constAddCard = dialog.findViewById(R.id.const_add_card);

            txtName.setText(p.getTitle());
            txtDesc.setText(p.getDescription());
            txtPrice.setText(String.format("%s", p.getPrice()));
            txtScore.setText(String.format("%s", p.getRating().getRate()));
            Picasso.get().load(p.getImage()).into(imgProduct);
            constAddCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ShoppingActivity.class);
                    intent.putExtra("product_id", p.getId());
                    startActivity(intent);
                }
            });

            dialog.show();
        };

        productsReq();


        txtAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtDesc.setTextColor(getResources().getColor(R.color.gray));
                setTextViewDrawableColor(txtDesc, R.color.gray);
                txtAsc.setTextColor(getResources().getColor(R.color.primary));
                setTextViewDrawableColor(txtAsc, R.color.primary);
                sortProducts("asc");
            }
        });
        txtDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtAsc.setTextColor(getResources().getColor(R.color.gray));
                setTextViewDrawableColor(txtAsc, R.color.gray);
                txtDesc.setTextColor(getResources().getColor(R.color.primary));
                setTextViewDrawableColor(txtDesc, R.color.primary);
                sortProducts("desc");
            }
        });


        return view;
    }

    public void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(
                        new PorterDuffColorFilter(
                                ContextCompat.getColor(textView.getContext(), color), PorterDuff.Mode.SRC_IN
                        )
                );
            }
        }
    }

    public void productsReq() {
        Call<List<Product>> call = RetrofitClient.getInstance().getMyApi().getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.code() == 200 && response.body() != null)
                    for (Product p : response.body())
                        adapter.addData(p);

                adapter.notifyDataSetChanged();

                adapter.setOnClickListener(onProductClick);

            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.net_error), Toast.LENGTH_LONG).show();
                Log.e("HomeFragment", t.toString());
            }
        });
    }

    public void sortProducts(String sort) {
        adapter.clearData();
        adapter.notifyDataSetChanged();
        Call<List<Product>> call = RetrofitClient.getInstance().getMyApi().sortProducts(sort);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.code() == 200 && response.body() != null)
                    for (Product p : response.body())
                        adapter.addData(p);

                adapter.notifyDataSetChanged();

                adapter.setOnClickListener(onProductClick);

            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.net_error), Toast.LENGTH_LONG).show();
                Log.e("HomeFragment", t.toString());
            }
        });
    }

}