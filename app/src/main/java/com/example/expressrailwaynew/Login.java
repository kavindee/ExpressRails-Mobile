package com.example.expressrailwaynew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expressrailwaynew.Database.DbHandler;

public class Login extends AppCompatActivity {

    EditText edtLg;
    Button btnLgSubmit;
    String nicLg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        edtLg = findViewById(R.id.email);
        btnLgSubmit = findViewById(R.id.btnLogin);

        btnLgSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nicLg =  edtLg.getText().toString();

                DbHandler dbHandler = new DbHandler(Login.this);
                boolean userExists = dbHandler.checkUserExistence(nicLg);

                if (userExists) {

                    Intent intent = new Intent(Login.this, DashBoard.class);
                    intent.putExtra("userNic" , nicLg);
                    startActivity(intent);
                } else {

                    Toast.makeText(Login.this, "Invalid NIC", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView linkRegister = (TextView) findViewById(R.id.linkRegister);

        linkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateProfile.class);
                startActivity(intent);
            }
        });
    }
}