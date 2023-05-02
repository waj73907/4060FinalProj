package uga.edu.cs.myapplication;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Purchase {
    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "totalCost=" + totalCost +
                ", perRoomateCost=" + perRoomateCost +
                ", datePurchased='" + datePurchased + '\'' +
                '}';
    }

    public double getPerRoomateCost() {
        return perRoomateCost;
    }

    public void setPerRoomateCost(double perRoomateCost) {
        this.perRoomateCost = perRoomateCost;
    }

    public String getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(String datePurchased) {
        this.datePurchased = datePurchased;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        this.Key = key;
    }

    private String Key;
    private double totalCost;

    private double perRoomateCost;

    private String datePurchased;

    public Purchase() {

    }

    public Purchase(double totalCost, double perRoomateCost) {
        this.totalCost = totalCost;
        this.perRoomateCost = perRoomateCost;
        Format f = new SimpleDateFormat("MM/dd/yy");
        this.datePurchased = f.format(new Date());
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap();
        result.put("totalCost", this.getTotalCost());
        result.put("perRoomateCost", this.getPerRoomateCost());
        result.put("datePurchased", this.getDatePurchased());
        result.put("Key", this.getKey());
        return result;
    }

    public void addToDB() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = db.getReference().child("purchases");
        String key = dbRef.push().getKey();
        this.setKey(key);
        dbRef.child(key).setValue(this.toMap());
    }
}
