package com.kibaki.insurance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.kibaki.insurance.Fragment.DashFragment;
import com.kibaki.insurance.Fragment.ProductFragment;
import com.kibaki.insurance.Fragment.QuickFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView name, email;
    CircleImageView image;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String userid, uri;
    NavigationView navigationView;
    boolean exit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Fragment frag = null;
        frag = new DashFragment();
        if (frag != null) {
            FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.content, frag);
            fr.commit();
            getSupportActionBar().setTitle("Dashboard");
        }

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        name = header.findViewById(R.id.name);
        email = header.findViewById(R.id.email);
        firebaseAuth = FirebaseAuth.getInstance();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        checkUserStatus();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_logout){
            firebaseAuth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.dashboard) {
            fragment = new DashFragment();

            getSupportActionBar().setTitle("DashBoard");
        }  else if (id == R.id.products) {
            fragment = new ProductFragment();
            getSupportActionBar().setTitle("Products");

        } else if (id == R.id.quick) {
            fragment = new QuickFragment();
            getSupportActionBar().setTitle("Quick Quote");

        }

        FragmentTransaction f = getSupportFragmentManager().beginTransaction();
        f.replace(R.id.content, fragment);
        f.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (exit) {
                finish();
                return;
            }

            this.exit = true;
            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);
        }
    }




    private void checkUserStatus(){
        FirebaseUser  user = firebaseAuth.getCurrentUser();

        if (user != null){

        }else{
            Intent main = new Intent(DashBoardActivity.this,MainActivity.class);
            startActivity(main);
        }
    }
}
