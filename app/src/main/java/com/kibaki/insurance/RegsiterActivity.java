package com.kibaki.insurance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegsiterActivity extends AppCompatActivity {

    EditText name,contact,password,dob,nationality,email,occupation, employer,pin,address,status,gender;
    Button reg;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsiter);
        name =findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        password = findViewById(R.id.password);
        dob = findViewById(R.id.Date_of_birth);
        nationality = findViewById(R.id.nationality);
        email = findViewById(R.id.email);
        occupation = findViewById(R.id.occupation);
        employer = findViewById(R.id.employer);
        pin = findViewById(R.id.pin);
        address = findViewById(R.id.address);
        status = findViewById(R.id.status);
        gender = findViewById(R.id.gender);
        reg = findViewById(R.id.regbtn);


        firebaseAuth = FirebaseAuth.getInstance();


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String mEmail = email.getText().toString().trim();
                final String mPassword = password.getText().toString().trim();
                if (!Validate_email(mEmail)) {
                    email.setError("Incorrect Email");
                    email.requestFocus();

                } else if (!Validate_password(mPassword)) {
                    password.setError("Password must be greater than 6 characters");
                    password.requestFocus();
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String user_id = firebaseAuth.getCurrentUser().getUid();
                            createLoginSubscriber(user_id);

                            Intent Dashboard = new Intent(RegsiterActivity.this,DashBoardActivity.class);
                            startActivity(Dashboard);
                            finish();

                        }
                    });

                }

            }
        });

    }

    private void createLoginSubscriber(String sid){
        String  sname,scontact,spassword,sdob,snationality,semail,soccupation, semployer,spin,saddress,sstatus,sgender;
        sname = name.getText().toString().trim();
        scontact = contact.getText().toString().trim();
        spassword= password.getText().toString().trim();
        sdob = dob.getText().toString().trim();
        snationality = nationality.getText().toString().trim();
        semail = email.getText().toString().trim();
        soccupation = occupation.getText().toString().trim();
        semployer = employer.getText().toString().trim();
        spin = pin.getText().toString().trim();
        saddress = address.getText().toString().trim();
        sstatus = status.getText().toString().trim();
        sgender = gender.getText().toString().trim();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("subscriber_login_id",sid );
        hashMap.put("subscriber_login_name",sname );
        hashMap.put("subscriber_login_contact",scontact );
        hashMap.put("subscriber_login_password" ,spassword );
        hashMap.put("subscriber_login_sdob", sdob );
        hashMap.put("subscriber_login_nationality",snationality );
        hashMap.put("subscriber_login_email",semail );
        hashMap.put("subscriber_login_occupation",soccupation );
        hashMap.put("subscriber_login_employer", semployer );
        hashMap.put("subscriber_login_pin",spin );
        hashMap.put("subscriber_login_address", saddress );
        hashMap.put("subscriber_login_status",sstatus );
        hashMap.put("subscriber_login_gender",sgender );

        databaseReference.child("Subscriber_login").push().setValue(hashMap);

    }

    private boolean Validate_password(String s) {
        if (s != null && s.length() > 6)
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
}
