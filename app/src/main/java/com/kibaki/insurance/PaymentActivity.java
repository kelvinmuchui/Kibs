package com.kibaki.insurance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PaymentActivity extends AppCompatActivity   {

    private Spinner spinner,spinner2;
    private DatabaseReference mDatabase ,reference;
    TextInputEditText amount, accountNumber, acoountName,accountBranch;
    Button submit;
    FirebaseAuth auth;
    String prolicy_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Payment");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Bank");
        reference = FirebaseDatabase.getInstance().getReference().child("Bank_Branch");

        auth = FirebaseAuth.getInstance();
        prolicy_id = getIntent().getStringExtra("policy_id");

        amount = findViewById(R.id.nameedt);
        accountNumber = findViewById(R.id.accountedt);
        acoountName =findViewById(R.id.accountNameedt);
        accountBranch =findViewById(R.id.bAccountBranch);
        submit = findViewById(R.id.paybtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment();

                amount.setText("");
                Intent over = new Intent(PaymentActivity.this,MainActivity.class);
                startActivity(over);
                finish();


            }
        });

    }

    private void payment(){
        String payaount = amount.getText().toString().trim();
        String acc_name = acoountName.getText().toString().trim();
        String acc_branch = accountBranch.getText().toString().trim();
        String acc_Numb = accountNumber.getText().toString().trim();


        String user_id = auth.getCurrentUser().getUid();
        Date d = new Date();
        String maturity = (String) DateFormat.format("MMMM d, yyyy ", d.getTime());
        String pay_id = getID(10);
        String acc_id = getID(10);
        String acc_No = getID(7);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("payment_details_id",pay_id);
        hashMap.put("payment_details_amount",payaount);
        hashMap.put("payment_details_date",maturity);
        hashMap.put("payment_details_bank_account_Id",acc_id);
        hashMap.put("payment_details_policy_Id",prolicy_id);
        hashMap.put("payment_detail_subscriber_login_id",user_id);
        databaseReference.child("Payment_Details").setValue(hashMap);
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("bank_account_id",acc_id);
        hashMap2.put("bank_account_name",acc_name);
        hashMap2.put("bank_account_branch", acc_branch);
        hashMap2.put("bank_account_subscriber_login_id",user_id);
        hashMap2.put("bank_account_bank_Id","-M6fQ5Yer-SktwMMvcCq");
        hashMap2.put("bank_account_No",acc_Numb);
        hashMap2.put("bank_account_branch_Id","-M6pTpQ4SdaT-2QAPtMY");
        databaseReference.child("Bank_Account").setValue(hashMap2);


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
            Intent main = new Intent(PaymentActivity.this,MainActivity.class);
            startActivity(main);
        }
    }
}
