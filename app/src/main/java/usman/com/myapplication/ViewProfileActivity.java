package usman.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewProfileActivity extends AppCompatActivity {
    private EditText firstname,lastname,blood,eno1,eno2,emo3;
    DatabaseHelper helper;
    private Button update,sendNotification;
    private List<driver> studentList =new ArrayList<>();

    private LocationManager locationManager;
    SmsManager smsManager;
    private String no1,no2,no3;
    boolean isUpdated;

   // helper = new DatabaseHelper(getApplicationContext());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_view_profile );
        firstname=findViewById( R.id.edt_firstname );
        lastname=findViewById( R.id.edt_lastname );
        blood=findViewById( R.id.edt_blood );
        eno1=findViewById( R.id.edt_eno1 );
        eno2=findViewById( R.id.edt_eno2 );
        emo3=findViewById( R.id.edt_eno3 );
        update=findViewById( R.id.btn_update );
        sendNotification=findViewById(R.id.btn_sendnotification);


        helper = new DatabaseHelper(getApplicationContext());
        List<driver> list = helper.fetchData(Integer.parseInt("1"));  //fetching record from data base

        for (driver s: list)                                     //displaying the result in text field
        {
            firstname.setText(s.getFname());
            lastname.setText(s.getLname());
            blood.setText(s.getBlood());
            eno1.setText(s.getNo1());
            eno2.setText(s.getNo2());
            emo3.setText(s.getNo3());
        }
        update.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper=new DatabaseHelper(getApplicationContext());
                isUpdated=helper.UpdateRecordByID(Integer.parseInt("1"),firstname.getText().toString(),lastname.getText().toString(),blood.getText().toString(),eno1.getText().toString(),eno2.getText().toString(),emo3.getText().toString());

                if(isUpdated)
                {
                    Toast.makeText(ViewProfileActivity.this, "updated", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ViewProfileActivity.this, "id not found", Toast.LENGTH_SHORT).show();
                }
            }
        } );
        sendNotification.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotificaton();
            }
        } );

    }
    protected void sendNotificaton()
    {
        helper = new DatabaseHelper( this );
     //  studentList=helper.getAllRecord();
        List<driver> list = helper.getAllRecord();



        for (driver s: list)
        {
            no1= s.getNo1();
            no2= s.getNo2();
            no3= s.getNo3();

        }
      //  SystemClock.sleep( 5000 );

       // Toast.makeText( this,no1 +"dfdf"+no2+"asdasd"+no3,Toast.LENGTH_LONG ).show();

        locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if (ActivityCompat.checkSelfPermission(ViewProfileActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation( locationManager.NETWORK_PROVIDER );
        double longitude=location.getLongitude();
        double latitude=location.getLatitude();


        try {
            smsManager = SmsManager.getDefault();

            smsManager.sendTextMessage(no1, "Number","Accident detected at location http://maps.google.com/maps?q=loc:"+ String.format("%f,%f", location.getLatitude() , location.getLongitude() )  , null, null);
            smsManager.sendTextMessage(no2, "Number","Accident detected at location http://maps.google.com/maps?q=loc:"+ String.format("%f,%f", location.getLatitude() , location.getLongitude() )  , null, null);
            smsManager.sendTextMessage(no3, "Number","Accident detected at location http://maps.google.com/maps?q=loc:"+ String.format("%f,%f", location.getLatitude() , location.getLongitude() )  , null, null);

              Toast.makeText(this,"SMS SEND", Toast.LENGTH_SHORT).show();
         //   Toast.makeText( this,no1 +"dfdf"+no2+"asdasd"+no3,Toast.LENGTH_LONG ).show();

        }catch(Exception e){
            Toast.makeText(this, "SMS FAILED", Toast.LENGTH_SHORT).show();
        }
    }
}
