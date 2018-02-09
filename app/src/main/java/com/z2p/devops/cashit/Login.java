package com.z2p.devops.cashit;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    Button login ;
    EditText email,password ;
    TextView signup ;

    private FirebaseAuth mAuth;
    FirebaseDatabase database ;
    DatabaseReference myRef ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button)findViewById(R.id.LoginButton) ;
        email = (EditText)findViewById(R.id.useremail) ;
        password = (EditText)findViewById(R.id.userpassword) ;
        signup = (TextView)findViewById(R.id.newaccount) ;
        database = FirebaseDatabase.getInstance();


        email.setText("momo@momo.com");
        password.setText("momo1234");

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
                    sign_in(user_email,user_password );
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

    EditText signup_email,signup_password,signup_password2 ;
    Button signup_button ;

    private void showsignup() {

        Dialog dialog = new Dialog(Login.this);
        dialog.setContentView(R.layout.signup);
        signup_button = (Button)dialog.findViewById(R.id.signupbutton) ;
        signup_password = (EditText)dialog.findViewById(R.id.signupuserpassword) ;
        signup_password2 = (EditText)dialog.findViewById(R.id.signupuserpassword2) ;
        signup_email = (EditText)dialog.findViewById(R.id.signupuseremail) ;
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user_email = signup_email.getText().toString();
                final String user_password = signup_password.getText().toString() ;
                String user_password2 = signup_password2.getText().toString() ;
                Boolean check = true ;




                if(!signup_email.getText().toString().contains("@")||!signup_email.getText().toString().contains(".")){
                    signup_email.setError("Email must be valid ");
                    check=false;
                }


                if (signup_password.getText().toString().length()<=8){
                    signup_password.setError("Must containt 8 caracters");
                    check=false;
                }
                if (!user_password2.equals(user_password)){
                    signup_password2.setError("passwords mismatch");
                    check=false;
                }
                if(check){

                    mAuth.createUserWithEmailAndPassword(user_email, user_password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("sign up", "createUserWithEmail:success");




                                        FirebaseUser user = mAuth.getCurrentUser();
                                        save_user(user,user_password);
                                        sign_in(user_email,user_password );

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("sign up", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(Login.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }


                                }
                            });


                }
            }
                                         });


        dialog.show();





    }

    private void save_user(FirebaseUser user, String user_password) {

        myRef = database.getReference("users/"+user.getUid());
        myRef.child("email").setValue(user.getEmail()) ;
        myRef.child("password").setValue(user_password) ;



    }

    @Override
    protected void onStart() {
        super.onStart();



    }
    private void sign_in(String email,String password ){
        mAuth.signInWithEmailAndPassword(email, password)
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
