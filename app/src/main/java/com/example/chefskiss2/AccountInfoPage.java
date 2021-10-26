package com.example.chefskiss2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AccountInfoPage extends AppCompatActivity {

    private static final String TAG = "AccountInfoPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info_page);
        Log.d(TAG, "onCreate: Starting.");

        Button backhomebtn = (Button) findViewById(R.id.buttontohomepage);

        backhomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked backtohomebtn.");

                Intent intent = new Intent(AccountInfoPage.this, Homepage.class);
                startActivity(intent);
            }
        });
        Button editaccountbtn = (Button) findViewById(R.id.buttontoedit);

        editaccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked editaccountbtn.");

            }
        });
    }
}