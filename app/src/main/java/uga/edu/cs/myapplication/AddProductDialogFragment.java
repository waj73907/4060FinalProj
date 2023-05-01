package uga.edu.cs.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class AddProductDialogFragment extends DialogFragment {
    private EditText addProductName;

    private EditText addProductPrice;

    private DatabaseManager manager;


    public interface AddProductDialogListener {
        void addProduct(Product p);
    }

    public AddProductDialogFragment(DatabaseManager manager) {
        this.manager = manager;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.fragment_add_product_dialog, getActivity().findViewById(R.id.root));

        addProductName = layout.findViewById(R.id.addDialogName);
        addProductPrice = layout.findViewById(R.id.addDialogPrice);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), com.google.android.material.R.style.AlertDialog_AppCompat);
        builder.setView(layout);
        builder.setTitle("New Product");
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton(R.string.submit, new AddProductlistener());

        return builder.create();
    }

    private class AddProductlistener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String productName = addProductName.getText().toString();
            int productPrice = Integer.valueOf(addProductPrice.getText().toString());
            Product p = new Product(productName, productPrice);

            manager.addProduct(p);
            dismiss();
        }
    }
}