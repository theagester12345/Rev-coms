package com.digimasters.rev_comsgh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private EditText username,password;
    private Button login;

  private DatabaseReference agent_details;
    private ProgressDialog spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Mapping the username,password and login button
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);

        //Initializing database reference
        agent_details = FirebaseDatabase.getInstance().getReference().child("Agent_Details");



        //Initializing the Progress Dialog
        spinner = new ProgressDialog(this,ProgressDialog.THEME_HOLO_LIGHT);



        //Checking login status
        //TODO Develope Login status checker
        //TODO Network status check


        
        
        //Setting Onclick listener on login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checklogin();
            }
        });
    }

    private void checklogin() {

        final String user_name = username.getText().toString().trim();
        final String password_ = password.getText().toString().trim();

        if(!TextUtils.isEmpty(user_name)&& !TextUtils.isEmpty(password_)){
            spinner.setMessage("Logging In...");
            spinner.setCanceledOnTouchOutside(false);
            spinner.setCancelable(false);
            spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            spinner.show();

            //Query
            Query queryon_agent = agent_details.orderByChild("email").equalTo(user_name);
            queryon_agent.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){

                        for (DataSnapshot agents : dataSnapshot.getChildren()){
                            String push_key= dataSnapshot.getChildren().iterator().next().getKey();
                            String path = "/"+dataSnapshot.getKey()+"/"+push_key;

                            //Getting email and password of agent
                            String email = agents.child("email").getValue(String.class);
                            String password = agents.child("password").getValue(String.class);



                            if (TextUtils.equals(email,user_name) && TextUtils.equals(password,password_)){
                                Intent i = new Intent(Login.this,Home.class);
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
