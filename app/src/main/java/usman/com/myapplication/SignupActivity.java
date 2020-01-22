package usman.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SignupActivity extends AppCompatActivity {
    private EditText edt_firstname,edt_Lastname,edt_blood,edt_no1,edt_no2,edt_no3;
    private Button btn_save,btn_cont;
    private LinearLayout num1,num2;
    private DatabaseHelper helper;
    private int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_signup);
        edt_firstname = findViewById( R.id.edt_firstname );
        edt_Lastname = findViewById( R.id.edt_lastname );
        edt_blood = findViewById( R.id.edt_blood );
        edt_no1 = findViewById( R.id.edt_no1 );
        edt_no2 = findViewById( R.id.edt_no2 );
        edt_no3 = findViewById( R.id.edt_no3 );
        btn_save = findViewById( R.id.btn_signup );
        btn_cont= findViewById( R.id.btn_contact );
        num1=findViewById( R.id.num1 );
        num2=findViewById( R.id.num2 );
        helper = new DatabaseHelper( this );
        // lay.setVisibility(View.VISIBLE );

        btn_cont.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i==0){
                    num1.setVisibility( View.VISIBLE );
                    i++;

                }
                else
                {
                    num2.setVisibility( View.VISIBLE );
                    i++;
                }

            }
        } );
        btn_save.setOnClickListener( new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                helper = new DatabaseHelper( getApplicationContext() );
                helper.insertData( new driver( edt_firstname.getText().toString(), edt_Lastname.getText().toString(), edt_blood.getText().toString(),
                        edt_no1.getText().toString(), edt_no2.getText().toString(), edt_no3.getText().toString() ), getApplicationContext() );
                Intent ii=new Intent(SignupActivity.this,ViewProfileActivity.class);
                startActivity(ii);

            }
        } );

    }
}
