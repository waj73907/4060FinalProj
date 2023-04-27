package uga.edu.cs.myapplication;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public boolean addProduct(Product p) {
        try {
            String key = dbReference.push().getKey();
            Log.d(dbg, key);
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
        return true;
    }

    public void updateReference() {
        this.dbReference = db.getReference("list");
    }

    public void deleteProduct(Product p) {
        this.dbReference.child(p.getName());
    }





}
