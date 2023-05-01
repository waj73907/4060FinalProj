package uga.edu.cs.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProductDialogFragment extends DialogFragment {
    private static final String dbg = "EDIT_PRODUCT_DIALOG";

    public static final int SAVE = 1;

    public static final int DELETE = 2;

    private EditText productNameView;

    private EditText productPriceView;

    private DatabaseManager manager;

    int position;

    String key;

    String name;

    int price;

    boolean checkout;

    boolean purchased;


    public interface EditProductDialogListener {
        void updateProduct(int position, Product product, int action);
    }

    public static EditProductDialogFragment newInstance(int position, String key, String name, int price, boolean checkout, boolean purchased, DatabaseManager manager) {
        EditProductDialogFragment dialog = new EditProductDialogFragment();

        Bundle args = new Bundle();
        args.putString("key", key);
        args.putInt("position", position);
        args.putString("name", name);
        args.putInt("price", price);
        args.putBoolean("checkout", checkout);
        args.putBoolean("purchased", purchased);
        dialog.setArguments(args);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        key = getArguments().getString("key");
        Log.d(dbg, key);
        name = getArguments().getString("name");
        position = getArguments().getInt("position");
        price = getArguments().getInt("price");
        checkout = getArguments().getBoolean("checkout");
        purchased = getArguments().getBoolean("purchased");

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.fragment_add_product_dialog, getActivity().findViewById(R.id.root));

        productNameView = layout.findViewById(R.id.addDialogName);
        productPriceView = layout.findViewById(R.id.addDialogPrice);

        productNameView.setText(name);
        productPriceView.setText(String.valueOf(price));
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity(), com.google.android.material.R.style.AlertDialog_AppCompat);
        builder.setView(layout);
        builder.setTitle("Edit Product");


        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("SAVE", new SaveButtonClickListener() );

        //builder.setNeutralButton("DEKETE", new DeleteButtonClickListener() );

        return builder.create();
    }

    private class SaveButtonClickListener implements  DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference dbReference = db.getReference().child("list").child(key);
            name = productNameView.getText().toString();
            price = Integer.valueOf(productPriceView.getText().toString());
            dbReference.child("name").setValue(name);
            dbReference.child("price").setValue(Integer.valueOf(price));
            dismiss();
        }
    }
}