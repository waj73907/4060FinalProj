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

    private Button loginButton;
    private Button registerButton;
    public DatabaseManager manager = new DatabaseManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        manager.updateReference();
        manager.shoppingList = new ArrayList<>();
        Product p = new Product("P1", 5);
        manager.addProduct(p);


        ValueEventListener listListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                manager.shoppingList.clear();

                for(DataSnapshot ds: snapshot.getChildren()) {
                    Product product = ds.getValue(Product.class);
                    manager.shoppingList.add(product);
                }
                /*
                  any updates to the ui should happen here ideally. Since
                  the ondatachange method is asynchronous, you can only
                  access the finished state of the shopping inside these
                  brackets. You can however pass the finalized shopping
                  list to other methods.

                  Any time data inside of the database is changed, this block
                  of code will be called. So anytime a product is added, the list
                  is rebuilt from the ground up.
                */
                Log.d(dbg, manager.shoppingList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        manager.getDbReference().addValueEventListener(listListener);

    }

}
