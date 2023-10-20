package com.example.expressrailwaynew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.expressrailwaynew.Database.DbHandler;

public class TicketSummary extends AppCompatActivity {
    Button calcTotal,bKticketConfirm;
    TextView totalPrice;
    String userUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_summary);

        calcTotal = findViewById(R.id.calculateBTNTotal);
        totalPrice = findViewById(R.id.totalPriceTextView);
        bKticketConfirm = findViewById(R.id.bookTicketBtn);

        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        String to = intent.getStringExtra("to");
        String date = intent.getStringExtra("date");
        String passengers = intent.getStringExtra("passengers");

        userUid = intent.getStringExtra("userUId");

        System.out.println("user id is " + userUid);

        long ticketId = intent.getLongExtra("idOf_ticket", -1);

        System.out.println("Ticket ID: " + ticketId);

        TextView fromTextView = findViewById(R.id.fromTextView);
        TextView toTextView = findViewById(R.id.toTextView);
        TextView dateTextView = findViewById(R.id.dateTextView);
        TextView passengersTextView = findViewById(R.id.passengersTextView);

        // Set data in TextViews
        fromTextView.setText("From: " + from);
        toTextView.setText("To: " + to);
        dateTextView.setText("Date: " + date);
        passengersTextView.setText("Passengers: " + passengers);

        // Hide the confirm button initially
        bKticketConfirm.setVisibility(View.GONE);

        // implement total calculation method

        calcTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double calculatedPrice = calculateTotalPrice(Integer.parseInt(passengers));
                totalPrice.setText("Total Price: $" + calculatedPrice);


                bKticketConfirm.setVisibility(View.VISIBLE);
            }
        });

        bKticketConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double totalPriceValue = calculateTotalPrice(Integer.parseInt(passengers));
                String todayDate = getTodayDate();

                DbHandler dbHandler = new DbHandler(getApplicationContext());
                long newRowId = dbHandler.addHistoryRecord(userUid, ticketId, totalPriceValue,from,to,passengers, todayDate);

                if (newRowId != -1) {
                    System.out.println("New added history row ID: " + newRowId);
                } else {
                    System.out.println("Error occurred");
                }

                Intent toProfile = new Intent(getApplicationContext() , ViewHistory.class);

                startActivity(toProfile);
            }
        });
    }

    private double calculateTotalPrice(int passengerCount) {
        // Example logic: Assume a base price of $50 per passenger
        double basePricePerPassenger = 50.0;
        return basePricePerPassenger * passengerCount;
    }

    private String getTodayDate() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }
}