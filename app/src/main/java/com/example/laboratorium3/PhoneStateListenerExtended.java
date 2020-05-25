package com.example.laboratorium3;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class PhoneStateListenerExtended extends PhoneStateListener {
    @Override
    public void onCallStateChanged(int state, String phoneNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                //...
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //...
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                //...
                break;
            default:
                //...
                break;
        }
    }
}
