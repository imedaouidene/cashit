package com.z2p.devops.cashit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Splach extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);

        Intent to_login = new Intent(getApplicationContext(),Login.class) ;
        startActivity(to_login);

    }
}
