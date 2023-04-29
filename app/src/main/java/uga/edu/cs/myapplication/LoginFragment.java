package uga.edu.cs.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*
    This class handles the logging in of already existing users.
    The process of checking the credentials in the users section
    of the database is entirely offloaded to firebase.
 */
public class LoginFragment extends Fragment {
    private Button createAccountHere; // go to create account fragment page

    private EditText userEmail;
    private EditText userPassword;
    private Button loginButton;
    private FirebaseAuth auth;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userEmail = view.findViewById(R.id.loginEmail);
        userPassword = view.findViewById(R.id.loginPassword);
        loginButton = view.findViewById(R.id.loginButton);
        createAccountHere = view.findViewById(R.id.createAccountHereButton);
        auth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = userEmail.getText().toString();
                String txt_password = userPassword.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(getActivity(), "Missing Credentials", Toast.LENGTH_SHORT).show();
                }
                else loginUser(txt_email, txt_password);
            }
        });
        createAccountHere.setOnClickListener(new CreateAccountButtonClickListener());
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                 FirebaseUser user = auth.getCurrentUser();
                 Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                // Sign in success, update UI with the signed-in user's information

                ShoppingListFragment shoppingList = new ShoppingListFragment();
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.loginfragment, shoppingList);
                fragmentTransaction.addToBackStack("shopping list fragment");
                fragmentTransaction.commit();
            }
        }).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Unsuccessful Login", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private class CreateAccountButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // create the new fragment
            CreateAccountFragment createAccountFragment = new CreateAccountFragment();
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.loginfragment, createAccountFragment);
            fragmentTransaction.addToBackStack("create account fragment");
            fragmentTransaction.commit();
        }
    }
}