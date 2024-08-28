package com.example.roadrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogoutActivity extends AppCompatActivity {

    Button buttonUpdateProfile, buttonCancelUpdateProfile;
    TextView textViewEmail;
    EditText editTextName, editTextBirth, editTextPhoneNumber, editTextAddress;
    String defaultEmail = "dunglelass@gmail.com";
    String currentName, currentBirth, currentPhoneNumber, currentAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        // UI elements
        buttonUpdateProfile = findViewById(R.id.btn_update_profile);
        buttonCancelUpdateProfile = findViewById(R.id.btn_cancel_update_profile);
        textViewEmail = findViewById(R.id.edt_email_profile);
        editTextName = findViewById(R.id.edt_name_profile);
        editTextBirth = findViewById(R.id.edt_birth_profile);
        editTextPhoneNumber = findViewById(R.id.edt_phonenumber_profile);
        editTextAddress = findViewById(R.id.edt_address_profile);

        // Set default email
        textViewEmail.setText(defaultEmail);

        // Fetch current user profile info
        fetchUserProfile();

        // Update profile button listener
        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonUpdateProfile.getText().toString().equals("Change")) {
                    // Enable editing
                    editTextName.setEnabled(true);
                    editTextBirth.setEnabled(true);
                    editTextPhoneNumber.setEnabled(true);
                    editTextAddress.setEnabled(true);

                    // Show cancel button
                    buttonCancelUpdateProfile.setVisibility(View.VISIBLE);

                    // Change button text to 'Save'
                    buttonUpdateProfile.setText("Save");

                    // Save current values for cancel functionality
                    currentName = editTextName.getText().toString();
                    currentBirth = editTextBirth.getText().toString();
                    currentPhoneNumber = editTextPhoneNumber.getText().toString();
                    currentAddress = editTextAddress.getText().toString();
                } else if (buttonUpdateProfile.getText().toString().equals("Save")) {
                    // Save changes
                    saveChanges();

                    // Disable editing
                    editTextName.setEnabled(false);
                    editTextBirth.setEnabled(false);
                    editTextPhoneNumber.setEnabled(false);
                    editTextAddress.setEnabled(false);

                    // Hide cancel button
                    buttonCancelUpdateProfile.setVisibility(View.GONE);

                    // Change button text back to 'Change'
                    buttonUpdateProfile.setText("Change");

                    Toast.makeText(LogoutActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Cancel update profile button listener
        buttonCancelUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Disable editing
                editTextName.setEnabled(false);
                editTextBirth.setEnabled(false);
                editTextPhoneNumber.setEnabled(false);
                editTextAddress.setEnabled(false);

                // Hide cancel button
                buttonCancelUpdateProfile.setVisibility(View.GONE);

                // Change button text back to 'Change'
                buttonUpdateProfile.setText("Change");

                // Reset fields to previous values
                editTextName.setText(currentName);
                editTextBirth.setText(currentBirth);
                editTextPhoneNumber.setText(currentPhoneNumber);
                editTextAddress.setText(currentAddress);
            }
        });
    }

    // Method to fetch user profile details (mock implementation)
    private void fetchUserProfile() {
        // Mock implementation to set initial values
        currentName = "Anh Dung";
        currentBirth = "01/01/2002";
        currentPhoneNumber = "1234567890";
        currentAddress = "316 Nguyen Trai, Ha Noi";

        // Display current values
        editTextName.setText(currentName);
        editTextBirth.setText(currentBirth);
        editTextPhoneNumber.setText(currentPhoneNumber);
        editTextAddress.setText(currentAddress);
    }

    // Method to save changes
    private void saveChanges() {
        // Perform save/update operation (mock implementation)
        currentName = editTextName.getText().toString();
        currentBirth = editTextBirth.getText().toString();
        currentPhoneNumber = editTextPhoneNumber.getText().toString();
        currentAddress = editTextAddress.getText().toString();
    }
}
