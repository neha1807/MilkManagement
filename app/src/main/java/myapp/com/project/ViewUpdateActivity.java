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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import myapp.com.project.Adapter.CustomerAdapter;
import myapp.com.project.Adapter.CustomerAdapter1;
import myapp.com.project.Provider.MyContentProvider;

public class ViewUpdateActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Customer customer;
    ArrayList<Customer> customers;
    CustomerAdapter1 adapter;

    @BindView(R.id.listview1)
    ListView listView;
    Intent rcv;


    ContentResolver resolver;
    int pos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_update);
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
            adapter = new CustomerAdapter1(this, R.layout.view_customer, customers);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }

    }

    void showDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Details of " + customer.name);
        Dialog d = new Dialog(this);
        builder.setMessage(customer.toString());
        builder.setPositiveButton("DONE", null);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));


    }

    void deleteUserFromDB() {

        String where = Util.COL_ID1 + " = " + customer.id;
        int i = resolver.delete(Util.USER_URI1, where, null);
        Toast.makeText(this, customer.name + " deleted..." + i, Toast.LENGTH_LONG).show();

        customers.remove(pos);
        adapter.notifyDataSetChanged();

    }

    void askForDeletion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Customer: " + customer.name);
        builder.setMessage("Are you Sure ?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUserFromDB();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }


void showdialog(){
    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
    builder1.setTitle("Bill " + customer.name);

    builder1.setMessage(customer.name+" bill for today is "+customer.rate);

    AlertDialog dialog1 = builder1.create();
    dialog1.show();
    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));



}


    void showOptions() {

        String[] items = {"View", "Update", "Delete", "Cancel", "bill"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i) {
                    case 0:
                        showDetails();
                        break;

                    case 1:
                        Intent intent = new Intent(ViewUpdateActivity.this, AddCustomerActivity.class);
                        intent.putExtra("keycustomer", customer);
                        startActivity(intent);
                        break;

                    case 2:
                        askForDeletion();
                        break;


                    case 3:
                        finish();
                        break;
                    case 4:showdialog();
                    break;


                }

            }
        });
        builder.create().show();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pos = position;
        customer = customers.get(position);
         showOptions();
    }



}



