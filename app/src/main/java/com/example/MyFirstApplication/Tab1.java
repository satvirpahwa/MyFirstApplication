package com.example.MyFirstApplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class Tab1 extends Activity implements OnClickListener {

    // Widget GUI
    Button btnCalendar, btnTimePicker, btnSubmit;

    // Variable for storing current date and time
    private int mYear, mMonth, mDay, mHour, mMinute;

    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tab1);

            btnCalendar = (Button) findViewById(R.id.buttondate);
            btnTimePicker = (Button) findViewById(R.id.buttontime);
            btnSubmit = (Button) findViewById(R.id.buttonsubmit);

            btnCalendar.setOnClickListener(this);
            btnTimePicker.setOnClickListener(this);
            btnSubmit.setOnClickListener(this);
        }
private void testfunction(){
    int i = 1+1;
}
    @Override
    public void onClick(View v) {

        if (v == btnCalendar) {

            // Process to get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
                            btnCalendar.setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            dpd.show();
        }
        if (v == btnTimePicker) {

            // Process to get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            // Display Selected time in textbox
                            btnTimePicker.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            tpd.show();
        }
        if (v == btnSubmit) {
            sendSMS();
            Toast.makeText(getApplicationContext(), "Submit button clicked",
                    Toast.LENGTH_LONG).show();
        }
    }
        public void sendSMS ()
        {
            String SENT = "SMS_SENT";
            String DELIVERED = "SMS_DELIVERED";

            PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
            PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

            // ---when the SMS has been sent---
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context arg0, Intent arg1) {

                    switch (getResultCode()) {

                        case Activity.RESULT_OK:

                            Toast.makeText(getBaseContext(), "SMS sent",
                                    Toast.LENGTH_SHORT).show();
                            break;

                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:

                            Toast.makeText(getBaseContext(), "Generic failure",
                                    Toast.LENGTH_SHORT).show();
                            break;

                        case SmsManager.RESULT_ERROR_NO_SERVICE:

                            Toast.makeText(getBaseContext(), "No service",
                                    Toast.LENGTH_SHORT).show();
                            break;

                        case SmsManager.RESULT_ERROR_NULL_PDU:

                            Toast.makeText(getBaseContext(), "Null PDU",
                                    Toast.LENGTH_SHORT).show();
                            break;

                        case SmsManager.RESULT_ERROR_RADIO_OFF:

                            Toast.makeText(getBaseContext(), "Radio off",
                                    Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }, new IntentFilter(SENT));

            // ---when the SMS has been delivered---
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context arg0, Intent arg1) {

                    switch (getResultCode()) {

                        case Activity.RESULT_OK:

                            Toast.makeText(getBaseContext(), "SMS delivered",
                                    Toast.LENGTH_SHORT).show();
                            break;

                        case Activity.RESULT_CANCELED:

                            Toast.makeText(getBaseContext(), "SMS not delivered",
                                    Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }, new IntentFilter(DELIVERED));

            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage("9999083252", null, "Test message", null, null);
        }

}

