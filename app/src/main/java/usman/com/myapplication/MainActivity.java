package usman.com.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private DatabaseHelper helper;

    private List<driver> studentList =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate( savedInstanceState );
       // setContentView( R.layout.activity_update_profile );
        helper = new DatabaseHelper( this );
        studentList=helper.getAllRecord();


        if (studentList.isEmpty()) //if driver is using app first time
        {
            Intent i = new Intent( this, SignupActivity.class );
            startActivity( i );

        }
        else // if driver is already registered
        {
            Intent i=new Intent(this,ViewProfileActivity.class);
            startActivity(i);
        }
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS};
        if (!EasyPermissions.hasPermissions( this, perms )) {
            EasyPermissions.requestPermissions( this, "we need permission for good results", 123, perms );
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        EasyPermissions.requestPermissions( this, "All permissions are required to run this application", requestCode, permissions );
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    Toast.makeText( this,"permission granted sucessfulyy",Toast.LENGTH_LONG ).show();


    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.somePermissionPermanentlyDenied( this,perms ))
        {
            new AppSettingsDialog.Builder( this ).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult( requestCode, resultCode, data );
        if(requestCode ==AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE)
        { }
    }


}


/*

    EditText etPhone, etMessage;
    Button btnSendSMS;
    SmsManager smsManager;
    private LocationManager locationManager;
    private double currentLatitude;
    private double currentLongitude;

    private final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    private final String SENT = "SMS_SENT";
    private final String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    private List<driver> studentList =new ArrayList<>();   // object list
        else // if driver is already registered
        {
            Intent i=new Intent(this,ViewProfileActivity.class);
            startActivity(i);

            Cursor cursor = helper.emergency();
            Toast.makeText( MainActivity.this, "para hua hai data ", Toast.LENGTH_LONG ).show();
            etPhone = (EditText) findViewById( R.id.etPhone );
            etMessage = (EditText) findViewById( R.id.etMessage );
            btnSendSMS = (Button) findViewById( R.id.btnSendSMS );
            sentPI = PendingIntent.getBroadcast( MainActivity.this, 0, new Intent( SENT ), 0 );
            deliveredPI = PendingIntent.getBroadcast( MainActivity.this, 0, new Intent( DELIVERED ), 0 );
            btnSendSMS.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String message = etMessage.getText().toString();
                    String telNr = etPhone.getText().toString();
                    if (ContextCompat.checkSelfPermission( MainActivity.this, Manifest.permission.SEND_SMS )
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions( MainActivity.this, new String[]{Manifest.permission.SEND_SMS},
                                MY_PERMISSIONS_REQUEST_SEND_SMS );
                    } else {
                        SmsManager sms = SmsManager.getDefault();
                        //phone - Recipient's phone number
                        //address - Service Center Address (null for default)
                        //message - SMS message to be sent
                        //piSent - Pending intent to be invoked when the message is sent
                        //piDelivered - Pending intent to be invoked when the message is delivered to the recipient
                        sms.sendTextMessage( telNr, null, message+"accident detected ", sentPI, deliveredPI );
                    }

                }
            } );



            //The deliveredPI PendingIntent does not fire in the Android emulator.
            //You have to test the application on a real device to view it.
            //However, the sentPI PendingIntent works on both, the emulator as well as on a real device.

            smsSentReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            Toast.makeText( context, "SMS sent successfully!", Toast.LENGTH_SHORT ).show();
                            break;

                        //Something went wrong and there's no way to tell what, why or how.
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            Toast.makeText( context, "Generic failure!", Toast.LENGTH_SHORT ).show();
                            break;

                        //Your device simply has no cell reception. You're probably in the middle of
                        //nowhere, somewhere inside, underground, or up in space.
                        //Certainly away from any cell phone tower.
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            Toast.makeText( context, "No service!", Toast.LENGTH_SHORT ).show();
                            break;

                        //Something went wrong in the SMS stack, while doing something with a protocol
                        //description unit (PDU) (most likely putting it together for transmission).
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            Toast.makeText( context, "Null PDU!", Toast.LENGTH_SHORT ).show();
                            break;

                        //You switched your device into airplane mode, which tells your device exactly
                        //"turn all radios off" (cell, wifi, Bluetooth, NFC, ...).
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            Toast.makeText( context, "Radio off!", Toast.LENGTH_SHORT ).show();
                            break;

                    }

                }
            };

            smsDeliveredReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            Toast.makeText( context, "SMS delivered!", Toast.LENGTH_SHORT ).show();
                            break;

                        case Activity.RESULT_CANCELED:
                            Toast.makeText( context, "SMS not delivered!", Toast.LENGTH_SHORT ).show();
                            break;
                    }

                }
            };

            //register the BroadCastReceivers to listen for a specific broadcast
            //if they "hear" that broadcast, it will activate their onReceive() method
            registerReceiver( smsSentReceiver, new IntentFilter( SENT ) );
            registerReceiver( smsDeliveredReceiver, new IntentFilter( DELIVERED ) );
        }*/






/*        Boolean isFirstRun = getSharedPreferences("preference",MODE_PRIVATE).getBoolean("isfirstrun",true);

        if(isFirstRun)
        {
            Toast.makeText(MainActivity.this,"first time run ",Toast.LENGTH_LONG).show();
            getSharedPreferences("PREFERENCE",MODE_PRIVATE).edit().putBoolean("isfirstrun",false).commit();
        }*/