package uga.edu.cs.myapplication;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/*
    This class manages all of the backend interactions with the firebase
    real-time database. It's actions include: Creating new products,
    Reading already existing products, Updating already existing products,
    and Deleting already existing products (CRUD).


 */

public class DatabaseManager {

    private final String dbg = "DATABASE_MANAGER";

    public DatabaseManager(FirebaseDatabase db, DatabaseReference listReference) {
        this.db = db;
        this.dbReference = listReference;
    }

    public DatabaseManager() {


    }

    public DatabaseReference getDbReference() {
        return dbReference;
    }

    private final String DBM_DEBUG = "DATABASE MANAGER DEBUG";
    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    private DatabaseReference dbReference = db.getReference();

    public ArrayList<Product> shoppingList = new ArrayList<>();

    public ArrayList<Product> checkoutBag = new ArrayList<>();

    public boolean addProduct(Product p) {
        try {
            this.updateReference();
            String key = dbReference.push().getKey();
            p.setProductKey(key);
            dbReference.child(key).setValue(p.toMap());
            Log.d(DBM_DEBUG, "PRODUCT ADD SUCCESSFUL");
        } catch(Exception e) {
            Log.d(DBM_DEBUG, e.getMessage());
            return false;
        }
        return true;
    }




    public boolean updateProductName(Product p, String name) {
        try {
            this.updateReference();
            String key = p.getProductKey();
            dbReference.child(key).child("name").setValue(name);
            return true;
        } catch (Exception e) {
            Log.d(dbg, "ERROR UPDATING NAME: " + e.getMessage());
            return false;
        }
    }

    public boolean updateProductPrice(Product p, int price) {
        try {
            this.updateReference();
            String key = p.getProductKey();
            dbReference.child(key).child("price").setValue(price);
            return true;
        } catch (Exception e) {
            Log.d(dbg, "ERROR UPDATING PRICE: " + e.getMessage());
            return false;
        }
    }

    public void updateReference() {
        this.dbReference = db.getReference("list");
    }

    public boolean deleteProductFromShoppingList(Product p) {
        try {
            this.updateReference();
            String key = p.getProductKey();
            dbReference.child(key).removeValue();
            return true;
        } catch (Exception e) {
            Log.d(dbg, "ERROR DELETING PRODUCT: " + e.getMessage());
            return false;
        }
    }
    public boolean updateItemCheckoutStatus(Product p, boolean status) {
        try {
            this.updateReference();
            dbReference.child(p.getProductKey()).child("Checkout").setValue(status);
            return true;
        } catch (Exception e) {
            Log.d(dbg, "ERROR UPDATING ITEM CHECKOUT STATUS: " + e.getMessage());
            return false;
        }
    }

    public Product getProductFromShoppingList(String key) {
        Product returnProduct = null;
        for (Product p : this.shoppingList) {
            if (p.getProductKey().equals(key)) {
                return returnProduct = p;
            }
        }
        return null;
    }
}
