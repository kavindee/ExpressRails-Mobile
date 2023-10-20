package com.example.expressrailwaynew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expressrailwaynew.Database.DbHandler;

public class CreateProfile extends AppCompatActivity {

    EditText Enter_name ,Enter_email,Enter_mobileNumber , Enter_nic,Enter_password;

    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Enter_name = findViewById(R.id.txtFirstName);
        Enter_email = findViewById(R.id.txtEmail);
        Enter_mobileNumber = findViewById(R.id.txtPhone);
        Enter_nic = findViewById(R.id.txtCreditCard);
        Enter_password = findViewById(R.id.txtPassword);
        submitBtn = findViewById(R.id.btnRegister);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input values
                String name = Enter_name.getText().toString().trim();
                String email = Enter_email.getText().toString().trim();
                String mobileNumber = Enter_mobileNumber.getText().toString().trim();
                String nic = Enter_nic.getText().toString().trim();
                String password = Enter_password.getText().toString().trim();

                // Perform input validations
                if (name.isEmpty()) {
                    Enter_name.setError("Name is required");
                    return;
                }

                if (email.isEmpty()) {
                    Enter_email.setError("Email is required");
                    return;
                }


                if (mobileNumber.isEmpty()) {
                    Enter_mobileNumber.setError("Mobile number is required");
                    return;
                }

                if (password.isEmpty()) {
                    Enter_password.setError("Password is required");
                    return;
                }

                // Add similar validations for nic
                if (nic.isEmpty()) {
                    Enter_nic.setError("NIC is required");
                    return;
                }
                // If all validations pass, proceed with registration
                DbHandler dbHandler = new DbHandler(getApplicationContext());
                long newID = dbHandler.registerUser(nic, name, email, mobileNumber,password);

                if (newID != -1) {
                    // If registration is successful, navigate to ViewProfile activity
                    Intent toViewProfile = new Intent(getApplicationContext(), Login.class);
                    Toast.makeText(CreateProfile.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    startActivity(toViewProfile);
                    Enter_name.setText(null);
                    Enter_email.setText(null);
                    Enter_mobileNumber.setText(null);
                    Enter_nic.setText(null);
                    Enter_password.setText(null);


                } else {
                    Toast.makeText(CreateProfile.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView linkLogin = (TextView) findViewById(R.id.linkLogin);

        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }


//    public void emailValidator(EditText etMail) {
//
//        // extract the entered data from the EditText
//        String emailToText = etMail.getText().toString();
//
//        // Android offers the inbuilt patterns which the entered
//        // data from the EditText field needs to be compared with
//        // In this case the entered data needs to compared with
//        // the EMAIL_ADDRESS, which is implemented same below
//        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
//            Toast.makeText(this, "Email Verified !", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Enter valid Email address !", Toast.LENGTH_SHORT).show();
//        }
//    }
}