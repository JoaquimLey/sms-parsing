package com.joaquimley.smsparsing;

import android.telephony.SmsManager;

/**
 * Constants helper class
 */

public class SmsHelper {

    public static final String SMS_CONDITION = "Some condition";

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return android.util.Patterns.PHONE.matcher(phoneNumber).matches();
    }

    public static void sendDebugSms(String number, String smsBody) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, smsBody, null, null);
    }
}
