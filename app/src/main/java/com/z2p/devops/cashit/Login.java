package com.z2p.devops.cashit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    Button login ;
    EditText email,password ;
    TextView signup ;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button)findViewById(R.id.LoginButton) ;
        email = (EditText)findViewById(R.id.useremail) ;
        password = (EditText)findViewById(R.id.userpassword) ;
        signup = (TextView)findViewById(R.id.signupbutton) ;

        email.setText("momo@momo.com");
        password.setText("momo123");



        /****FireBase configuration***/

        mAuth = FirebaseAuth.getInstance();

        /*************************/

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_email = email.getText().toString();
                String user_password = password.getText().toString() ;
                Boolean check_email_password = true ;


                if(!email.getText().toString().contains("@")||!email.getText().toString().contains(".")){
                    email.setError("Email must be valid ");
                    check_email_password = false ;
                }


                if (password.getText().toString().length()<=8){
                    password.setError("Must containt 8 caracters");
                    check_email_password=false;
                }

                if (check_email_password){

                mAuth.signInWithEmailAndPassword(user_email, user_password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("user", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getApplicationContext(),user.getEmail(),Toast.LENGTH_LONG).show();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("user", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_LONG).show();

                                }

                                // ...
                            }
                        });
                }





            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showsignup();
            }
        });









    }

    private void showsignup() {

        AlertDialog.Builder mybuilder  = new AlertDialog.Builder(getApplicationContext());

        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService (Context.LAYOUT_INFLATER_SERVICE) ;
        View view
    }

    @Override
    protected void onStart() {
        super.onStart();



    }
}
