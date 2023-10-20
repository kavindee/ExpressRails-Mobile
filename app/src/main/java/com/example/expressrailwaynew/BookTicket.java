package com.example.expressrailwaynew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.expressrailwaynew.Database.DbHandler;

public class BookTicket extends AppCompatActivity {

    Spinner SpinnerFrom , toSpinner;
    CalendarView cView;
    EditText passengerCount;
    Button submitButton;
    String selectedDate;
    String FromItem;
    String ToItem;
    long newID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);

        SpinnerFrom = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        cView = findViewById(R.id.calendar_View);
        passengerCount = findViewById(R.id.edt_Passengers);
        submitButton = findViewById(R.id.btnTicketBook);

        Intent intent = getIntent();
        String UNIC =  intent.getStringExtra("NIC");
        System.out.println("Get Uid is "+UNIC);

        SpinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FromItem =parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Selected: " + FromItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToItem =parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Selected: " + ToItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Month is 0-based, so add 1 to get the correct month
            selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            Toast.makeText(getApplicationContext(), "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate FromItem and ToItem
                if (FromItem == null || ToItem == null) {
                    Toast.makeText(getApplicationContext(), "Please select both 'From' and 'To' stations", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate selectedDate
                if (selectedDate == null) {
                    Toast.makeText(getApplicationContext(), "Please select a date", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate passengerCount
                String passengers = passengerCount.getText().toString().trim();
                if (passengers.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter the number of passengers", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Additional validation if needed...

                // If all validations pass, proceed to book the ticket
                DbHandler dbHandler = new DbHandler(getApplicationContext());

                newID = dbHandler.BookTicket(FromItem, ToItem, selectedDate, UNIC, passengers
                );

                if (newID != -1) {
                    Toast.makeText(getApplicationContext(), "Ticket booked successfully!"+newID , Toast.LENGTH_SHORT).show();
                    Intent toTicketSummary = new Intent(getApplicationContext(), TicketSummary.class);
                    toTicketSummary.putExtra("idOf_ticket", newID);
                    toTicketSummary.putExtra("from", FromItem);
                    toTicketSummary.putExtra("to", ToItem);
                    toTicketSummary.putExtra("date", selectedDate);
                    toTicketSummary.putExtra("passengers", passengers);
                    toTicketSummary.putExtra("userUId", UNIC);
                    startActivity(toTicketSummary);
                } else {
                    Toast.makeText(getApplicationContext(), "Error booking ticket!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}