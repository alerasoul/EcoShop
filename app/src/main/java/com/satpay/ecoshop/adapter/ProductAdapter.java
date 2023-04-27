package com.satpay.ecoshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.satpay.ecoshop.R;
import com.satpay.ecoshop.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    List<Product> data = new ArrayList<>();
    OnClickListener onClickListener;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new MyViewHolder(view.getRootView());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product p = data.get(position);
        holder.itemView.setOnClickListener(view -> onClickListener.onClick(p));
        holder.txtName.setText(p.getTitle());
        holder.txtDesc.setText(String.format("%s ...", p.getDescription().substring(0, 45)));
        holder.txtPrice.setText(String.format("%s", p.getPrice()));
        holder.boxCategory.setText(p.getCategory());
        Picasso.get().load(p.getImage()).into(holder.imgProduct);

        setImgStarColor(p.getRating().getRate(), 0.5f, holder.imgStar1);
        setImgStarColor(p.getRating().getRate(), 1.5f, holder.imgStar2);
        setImgStarColor(p.getRating().getRate(), 2.5f, holder.imgStar3);
        setImgStarColor(p.getRating().getRate(), 3.5f, holder.imgStar4);
        setImgStarColor(p.getRating().getRate(), 4.5f, holder.imgStar5);
    }

    public void setImgStarColor(float rate, float num, ImageView view) {
        if (rate >= num)
            view.setImageResource(R.drawable.ic_star_fill);
        else
            view.setImageResource(R.drawable.ic_star_border);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(Product product) {
        data.add(product);
    }

    public void clearData() {
        data.clear();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        ImageView imgStar1, imgStar2, imgStar3, imgStar4, imgStar5;
        Button boxCategory;
        TextView txtName, txtDesc, txtPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_product);
            imgStar1 = itemView.findViewById(R.id.img_star1);
            imgStar2 = itemView.findViewById(R.id.img_star2);
            imgStar3 = itemView.findViewById(R.id.img_star3);
            imgStar4 = itemView.findViewById(R.id.img_star4);
            imgStar5 = itemView.findViewById(R.id.img_star5);
            boxCategory = itemView.findViewById(R.id.box_category);
            txtName = itemView.findViewById(R.id.txt_name);
            txtDesc = itemView.findViewById(R.id.txt_desc);
            txtPrice = itemView.findViewById(R.id.txt_price);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(Product p);
    }
}
