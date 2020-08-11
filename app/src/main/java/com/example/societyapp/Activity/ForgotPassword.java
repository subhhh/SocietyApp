package com.example.societyapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.societyapp.R;

public class ForgotPassword extends AppCompatActivity {
    EditText phoneEd;
    Button submitBtn;
    String MobilePattern = "[0-9]{10}";
    String userPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        phoneEd = findViewById(R.id.phoneEd);
        submitBtn = findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPhone = phoneEd.getText().toString().trim();

                if (userPhone.length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "Please enter your phone number", Toast.LENGTH_SHORT).show();
                }

                else if (!phoneEd.getText().toString().matches(MobilePattern)){
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    phoneEd.requestFocus();
                    phoneEd.setError("Please enter a valid phone number",customErrorDrawable);
                }
                else {
                  //  startActivity(new Intent(ForgotPassword.this,MainActivity.class));
                }
            }
        });
    }
}
