package uga.edu.cs.myapplication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/*
    Every product contains a product key, name, price and a boolean value checkout.
    The boolean value is used to keep track of if the product is in the checkout
    bag. The key is the unique string that firebase assigns to all objects in
    the shopping list. The products are put into the database by assigning the
    name, price and checkout boolean to keys of a hashmap.

    If you want to take an item out of the checkout bag, just change the
    checkout status to false using the method found in the database manager class.
 */
public class Product implements Serializable {



    private String productKey = "";
    private String name;

    private int price;



    private boolean Checkout;


    public boolean isPurchased() {
        return Purchased;
    }

    public void setPurchased(boolean purchased) {
        Purchased = purchased;
    }

    private boolean Purchased;

    public Product() {

    }

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
        this.Checkout = false;
        this.Purchased = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public boolean isCheckout() {
        return Checkout;
    }
    public void setCheckout(boolean inCheckout) {
        this.Checkout = inCheckout;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", this.getName());
        result.put("price", this.getPrice());
        result.put("Checkout", this.isCheckout());
        result.put("Purchased", this.isPurchased());
        result.put("productKey", this.getProductKey());
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productKey='" + productKey + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", Checkout=" + Checkout +
                ", Purchased=" + Purchased +
                '}';
    }
}
