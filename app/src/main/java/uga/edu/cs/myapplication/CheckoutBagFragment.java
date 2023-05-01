package uga.edu.cs.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CheckoutBagFragment extends Fragment {



    private static final String dbg = "CheckoutBagFragment";
    private ArrayList<Product> checkoutList;
    private Button purchasesBtn;

    private Button checkoutBtn;
    private Button backToListBtn;

    private RecyclerView checkoutView;
    public CheckoutBagFragment() {
        // Required empty public constructor
    }

    public CheckoutBagFragment(ArrayList<Product> checkoutList) {
        this.checkoutList = checkoutList;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        purchasesBtn = view.findViewById(R.id.purchasesBtn);
        checkoutBtn = view.findViewById(R.id.checkoutBtn);
        checkoutView = view.findViewById(R.id.checkoutView);
        backToListBtn = view.findViewById(R.id.backToListBtn);





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
                updateCheckoutList(manager.checkoutBag);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        manager.getDbReference().addValueEventListener(listListener);
    }

    private void updateCheckoutList(ArrayList<Product> checkoutBag) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.getContext());
        checkoutView.setLayoutManager(layoutManager);
        ProductRecyclerAdapter recyclerAdapter = new ProductRecyclerAdapter(checkoutList, getContext());
        checkoutView.setAdapter(recyclerAdapter);
        backToListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ShoppingListFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(((ViewGroup)(getView().getParent())).getId(), fragment);
                fragmentTransaction.commit();

            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout_bag, container, false);
    }
}