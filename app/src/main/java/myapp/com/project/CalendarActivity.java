package myapp.com.project;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DateTimeKeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarCellView;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.ButterKnife;
import butterknife.BindView;
import myapp.com.project.Adapter.UsersAdapter;
import myapp.com.project.Provider.MyContentProvider;
import myapp.com.project.model.User;


public class CalendarActivity extends AppCompatActivity implements View.OnClickListener,CalendarCellDecorator {
    @BindView(R.id.lowerFrame)
FrameLayout frameLayout;

    @BindView(R.id.calendar)
    CalendarPickerView calendarPickerView;

    @BindView(R.id.textqua)
     EditText txtquantity;

    @BindView(R.id.textrate)
    EditText txtrate;

    ContentResolver resolver;
    @BindView(R.id.buttonupdate)
    Button btn;


    @BindView(R.id.textViewquestion)
            TextView txtquestion;

    User user;
    ArrayList<User> users;
    String strDate,strdate1,where;
    Intent rcv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        resolver=getContentResolver();
        btn.setOnClickListener(this);
         rcv=getIntent();
        users=new ArrayList<>();
        user = (User) rcv.getSerializableExtra("keyuser");



       Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR,2);


       Calendar lastYear= Calendar.getInstance();
       lastYear.add(Calendar.YEAR, -2);
        Date today = new Date();
        calendarPickerView.init(lastYear.getTime(), nextYear.getTime()).withSelectedDate(today).inMode(CalendarPickerView.SelectionMode.RANGE);
        List<CalendarCellDecorator> decoratorList = new ArrayList<>();
        decoratorList.add(new CalendarActivity());
        calendarPickerView.setDecorators(decoratorList);
        calendarPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {

                queryUsersFromDB();
                strDate=formatDate(date);


                }

            @Override
            public void onDateUnselected(Date date) {

                }
        });

    }


    private String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return simpleDateFormat.format(date);
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(CalendarActivity.this, AllUsersActivity.class);
        startActivity(intent);
        }



   /* private ArrayList<Date> Highlight(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String dateInString = user.date;
        Date date=null ;
        try {

            date = sdf.parse(dateInString);

        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Date> holidays = new ArrayList<>();
        holidays.add(date);
        return holidays;
    }*/
    void queryUsersFromDB(){
        String[] projection={Util.COL_QUANTITY,Util.COL_RATE,Util.COL_DATE,Util.COL_TYPE};
         where = Util.COL_DATE + "='" + strDate +"'";


       Cursor cursor=resolver.query(Util.USER_URI,projection,where,null,null);



        if(cursor!=null){

            while(cursor.moveToNext()){
                User user=new User();
                user.quantity=cursor.getDouble(cursor.getColumnIndex(Util.COL_QUANTITY));
                user.rate=cursor.getDouble(cursor.getColumnIndex(Util.COL_RATE));
                user.date=cursor.getString(cursor.getColumnIndex(Util.COL_DATE));
                user.type=cursor.getString(cursor.getColumnIndex(Util.COL_TYPE));

            }
            txtquantity.setText("Quantity: "+String.valueOf(user.quantity));
            txtrate.setText("Rate: "+String.valueOf(user.quantity * user.rate));
        }

    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing App")
                .setMessage("Are you sure you want to close this app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void decorate(CalendarCellView cellView, Date date) {

            if (date.getDate()<5) {
                cellView.setBackgroundResource(R.drawable.red_background);
            } else {
                cellView.setBackgroundResource(R.drawable.blue);
            }

    }

}