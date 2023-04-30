package com.satpay.ecoshop.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.satpay.ecoshop.R;
import com.satpay.ecoshop.api.RetrofitClient;
import com.satpay.ecoshop.model.Product;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartFragment extends Fragment {

    ImageView imgProduct;
    TextView txtName, txtPrice, txtTotal, txtCount, txtTotalAmountPrice;
    Button btnProceed, btnDec, btnInc, btnCash, btnLoan;
    int count = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        imgProduct = view.findViewById(R.id.img_product);
        txtName = view.findViewById(R.id.txt_pro_name);
        txtPrice = view.findViewById(R.id.txt_pro_price);
        txtTotal = view.findViewById(R.id.txt_pro_total);
        txtCount = view.findViewById(R.id.txt_count);
        txtTotalAmountPrice = view.findViewById(R.id.txt_total_amount_price);
        btnProceed = view.findViewById(R.id.btn_proceed);
        btnDec = view.findViewById(R.id.btn_dec);
        btnInc = view.findViewById(R.id.btn_inc);
        btnCash = view.findViewById(R.id.btn_cash);
        btnLoan = view.findViewById(R.id.btn_loan);

        count = Integer.parseInt(String.valueOf(txtCount.getText()));

        int id = getArguments().getInt("product_id", 0);
        productReq(id);

        btnDec.setOnClickListener(view12 -> {
            if (count > 1) {
                count--;
                txtCount.setText(String.format("%d", count));
                Double total = (double) (Double.parseDouble(String.valueOf(txtPrice.getText()).substring(1)) * count);
                txtTotal.setText(String.format("$%s", total));
                txtTotalAmountPrice.setText(String.format("$%s", total));
            } else
                Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.check_pro_num), Toast.LENGTH_LONG).show();

        });

        btnInc.setOnClickListener(view1 -> {
            count++;
            txtCount.setText(String.format("%d", count));
            Double total = (double) (Double.parseDouble(String.valueOf(txtPrice.getText()).substring(1)) * count);
            txtTotal.setText(String.format("$%s", total));
            txtTotalAmountPrice.setText(String.format("$%s", total));
        });

        btnCash.setOnClickListener(view13 -> {
            btnCash.setBackgroundResource(R.drawable.back_button_cash);
            btnCash.setTextColor(getResources().getColor(R.color.white));
            btnLoan.setBackgroundColor(getResources().getColor(R.color.gray_white2));
            btnLoan.setTextColor(getResources().getColor(R.color.black));
        });
        btnLoan.setOnClickListener(view13 -> {
            btnLoan.setBackgroundResource(R.drawable.back_button_cash);
            btnLoan.setTextColor(getResources().getColor(R.color.white));
            btnCash.setBackgroundColor(getResources().getColor(R.color.gray_white2));
            btnCash.setTextColor(getResources().getColor(R.color.black));
        });
        return view;
    }

    private void productReq(int id) {
        Call<Product> call = RetrofitClient.getInstance().getMyApi().getProductById(id);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product p = response.body();
                if (p != null) {
                    Picasso.get().load(p.getImage()).into(imgProduct);
                    txtName.setText(p.getTitle());
                    txtPrice.setText(String.format("$%s", p.getPrice()));
                    txtTotal.setText(String.format("$%s", p.getPrice()));
                    txtTotalAmountPrice.setText(String.format("$%s", p.getPrice()));
                }

            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                if (isAdded())
                    Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.net_error), Toast.LENGTH_LONG).show();
                Log.e("PaymentFragment", t.toString());
            }
        });

    }
}