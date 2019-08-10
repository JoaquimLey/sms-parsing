package com.joaquimley.smsparsing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * A broadcast receiver who listens for incoming SMS
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsBroadcastReceiver";

    @Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
			String smsSender = "";
			String smsBody = "";
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                smsSender = smsMessage.getDisplayOriginatingAddress();
				for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
					smsBody += smsMessage.getMessageBody();
				}
			} else {
				Bundle smsBundle = intent.getExtras();
				if (smsBundle != null) {
					Object[] pdus = (Object[]) smsBundle.get("pdus");
					if (pdus == null) {
						// Display some error to the user
						Log.e(TAG, "SmsBundle had no pdus key");
						return;
					}
					SmsMessage[] messages = new SmsMessage[pdus.length];
					for (int i = 0; i < messages.length; i++) {
						messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
						smsBody += messages[i].getMessageBody();
					}
					smsSender = messages[0].getOriginatingAddress();
				}
			}

			if (smsSender.equals(SmsHelper.SERVICE_PROVIDER) && smsBody.startsWith(SmsHelper.SMS_CONDITION)) {
				if (listener != null) {
					listener.onTextReceived(smsBody);
				}
			}
		}
	}
}
