package com.kibaki.insurance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button login, reg;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        login = findViewById(R.id.loginbtn);
        reg = findViewById(R.id.regbtn);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(MainActivity.this, RegsiterActivity.class);
                startActivity(reg);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(reg);
            }
        });

        checkUserStatus();
    }
    private void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user == null){

        }else{
            Intent main = new Intent(MainActivity.this,DashBoardActivity.class);
            startActivity(main);
        }
    }
}
