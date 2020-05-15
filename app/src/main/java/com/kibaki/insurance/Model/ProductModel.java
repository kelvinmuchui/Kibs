package com.kibaki.insurance.Model;

public class ProductModel {

    String product_name, product_id;

    public ProductModel() {
    }

    public ProductModel(String product_name, String product_id) {
        this.product_name = product_name;
        this.product_id = product_id;
    }



    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
