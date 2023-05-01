package com.satpay.ecoshop.model;

import com.google.gson.annotations.SerializedName;

public class AddProduct {
    @SerializedName("title")
    String title;

    @SerializedName("price")
    float price;

    @SerializedName("description")
    String description;

    @SerializedName("category")
    String category;

    @SerializedName("image")
    String image;

    public AddProduct(float price, String title, String description, String image, String category) {
        this.price = price;
        this.title = title;
        this.description = description;
        this.image = image;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
