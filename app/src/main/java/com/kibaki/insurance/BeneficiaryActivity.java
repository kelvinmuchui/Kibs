package com.kibaki.insurance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class BeneficiaryActivity extends AppCompatActivity {


    TextInputEditText name,dod,reation,contact,share;
    Button pay;
    final Calendar myCalendar = Calendar.getInstance();
    String policy_id;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiary);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Beneficiary");

        name = findViewById(R.id.bname);
        dod = findViewById(R.id.bdod);
        reation = findViewById(R.id.breation);
        contact = findViewById(R.id.bcontact);
        share =findViewById(R.id.bshare);
        pay = findViewById(R.id.paybtn);
        auth = FirebaseAuth.getInstance();
        policy_id = getIntent().getStringExtra("policy_id");



        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dod.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(BeneficiaryActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBen();

                Intent pay = new Intent(BeneficiaryActivity.this,PaymentActivity.class);
                pay.putExtra("policy_id", policy_id);
                startActivity(pay);
            }
        });


    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dod.setText(sdf.format(myCalendar.getTime()));
    }



    private  void addBen(){
        String bname = name.getText().toString().trim();
        String bdod = dod.getText().toString().trim();
        String breaction = reation.getText().toString().trim();
        String bcontact = contact.getText().toString().trim();
        String mshare = share.getText().toString().trim();
        String ben = getID(10);
        String user_id = auth.getCurrentUser().getUid();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("beneficiary_id",ben);
        hashMap.put("beneficiary_name",bname);
        hashMap.put("beneficiary_DOB",bdod);
        hashMap.put("beneficiary_relation",breaction);
        hashMap.put("beneficiary_contact",bcontact);
        hashMap.put("beneficiary_share",mshare);
        hashMap.put("beneficiary_policy_id",policy_id);
        hashMap.put("beneficiary_subscriber_login_id",user_id);
        databaseReference.child("Beneficiary").push().setValue(hashMap);

    }

    private String getID(int n)
    {

        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString
                = new String(array, Charset.forName("UTF-8"));

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();

        // remove all spacial char
        String  AlphaNumericString
                = randomString
                .replaceAll("[^A-Za-z0-9]", "");

        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < AlphaNumericString.length(); k++) {

            if (Character.isLetter(AlphaNumericString.charAt(k))
                    && (n > 0)
                    || Character.isDigit(AlphaNumericString.charAt(k))
                    && (n > 0)) {

                r.append(AlphaNumericString.charAt(k));
                n--;
            }
        }

        // return the resultant string
        return r.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_logout){
            auth.signOut();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void checkUserStatus(){
        FirebaseUser user = auth.getCurrentUser();

        if (user != null){

        }else{
            Intent main = new Intent(this,MainActivity.class);
            startActivity(main);
        }
    }

}
