package com.kibaki.insurance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.password);
        login = findViewById(R.id.loginbtn);
        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Validate_email(email.getText().toString())) {
                    email.setError("Email Incorrect");
                    email.requestFocus();
                } else if (!Validate_password(password.getText().toString())) {
                    password.setError("Password Incorrect");
                    email.requestFocus();
                } else {





                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        //progressDialog.dismiss();

                                        Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));


                                    } else {
                                        Toast.makeText(LoginActivity.this, "Email And Password mismatch", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
            }
        });

    }
    private boolean Validate_password(String s) {
        if (password != null && password.length() > 6)
            return true;
        else
            return false;
    }

    private boolean Validate_email(String s) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email.getText().toString());

        return matcher.matches();

    }
}
