package myapp.com.project;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import myapp.com.project.Adapter.CustomerAdapter;
import myapp.com.project.Adapter.UsersAdapter;
import myapp.com.project.Provider.MyContentProvider;
import myapp.com.project.model.Bill;
import myapp.com.project.model.User;

import static myapp.com.project.Provider.MyContentProvider.sqLiteDatabase;

public class AllCustomerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    Customer customer;
    ArrayList<Customer> customers;
    CustomerAdapter adapter;

    @BindView(R.id.listview)
    ListView listView;
    Intent rcv;


    ContentResolver resolver;
    int pos = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_customer);
        ButterKnife.bind(this);
        rcv = getIntent();

        resolver = getContentResolver();
        customers = new ArrayList<>();
        queryUsersFromDB();


    }

    void queryUsersFromDB() {
        String[] projection = {Util.COL_ID1, Util.COL_NAME, Util.COL_PHONE, Util.COL_ADDRESS, Util.COL_TYPE1, Util.COL_RATECUSTOMER, Util.COL_QUANTITYCUSTOMER};
        Cursor cursor = resolver.query(Util.USER_URI1, projection, null, null, null);
        if (cursor != null) {

            while (cursor.moveToNext()) {
                customer = new Customer();
                customer.id = cursor.getInt(cursor.getColumnIndex(Util.COL_ID1));
                customer.quantity = cursor.getString(cursor.getColumnIndex(Util.COL_QUANTITYCUSTOMER));
                customer.rate = cursor.getDouble(cursor.getColumnIndex(Util.COL_RATECUSTOMER));
                customer.name = cursor.getString(cursor.getColumnIndex(Util.COL_NAME));
                customer.phone = cursor.getString(cursor.getColumnIndex(Util.COL_PHONE));
                customer.address = cursor.getString(cursor.getColumnIndex(Util.COL_ADDRESS));
                customer.type = cursor.getString(cursor.getColumnIndex(Util.COL_TYPE1));

                customers.add(customer);

            }
            adapter = new CustomerAdapter(this, R.layout.customer_no, customers);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pos = position;
        customer = customers.get(position);

    }



}
