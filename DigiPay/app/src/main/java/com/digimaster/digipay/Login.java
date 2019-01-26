package com.digimaster.digipay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private TextInputEditText id ,password;
    private MaterialButton signin;

    private DatabaseReference clients;
    private ProgressDialog spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Mapping the id, password and signin button
        id = (TextInputEditText) findViewById(R.id.client_id);
        password = (TextInputEditText) findViewById(R.id.client_password);
        signin = (MaterialButton) findViewById(R.id.signin_btn);

        //Initializing the database reference
        clients = FirebaseDatabase.getInstance().getReference().child("Customer_Details");

        //Initializing the progress dialog
        spinner = new ProgressDialog(this,ProgressDialog.THEME_HOLO_LIGHT );


        //Checking login status
        //TODO Develope Login status checker
        //TODO Network status check

        //Onclick listener on signin btn
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checklogin();
            }
        });


    }

    private void checklogin() {

        final String cl_id = id.getText().toString().trim();
        final String password_ = password.getText().toString().trim();

        if(!TextUtils.isEmpty(cl_id)&& !TextUtils.isEmpty(password_)){
            spinner.setMessage("Logging In...");
            spinner.setCanceledOnTouchOutside(false);
            spinner.setCancelable(false);
            spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            spinner.show();

            //TODO query both id and mobile money number
            //Query
            Query queryon_client= clients.orderByChild("id").equalTo(cl_id);
            queryon_client.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){

                        for (DataSnapshot agents : dataSnapshot.getChildren()){
                            String push_key= dataSnapshot.getChildren().iterator().next().getKey();
                            String path = "/"+dataSnapshot.getKey()+"/"+push_key;

                            //Getting email and password of agent
                            String id= agents.child("id").getValue(String.class);
                            String password = agents.child("Password").getValue(String.class);



                            if (TextUtils.equals(id,cl_id) && TextUtils.equals(password,password_)){
                                Intent i = new Intent(Login.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                Toast.makeText(Login.this,"Invalid Login Credentials",Toast.LENGTH_SHORT).show();
                                spinner.dismiss();
                            }
                        }
                    }else{
                        Toast.makeText(Login.this,"Couldn't find your RevComs Account ",Toast.LENGTH_SHORT).show();
                        spinner.dismiss();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(Login.this,"Login Error.",Toast.LENGTH_SHORT).show();
                    spinner.dismiss();

                }
            });


        }else {
            Toast.makeText(getApplicationContext(),"Please Provide Email and Password",Toast.LENGTH_SHORT).show();
            spinner.dismiss();
        }
    }
}
