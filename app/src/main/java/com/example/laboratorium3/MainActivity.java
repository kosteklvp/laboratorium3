package com.example.laboratorium3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button b;
    private TelephonyManager mTelephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = findViewById(R.id.call);

        mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        b.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_CALL);
                dialIntent.setData(Uri.parse("794947690"));
                if (dialIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(dialIntent);
                } else {
                    Log.e("ERROR", "brak");
                }
            }
        });
    }

    private boolean isTelephonyEnabled() {
        if (mTelephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
            return true;
        }
        return false;
    }

    


}
