package usman.com.myapplication;
// fragment ,list if dialog fragment ,shade preference
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME ="DAD";
    private static final String TABLE_NAME="driver";
    private static final String ID="id";
    private static final String FNAME="fname";
    private static final String LNAME="lname";
    private static final String BLOOD="blood";
    private static final String NO1="no1";
    private static final String NO2="no2";
    private static final String NO3="no3";
    private static int DB_VERSION=1;

    private static final String CREATE_STUDENT_SCHEMA ="create table "+ TABLE_NAME +"(id Integer primary key autoincrement, fname text,lname text,blood text,no1 text,no2 text,no3 text)";
    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DB_VERSION);
    }
    //----------CRUD OPERATIONS-------//
    public int first()
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT id FROM driver ", null);
        if (!c.moveToFirst()) {
            return 1;
        }
        return 0;
    }
    public Cursor emergency()
    {

        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT no1,no2,no3 FROM driver ", null);
       // String data = cursor.getString(cursor.getColumnIndex("no1"));

        return cursor;
    }

    public void insertData(driver driver, Context context)
    {
        ContentValues values= new ContentValues();
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase(); // writeable form it will read data
        long result=0;
        try
        {
           values.put(FNAME,driver.getFname());
            values.put(LNAME,driver.getLname());
            values.put(BLOOD,driver.getBlood());
            values.put(NO1,driver.getNo1());
           values.put(NO2,driver.getNo2());
           values.put(NO3,driver.getNo3());

           result =sqLiteDatabase.insert(TABLE_NAME,null,values);
           if(result>0)
           {
               Toast.makeText(context,"record inserted ", Toast.LENGTH_SHORT).show();

           }
           else
               {
                   Toast.makeText(context, "insertion failed ", Toast.LENGTH_SHORT).show();
           }
        }
        catch (SQLException e)
        {
            Toast.makeText(context, "sqlite Error!", Toast.LENGTH_SHORT).show();
        }
        finally
        {
          sqLiteDatabase.close();
        }

    }
    public List<driver> getAllRecord()
    {
        List<driver> driverList =new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();

        Cursor cursor=null;

        try {
            cursor=sqLiteDatabase.rawQuery("select *from driver",null);

            if (cursor!=null)
            {
                if(cursor.moveToFirst())
                {
                    do {
                        driverList.add(new driver(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)));


                    }
                    while (cursor.moveToNext());

                }
            }
        }
        catch (SQLException e)
        {

        }
        finally {
            sqLiteDatabase.close();
            if(cursor!=null)
            cursor.close();
        }
        return driverList;
    }
    public boolean deleteRecordByID(int id )
    {
        boolean isDeleted =false;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=null;
        int getID=-1;

        try
        {
            cursor =sqLiteDatabase.rawQuery("select *from student where id=?",new String[]{String.valueOf(id)});
            if(cursor!=null)
            {
                if(cursor.moveToFirst())
                {
                    do {
                        getID=cursor.getInt(0);
                        if(getID==id)
                        {
                            sqLiteDatabase.delete(TABLE_NAME,"id=?",new String[]{String.valueOf(id)});
                            isDeleted=true;
                            break;

                        }
                        else {
                            isDeleted=false;
                        }
                    }
                    while (cursor.moveToNext());
                }
            }
        }
        catch (SQLException e)
        {

        }
        finally {
            sqLiteDatabase.close();
            if (cursor!=null)
            {
                cursor.close();;
            }
        }
        return isDeleted;
    }

    public List<driver> fetchData(int id)

    {



        List<driver> driverList =new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();

        Cursor cursor=null;

        try {
            cursor=sqLiteDatabase.rawQuery("select *from driver",null);
            //cursor=sqLiteDatabase.rawQuery("select *from driver WHERE id = ?",new String[]{String.valueOf(id)});
            if (cursor!=null)
            {
                if(cursor.moveToFirst())
                {
                    do {
                        driverList.add(new driver(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)));

                    }
                    while (cursor.moveToNext());

                }
            }
        }
        catch (SQLException e)
        {

        }
        finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
        return driverList;
    }
    public boolean UpdateRecordByID(int id, String fname, String lname, String blood, String no1, String no2, String no3 )
    {
        boolean isUpdated =false;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=null;
        int getID=-1;
        ContentValues contentValues=new ContentValues();
        contentValues.put(FNAME,fname);
        contentValues.put(LNAME,lname);
        contentValues.put(BLOOD,blood);
        contentValues.put(NO1,no1);
        contentValues.put(NO2,no2);
        contentValues.put(NO3,no3);


        try
        {
             cursor = sqLiteDatabase.rawQuery("SELECT * FROM driver ", null);
            //cursor =sqLiteDatabase.rawQuery("select *from driver where id=?",new String[]{String.valueOf(id)});
            if(cursor!=null)
            {
                if(cursor.moveToFirst())
                {
                    do {

                        getID=cursor.getInt(0);
                        if(getID==1)
                        {


                            sqLiteDatabase.update(TABLE_NAME,contentValues,"id=?",new String[]{String.valueOf(id)});
                            isUpdated=true;
                            break;

                        }
                        else {
                            isUpdated=false;
                        }
                    }
                    while (cursor.moveToNext());
                }
            }
        }
        catch (SQLException e)
        {

        }
        finally {
            sqLiteDatabase.close();
            if (cursor!=null)
            {
                cursor.close();;
            }
        }
        return isUpdated;
    }

    //----------END CRUD Operations-------------//
    @Override
    public void onCreate(SQLiteDatabase db)
    {//execute scema
      db.execSQL(CREATE_STUDENT_SCHEMA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    { //for new change in database
       onCreate(db);
       db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }
}
