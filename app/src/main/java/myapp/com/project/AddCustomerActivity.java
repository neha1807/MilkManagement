package myapp.com.project;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

public class AddCustomerActivity extends AppCompatActivity {
    EditText etxtname,etxtphone,etxtaddress,etxtrate;
    Spinner spinner;
    Button btnsubmit;
    ArrayAdapter<String> adapter;
    Customer customer;


    RadioButton buffalo,cow;
    Intent rcv;
    boolean updateMode;
    ContentResolver resolver;
    void initviews(){
        etxtname=findViewById(R.id.editTextname);
        etxtphone=findViewById(R.id.editTextphone);
        etxtaddress=findViewById(R.id.editTextaddress);
        etxtrate=findViewById(R.id.editTextrate);
        btnsubmit=findViewById(R.id.button);
        cow=findViewById(R.id.radioButtoncow);
        buffalo=findViewById(R.id.radioButtonbuffalo);
    }
    void initspinner(){
        spinner=findViewById(R.id.spinner);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item);
        adapter.add("--Select Quantity--");
        adapter.add("1");
        adapter.add("1.5");
        adapter.add("2");
        adapter.add("2.5");
        adapter.add("3");
        adapter.add("3.5");
        adapter.add("4");
        adapter.add("4.5");
        adapter.add("5");
        adapter.add("5.5");
        adapter.add("6");
        adapter.add("6.5");
        adapter.add("7");
        adapter.add("7.5");
        adapter.add("8");
        adapter.add("8.5");
        adapter.add("9");
        adapter.add("9.5");
        adapter.add("10");
        adapter.add("10.5");
        adapter.add("11");
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String qty = adapter.getItem(position);

                if(qty!=null){
                    customer.quantity=qty.toString();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    boolean validateFields(){
        boolean flag = true;

        if(customer.phone.length()!=10) {
            Toast.makeText(AddCustomerActivity.this,"please enter valid phone number",Toast.LENGTH_LONG).show();
            flag = false;
        }

        if(customer.name.isEmpty()) {
            flag = false;
        }
if(String.valueOf(customer.rate).isEmpty()){
            flag=false;
}

        if(customer.address.isEmpty()) {
            flag = false;

        }


        return flag;
    }
    void insertCustomerindb() {

        ContentValues values = new ContentValues();
        values.put(Util.COL_NAME, customer.name);
        values.put(Util.COL_PHONE, customer.phone);
        values.put(Util.COL_ADDRESS, customer.address);
        values.put(Util.COL_QUANTITYCUSTOMER, customer.quantity);
        values.put(Util.COL_RATECUSTOMER, customer.rate);
        values.put(Util.COL_TYPE1,customer.type);


        if (!updateMode) {
            Uri uri = resolver.insert(Util.USER_URI1, values);

            Toast.makeText(this, customer.name + " added successfully   ", Toast.LENGTH_LONG).show();
            clearFields();

        } else {
            String where = Util.COL_ID1 + " = " + customer.id;
            int i = resolver.update(Util.USER_URI1, values, where, null);
            if (i > 0) {
                Toast.makeText(this, customer.name + " is updated  ", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, customer.name + "is not updated", Toast.LENGTH_LONG).show();
            }
        }
    }
void clearFields(){
        etxtaddress.setText("");
        etxtphone.setText("");
        etxtname.setText("");
        etxtrate.setText("");
        etxtname.setText("");
}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        getSupportActionBar().setTitle("ADD CUSTOMER");
        initviews();
        initspinner();
        customer=new Customer();
       resolver=getContentResolver();
       rcv=getIntent();

       updateMode=rcv.hasExtra("keycustomer");
        if (updateMode) {
            customer = (Customer) rcv.getSerializableExtra("keycustomer");
            etxtname.setText(String.valueOf(customer.name));
            etxtrate.setText(String.valueOf(customer.rate));
            etxtphone.setText(customer.phone);
            etxtaddress.setText(customer.address);

            btnsubmit.setText("UPDATE");
            getSupportActionBar().setTitle("UPDATE DATA");
        }
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("keyId",customer.id);
                customer.name = etxtname.getText().toString();
                customer.phone = etxtphone.getText().toString();
                customer.address = etxtaddress.getText().toString();
                try {


                    customer.rate = Double.parseDouble(etxtrate.getText().toString().trim());
                }
                catch (NumberFormatException e){
                    customer.rate=0;
                }
                if (cow.isChecked()) {

                    customer.type = cow.getText().toString();
                }
                if (buffalo.isChecked()) {

                    customer.type = buffalo.getText().toString();
                }
                if (validateFields()) {
                    insertCustomerindb();
                    Toast.makeText(AddCustomerActivity.this, "Customer added Successfully", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AddCustomerActivity.this, "please enter correct details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
