package com.example.expressrailwaynew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashBoard extends AppCompatActivity {

    Button btn_booking , btn_vHistory , btn_Profile,btn_logout;
    String userNic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        btn_booking = findViewById(R.id.btnBookTicket);
        btn_vHistory = findViewById(R.id.btnBookingHistory);
        btn_Profile = findViewById(R.id.btnViewProfile);
        btn_logout = findViewById(R.id.btnLgOut);

        Intent intent = getIntent();
        userNic = intent.getStringExtra("userNic");

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();

            }
        });

        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBooking = new Intent(getApplicationContext() , BookTicket.class);
                startActivity(toBooking);
            }
        });

        btn_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toProfile = new Intent(getApplicationContext() , ViewProfile.class);
                toProfile.putExtra("NIC", userNic);
                startActivity(toProfile);
            }
        });

        btn_vHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toProfile = new Intent(getApplicationContext() , ViewHistory.class);

                startActivity(toProfile);
            }
        });

    }
}