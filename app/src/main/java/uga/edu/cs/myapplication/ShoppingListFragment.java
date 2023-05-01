package uga.edu.cs.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShoppingListFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private Button addProductBtn;
    private Button addToBagBtn;
    private final String dbg = "SHOPPING LIST FRAGMENT";
    public ShoppingListFragment() {
        // Required empty public constructor
    }

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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseManager manager = new DatabaseManager();
        manager.updateReference();
        /*
        Product p = new Product("Lettuce", 5);
        Product p2 = new Product("Tomatoes", 5);
        Product p3 = new Product("Bread", 5);
        Product p4 = new Product("Milk", 5);
        Product p5 = new Product("Eggs", 5);


        manager.addProduct(p);
        manager.addProduct(p2);
        manager.addProduct(p3);
        manager.addProduct(p4);
        manager.addProduct(p5);

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
                manager.purchaseList.clear();

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
                updateShoppingList(manager.shoppingList, manager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        manager.getDbReference().addValueEventListener(listListener);
    }

    private void updateShoppingList(ArrayList<Product> shoppingList, DatabaseManager manager) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this.context );
        recyclerView.setLayoutManager(layoutManager);
        this.context = getActivity();
        ProductRecyclerAdapter recyclerAdapter = new ProductRecyclerAdapter(shoppingList, this.context);
        recyclerView.setAdapter(recyclerAdapter);

        this.addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment = new AddProductDialogFragment(new DatabaseManager());
                fragment.show(getActivity().getSupportFragmentManager(), null);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        addProductBtn = view.findViewById(R.id.addProductBtn);
    }
}