package com.tna.campus_store.beans;

public class ProductKey {
    private Integer productId;
    private Integer count;


    public ProductKey() {
        super();
    }

    public ProductKey(Integer productId, Integer count) {
        super();
        this.productId = productId;
        this.count = count;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
