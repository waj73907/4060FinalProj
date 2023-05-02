package uga.edu.cs.myapplication;

import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PurchaseListFragment extends Fragment {

    private RecyclerView purchaseView;

    private ArrayList<Product> checkoutList;

    private Button cartBtn;

    public PurchaseListFragment() {

    }

    public PurchaseListFragment(ArrayList<Product> checkoutBag) {
        this.checkoutList = checkoutBag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = db.getReference().child("purchase");
        ValueEventListener purchaseListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Purchase> purchaseList = new ArrayList<>();

                for(DataSnapshot snap: snapshot.getChildren()) {
                    Purchase p = snap.getValue(Purchase.class);
                    purchaseList.add(p);
                }
                updatePurchaseUI(purchaseList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };
        db.getReference().child("purchases").addValueEventListener(purchaseListener);

    }

    private void updatePurchaseUI(ArrayList<Purchase> purchaseList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        purchaseView.setLayoutManager(layoutManager);
        PurchaseRecyclerAdapter purchaseAdapter = new PurchaseRecyclerAdapter(purchaseList);
        purchaseView.setAdapter(purchaseAdapter);
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CheckoutBagFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(((ViewGroup)(getView().getParent())).getId(), fragment);
                fragmentTransaction.commit();
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_purchase_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartBtn = view.findViewById(R.id.backToCart);
        purchaseView = view.findViewById(R.id.purchaseView);

    }
}