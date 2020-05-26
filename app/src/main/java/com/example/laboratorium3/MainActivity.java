package com.example.laboratorium3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button b;
    DatabaseReference myRef;
    private TelephonyManager mTelephonyManager;
    private PhoneStateListenerExtended mListener;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("TAG", dataSnapshot.getValue(String.class));
                Toast.makeText(getApplicationContext(), "ostatnie polaczenie: " +
                        dataSnapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG", "failed to read value", databaseError.toException());
            }
        });

        myRef.setValue("Hello, World!");

        b = findViewById(R.id.call);
//        FirebaseCrash.log("log blad");
//        FirebaseCrash.logcat(Log.ERROR, "blad", "Przechwycenie bledu");
//        FirebaseCrash.report(new ArrayIndexOutOfBoundsException());
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
                if (isTelephonyEnabled()) {
                    checkForPhonePermission();
                    mListener = new PhoneStateListenerExtended();
                    mTelephonyManager.listen(mListener, PhoneStateListener.LISTEN_CALL_STATE);
                } else {
                    //....
                }
                if (dialIntent.resolveActivity(getPackageManager()) != null) {
                    checkForPhonePermission();
                    myRef.setValue("wyslanie");
                }
            }
        });

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "001");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "ZDARZENIE");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "TYP_ZDARZENIA");

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions[0].equalsIgnoreCase(Manifest.permission.CALL_PHONE) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {

        }
    }

    private boolean isTelephonyEnabled() {
        if (mTelephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
            return true;
        }
        return false;
    }

    private void checkForPhonePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);

        } else {

        }
    }

}
