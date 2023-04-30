package com.satpay.ecoshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.satpay.ecoshop.fragment.ShoppingCartFragment;

public class ShoppingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        int id = getIntent().getIntExtra("product_id", 0);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("product_id", id);
        shoppingCartFragment.setArguments(bundle);

        transaction.replace(R.id.frameLayout_main, shoppingCartFragment);
        transaction.commit();

    }
}