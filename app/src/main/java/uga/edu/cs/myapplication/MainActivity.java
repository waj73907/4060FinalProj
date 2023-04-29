package uga.edu.cs.myapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/*

 */
public class MainActivity extends AppCompatActivity  {

    private final String dbg = "MAIN_ACTIVITY";
    public DatabaseManager manager = new DatabaseManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Product p = new Product("Eggs", 5);
        manager.addProduct(p);
        /*
    Setting a value listener for the "list" branch of the database.
    Everytime any form of data is changed on the "List" branch this listener
    is called and the onDataChange method is executed. This way we can ensure
    that the shopping list and checkoutBag inside of the DatabaseManager
    object are the most up to date that they can be with the product's
    key, name, price, checkout status and purchased status.

    The caveat to using the valueEvent listener is detailed below in the
    3 block comment above the total roommate cost variable.
 */
        ValueEventListener listListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*
                   Both shopping list and checkout bag are
                   cleared everytime they need to update just
                   to avoid any confusion.
                 */
                manager.shoppingList.clear();
                manager.checkoutBag.clear();

                double totalCost = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Product product = ds.getValue(Product.class);
                    manager.shoppingList.add(product);

                    /*
                       if-statement that checks if the product from
                       the snapshot's checkout value is true, and if
                       so adds to the checkout bag.
                     */

                    if (product.isCheckout()) {
                        manager.checkoutBag.add(product);
                        totalCost += product.getPrice();
                    }


                }
                /*
                  any updates to the ui should happen here ideally. Since
                  the ondatachange method is asynchronous, you can only
                  access the finished state of the shopping inside these
                  brackets. You can however pass the finalized shopping
                  list to other methods for access to the shopping list
                  outside of this block.

                  Any time data inside of the database is changed, the
                  ondatachange method will be called.
                  So anytime a product is added, the list
                  is rebuilt from the ground up.

                  Also with the cost just assuming there are four roommates
                  since theres nothing in the stories about needing to specify
                  the number of roommates in the app.
                */
                double perRoomateCost = Math.round((totalCost / 4.0) * 100.0) / 100.0;
                Log.d(dbg, "LOGGING SHOPPING LIST: " + manager.shoppingList.toString());
                Log.d(dbg, "LOGGING CHECKOUT BAG: " + manager.checkoutBag.toString());
                Log.d(dbg, "LOGGING PerRoomate Cost: " + perRoomateCost);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        manager.getDbReference().addValueEventListener(listListener);

    }


}
