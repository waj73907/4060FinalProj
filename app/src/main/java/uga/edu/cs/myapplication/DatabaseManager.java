package uga.edu.cs.myapplication;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
    This class manages all of the backend interactions with the firebase
    real-time database. It's actions include: Creating new products,
    Reading already existing products, Updating already existing products,
    and Deleting already existing products (CRUD).
 */

public class DatabaseManager {

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

    private DatabaseReference dbReference = db.getReference("list");

    public boolean addProduct(String productId, Product p) {
        try {

            dbReference.child(productId).setValue(p);
            Log.d(DBM_DEBUG, "PRODUCT ADD SUCCESSFUL");
        } catch(Exception e) {
            Log.d(DBM_DEBUG, e.getMessage());
            return false;
        }
        return true;
    }





}
