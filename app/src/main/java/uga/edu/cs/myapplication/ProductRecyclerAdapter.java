package uga.edu.cs.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductHolder> {

    public static final String dbg = "ProductRecyclerAdapter";

    private List<Product> productList;

    private Context context;

    public ProductRecyclerAdapter(List<Product> shoppingList, Context context) {
        this.productList = shoppingList;
        this.context = context;
    }
    class ProductHolder extends RecyclerView.ViewHolder {
        TextView ProductName;

        TextView ProductPrice;

        public ProductHolder(View itemView) {
            super(itemView);
            ProductName = itemView.findViewById(R.id.ProductName);
            ProductPrice = itemView.findViewById(R.id.ProductPrice);
        }
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.product, parent, false);
        return new ProductHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product = productList.get( position );


        String productName = product.getName();
        int productPrice = product.getPrice();

        holder.ProductName.setText(product.getName());
        holder.ProductPrice.setText("$" + String.valueOf(product.getPrice()));

    }

    public int getItemCount() {
        return productList.size();
    }
}
