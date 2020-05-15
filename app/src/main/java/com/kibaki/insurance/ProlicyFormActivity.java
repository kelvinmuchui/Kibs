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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class ProlicyFormActivity extends AppCompatActivity {


    RadioGroup radioGroup,radioGroup2,radioGroup3;

    TextInputEditText sumassured,totalprim,primterms,polterms,primsh;

    RadioButton debitRadio,checkRadio,cheque,anually,half,month,quater,personalRadio,funerelRadio,illnessRadio,deathRadio;

    String frequency,Benef, payment;

    Button back, next;

    FirebaseAuth auth;
    String policy_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prolicy_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Policy");

        radioGroup=findViewById(R.id.radiogroup);
        radioGroup2=findViewById(R.id.radiogroup2);
        radioGroup3 =findViewById(R.id.radiogroup3);
        debitRadio = findViewById(R.id.deathRadio);
        checkRadio = findViewById(R.id.checkRadio);
        cheque = findViewById(R.id.chequeRadio);
        anually = findViewById(R.id.anually);
        half = findViewById(R.id.half);
        month = findViewById(R.id.monthly);
        quater = findViewById(R.id.quataly);
        personalRadio = findViewById(R.id.personalRadio);
        funerelRadio = findViewById(R.id.funerelRadio);
        illnessRadio = findViewById(R.id.illnessRadio);
        deathRadio = findViewById(R.id.deathRadio);
        auth = FirebaseAuth.getInstance();

        sumassured = findViewById(R.id.pname);
        totalprim =findViewById(R.id.ptotalP);
        primterms = findViewById(R.id.pterms);
        polterms = findViewById(R.id.ppolicy);
        primsh = findViewById(R.id.ptotalPrem);
        back = findViewById(R.id.backbtn);
        next = findViewById(R.id.nextbtn);


        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (debitRadio.isChecked()){
                    payment = "Debit";
                }else if(checkRadio.isChecked()){
                    payment = "Check Off";

                }else {
                    payment = "Cheque";
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(anually.isChecked()){
                    frequency = "Anually";
                }else if(half.isChecked()){
                    frequency = "Half year";
                }else if(quater.isChecked()){
                    frequency = "Three Months";
                }else{
                    frequency = "One Month";
                }
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(personalRadio.isChecked()){
                    Benef = "Personal";
                }else if(funerelRadio.isChecked()){
                    Benef = "Funeral benefits";

                } else if (illnessRadio.isChecked()) {
                    Benef = "Critical Illness";
                }else {
                    Benef = "Accidental death";

                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();


                Intent b = new Intent(ProlicyFormActivity.this,BeneficiaryActivity.class);
                b.putExtra("prolicy_id", policy_id);
                startActivity(b);
            }
        });



    }


    private  void addData(){
        String mpolicy_id,mpolicy_name, mpolicy_sum_assured,mpolicy_total_premium,mprim_term, mprimtotal;
        String mpolicy_term,mpolicy_maturity_date,mpolicy_subcriber_loginID;

        mpolicy_sum_assured = sumassured.getText().toString().trim();
        mpolicy_total_premium = totalprim.getText().toString().trim();
        mpolicy_term =polterms.getText().toString().trim();
        mprim_term = primterms.getText().toString().trim();
        mprimtotal = totalprim.getText().toString().trim();

        String user_id = auth.getCurrentUser().getUid();
        policy_id = getID(10);
        String benif = getID(10);
        Date d = new Date();
        String maturity = (String) DateFormat.format("MMMM d, yyyy ", d.getTime());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("policy_id",policy_id);
        hashMap.put("policy_name","Life");
        hashMap.put("policy_premium_frequrency",frequency);
        hashMap.put("policy_payment_mode",payment);
        hashMap.put("policy_total_premium",mpolicy_total_premium);
        hashMap.put("policy_sum_assured",mpolicy_sum_assured);
        hashMap.put("policy_term",mpolicy_term);
        hashMap.put("policy_maturity_date",maturity);
        hashMap.put("policy_subcriber_loginID",user_id);
        databaseReference.child("Policy").push().setValue(hashMap);
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("optional_benefit_id",benif);
        hashMap2.put("optional_benefit_name",Benef);
        hashMap2.put("optional_benefit_policy_id",policy_id);
        hashMap2.put("optiont_benefit_subcriber_loginID",user_id);
        databaseReference.child("Optional_Benefits").push().setValue(hashMap2);


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
