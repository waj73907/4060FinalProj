package uga.edu.cs.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PurchaseRecyclerAdapter extends RecyclerView.Adapter<PurchaseRecyclerAdapter.PurchaseHolder> {
    public static final String dbg = "PurchaseRecyclerAdapter";
    private List<Purchase> purchaseList;



    public PurchaseRecyclerAdapter(List<Purchase> purchaseList) {
        this.purchaseList = purchaseList;
    }

    class PurchaseHolder extends RecyclerView.ViewHolder {
        TextView totalCost;
        TextView perRoomate;

        public PurchaseHolder(View itemView) {
            super(itemView);
            totalCost = itemView.findViewById(R.id.totalPrice);
            perRoomate = itemView.findViewById(R.id.perRoomate);
        }
    }


    @NonNull
    @Override
    public PurchaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from( parent.getContext()).inflate(R.layout.purchase, parent, false);
        return new PurchaseHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseHolder holder, int position) {
        Purchase p = purchaseList.get( position );

        double totalPrice = p.getTotalCost();
        double perRoomate = p.getPerRoomateCost();

        holder.totalCost.setText("$" + totalPrice);
        holder.perRoomate.setText("$" + perRoomate);

    }

    public int getItemCount() {
        return purchaseList.size();
    }
}
