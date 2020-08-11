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

public class ResetPassword extends AppCompatActivity {
    EditText newpasswordEd, confirmpasswordEd;
    String newpassStr, confirmpassStr;
    Button resetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        newpasswordEd = findViewById(R.id.newpasswordEd);
        confirmpasswordEd = findViewById(R.id.confirmpasswordEd);
        resetBtn = findViewById(R.id.resetBtn);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newpassStr = newpasswordEd.getText().toString().trim();
                confirmpassStr = confirmpasswordEd.getText().toString().trim();

                if (newpassStr.length()<1){
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    newpasswordEd.requestFocus();
                    newpasswordEd.setError("Please enter your password",customErrorDrawable);
                }
                else if (confirmpasswordEd.length()<1 ) {
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    confirmpasswordEd.requestFocus();
                    confirmpasswordEd.setError("Please verify your password",customErrorDrawable);

                }
                else if (!confirmpassStr.matches(newpassStr)){
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    confirmpasswordEd.requestFocus();
                    confirmpasswordEd.setError("Password is not matched",customErrorDrawable);
                }

                else {
                    Intent i = new Intent(ResetPassword.this, LoginActivity.class);
                    //  RegPrefManager.getInstance(MainActivity.this).logout();
                    startActivity(i);
                    finish();
                }
            }
        });

    }
}
