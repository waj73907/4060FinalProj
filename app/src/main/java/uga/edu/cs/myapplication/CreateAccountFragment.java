package uga.edu.cs.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/*
    This class handles the registering of new user accounts. If the
    account already exists then app wont move to the next activity.
    Standard checks to see if both fields are blank and if the
    password is less than 6 characters.
 */
public class CreateAccountFragment extends Fragment {
    private EditText email;
    private EditText password;
    private Button createAccount;
    private FirebaseAuth auth;

    public CreateAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = view.findViewById(R.id.createEmail);
        password = view.findViewById(R.id.createPassword);
        createAccount = view.findViewById(R.id.createAccountButton);
        auth = FirebaseAuth.getInstance();
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)) {
                    Toast.makeText(getActivity(), "Missing Credentials", Toast.LENGTH_SHORT).show();
                } else if (userPassword.length() < 6) {
                    Toast.makeText(getActivity(), "Password must be > 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(userEmail, userPassword);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                    // startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    // finish();
                } else {
                    Toast.makeText(getActivity(), "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}