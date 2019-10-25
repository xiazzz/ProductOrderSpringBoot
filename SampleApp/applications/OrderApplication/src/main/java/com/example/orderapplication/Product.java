package com.example.orderapplication;




public class Product {
    private long ProductID;
    private String ProductName;
    private String ProductDesc;

    public Product() {}

//    public Product(long productID, String productName, String productDesc) {
//        ProductID = productID;
//        ProductName = productName;
//        ProductDesc = productDesc;
//    }

    public Product(String productName, String productDesc) {
        ProductName = productName;
        ProductDesc = productDesc;
    }

    public long getProductID() {
        return ProductID;
    }

    public void setProductID(long productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductDesc() {
        return ProductDesc;
    }

    public void setProductDesc(String productDesc) {
        ProductDesc = productDesc;
    }
}
