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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class DependantFormActivity extends AppCompatActivity {

    TextInputEditText name , id, email, nationality,address,dod;
    Button add, next;
    FirebaseAuth auth;
    RadioButton male, female;
    RadioGroup radioGroup;
    String gender;
    final Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dependant_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Dependants");

        name = findViewById(R.id.nameedt);
        id  = findViewById(R.id.idedt);
        email = findViewById(R.id.emailedt);
        nationality = findViewById(R.id.nationalityedt);
        address = findViewById(R.id.addressedt);
        dod = findViewById(R.id.dodedt);
        add = findViewById(R.id.addbtn);
        next = findViewById(R.id.nextbtn);
        auth = FirebaseAuth.getInstance();



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
                new DatePickerDialog(DependantFormActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        radioGroup = findViewById(R.id.radiogroup);
        male = findViewById(R.id.radiomale);
        female = findViewById(R.id.radioFemale);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(male.isChecked()){
                    gender = "Male";
                }else

                    gender = "Female";

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addDepedant();
                name.setText("");
                id.setText("");
                email.setText("");
                nationality.setText("");
                address.setText("");
                dod.setText("");


            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDepedant();
                name.setText("");
                id.setText("");
                email.setText("");
                nationality.setText("");
                address.setText("");
                dod.setText("");
                Intent intent = new Intent(DependantFormActivity.this,ProlicyFormActivity.class);
                startActivity(intent);


            }
        });
    }

    private void addDepedant() {


        if(name.equals("")){
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();

        }else if (email.equals("")){
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
        }else if(id.equals("")){
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
        }else if(nationality.equals("")){
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
        }else if(address.equals("")){
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
        } else if(address.equals("")){
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
        }else {
            String mname = name.getText().toString();
            String mid = id.getText().toString();
            String memail = email.getText().toString();
            String mnationality = nationality.getText().toString();
            String maddress = address.getText().toString() ;
            String mdod =dod.getText().toString();

            String user_id = auth.getCurrentUser().getUid();


            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("dependant_id",mid);
            hashMap.put("dependant_name",mname );
            hashMap.put("dependant_dod",mdod );
            hashMap.put("dependant_gender",gender );
            hashMap.put("dependant_nationality",mnationality );
            hashMap.put("dependant_email",memail);
            hashMap.put("dependant_address",maddress );
            hashMap.put("dependant_subscriber_login_id",user_id );

            databaseReference.child("Dependant").push().setValue(hashMap);
        }



    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dod.setText(sdf.format(myCalendar.getTime()));
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
