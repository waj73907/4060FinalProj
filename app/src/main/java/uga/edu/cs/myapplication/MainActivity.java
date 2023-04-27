package uga.edu.cs.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/*
    This is the main activity where I assume most of the computation
    will take place. All of the products that are in the database are
    held inside the "shoppingList" ArrayList which gets updated everytime
    something in the "list" branch of the database gets updated. This
    list should always be the most up-to-date version of the shopping
    list.
 */
public class MainActivity extends AppCompatActivity {

    private final String dbg = "MAIN_ACTIVITY";

    private Button LoginButton;
    private Button RegisterButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseManager manager =  new DatabaseManager();
        manager.updateReference();
        ArrayList<Product> shoppingList = new ArrayList<>();
        Product p = new Product("Lettuce", 5);
        //manager.deleteProduct(p);
        manager.addProduct(p);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product p = snapshot.getValue(Product.class);
                    shoppingList.add(p);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("START_ACTIVITY", "loadPost:onCancelled", databaseError.toException());
            }
        };
        manager.getDbReference().addValueEventListener(postListener);
    }
}