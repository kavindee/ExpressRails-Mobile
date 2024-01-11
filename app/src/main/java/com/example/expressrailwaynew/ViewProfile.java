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

import java.util.List;

public class ViewProfile extends AppCompatActivity {

    EditText edtName ,edtAge ,edtMobileNumber ,edtNic;

    //textView,textView2
    TextView setName,setNic;
    Button btn_UpdateProf , btn_DeleteProf;
    String newNIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Intent fromDashboardClass = getIntent();
        newNIC = fromDashboardClass.getStringExtra("NIC");




        edtName = findViewById(R.id.edtDisplayName);
        edtAge = findViewById(R.id.edtDisplayAge);
        edtMobileNumber = findViewById(R.id.edtDisplayMobileNumber);
        edtNic = findViewById(R.id.edtDisplayNic);
        setName = findViewById(R.id.textView);
        setNic = findViewById(R.id.textView2);

        DbHandler dbHandler = new DbHandler(getApplicationContext());
        List userInfo = dbHandler.getUserInfo(newNIC);

        if (userInfo.size() >= 4) {
            String userNic = userInfo.get(0).toString();
            String userName = userInfo.get(1).toString();
            String userAge = userInfo.get(2).toString();
            String userMobileNumber = userInfo.get(3).toString();

            // Set user details to TextViews and EditTexts

            setName.setText(userName);
            setNic.setText(userNic);
            edtNic.setText(userNic);
            edtName.setText(userName);
            edtAge.setText(userAge);
            edtMobileNumber.setText(userMobileNumber);
        }

        btn_UpdateProf = findViewById(R.id.btn_update);
        btn_DeleteProf = findViewById(R.id.btn_deleteProf);

        btn_UpdateProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edtName.getText().toString().trim();
                String email = edtAge.getText().toString().trim();
                String mob = edtMobileNumber.getText().toString().trim();
                String oldnic = edtNic.getText().toString().trim();

                DbHandler dbHandler = new DbHandler(getApplicationContext());
                boolean isUpdateSuccessful = dbHandler.updateUser(name, email, mob,oldnic);

                if (isUpdateSuccessful) {
                    // If the update is successful, navigate to the appropriate activity
                    Intent toLogin = new Intent(getApplicationContext(), ViewProfile.class);
                    Toast.makeText(ViewProfile.this, "Update successful", Toast.LENGTH_SHORT).show();
                    startActivity(toLogin);
                } else {
                    Toast.makeText(ViewProfile.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Delete button click listener
        btn_DeleteProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nic = edtNic.getText().toString().trim();
                DbHandler dbHandler = new DbHandler(getApplicationContext());

                // Call the delete method to delete the user
                dbHandler.deleteUser(nic);

                // Create an intent to go to the Login activity
                Intent intent = new Intent(ViewProfile.this, Login.class);

                // Display a Toast message to inform the user
                Toast.makeText(ViewProfile.this, "User deactivated successfully", Toast.LENGTH_SHORT).show();

                // Start the Login activity
                startActivity(intent);
            }
        });





    }
}