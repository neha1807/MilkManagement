package myapp.com.project;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import myapp.com.project.model.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

        @BindView(R.id.editTextquantity)
        EditText etxtquantity;

        @BindView(R.id.editTextrate)
        EditText etxtrate;
        @BindView(R.id.editTextdate)
        EditText etxtdate;

        @BindView(R.id.buttonproceed)
         Button btnproceed;

        @BindView(R.id.radioButtoncow)
        RadioButton cow;

        @BindView(R.id.radiogroup)
        RadioGroup myradiogroup;

        @BindView(R.id.radioButtonbuffalo)
         RadioButton buffalo;
         User user;
         boolean updateMode,checked;

         Intent rcv;

         ContentResolver resolver;

         Calendar calendar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Monthly Milk Calendar");
        ButterKnife.bind(this);
        calendar=Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        etxtdate.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            new DatePickerDialog(MainActivity.this, date, calendar
                                                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                                    calendar.get(Calendar.DAY_OF_MONTH)).show();


                                        }
                                    });
        user = new User();
        resolver = getContentResolver();
        btnproceed.setOnClickListener(this);
        overridePendingTransition(R.anim.translate_right, R.anim.translate_left);
        rcv = getIntent();
        updateMode = rcv.hasExtra("keyuser");

        if (updateMode) {
            user = (User) rcv.getSerializableExtra("keyuser");
            etxtquantity.setText(String.valueOf(user.quantity));
            etxtrate.setText(String.valueOf(user.rate));
            etxtdate.setText(user.date);


            btnproceed.setText("UPDATE");
            getSupportActionBar().setTitle("UPDATE DATA");
        }
    }


        boolean validateFields(){
        boolean flag = true;

        if(String.valueOf(user.quantity).isEmpty()) {
            flag = false;
        }

        if(String.valueOf(user.rate).isEmpty()) {
            flag = false;
        }
        return flag;
    }

    void clearFields(){

        etxtquantity.setText("");
        etxtrate.setText("");
        etxtdate.setText("");
    }
    private void updateLabel() {
        String myFormat = "dd.MM.yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etxtdate.setText(sdf.format(calendar.getTime()));


    }

    @Override
    public void onClick(View view) {
        try {
            user.quantity = Double.parseDouble(etxtquantity.getText().toString());
            user.rate = Double.parseDouble(etxtrate.getText().toString());
        }
        catch (NumberFormatException e){
           Toast.makeText(this,"please enter valid details",Toast.LENGTH_SHORT).show();
        }
        user.date = etxtdate.getText().toString();
        if (cow.isChecked()) {

            user.type = cow.getText().toString();
        }
        if (buffalo.isChecked()) {

            user.type = buffalo.getText().toString();
        }

        if (validateFields()) {

                insertUserInDB();

            } else {
                Toast.makeText(this, "Please Enter Correct Details First !!", Toast.LENGTH_LONG).show();
            }
        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
        intent.putExtra("keyuser", user);
        startActivity(intent);


        }


    void insertUserInDB(){
        SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");
        String strdate=sdf.format(new Date());

        ContentValues values=new ContentValues();
        values.put(Util.COL_QUANTITY,user.quantity);
        values.put(Util.COL_RATE,user.rate * user.quantity);
        values.put(Util.COL_DATE,strdate);
        values.put(Util.COL_TYPE,user.type);

       if(!updateMode) {
             Uri uri = resolver.insert(Util.USER_URI, values);

            Toast.makeText(this, user.date + " inserted successfully with id  " + uri.getLastPathSegment(), Toast.LENGTH_LONG).show();
            clearFields();
       }
       else{
            String where=Util.COL_ID+" = "+user.id;
            int i=resolver.update(Util.USER_URI,values,where,null);
            if(i>0){
                Toast.makeText(this,user.date+" is updated with "+user.id,Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,user.date+"is not updated",Toast.LENGTH_LONG).show();
            }

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_user,menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if(id == R.id.allusers){
            Intent intent=new Intent(MainActivity.this,AllUsersActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}


