package myapp.com.project.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import myapp.com.project.Customer;
import myapp.com.project.R;
import myapp.com.project.model.Bill;
import myapp.com.project.model.User;


public class CustomerAdapter extends ArrayAdapter<Customer> implements View.OnClickListener {

    Context context;
    int resource;
    ArrayList<Customer> objects;

    Switch switchbtn;
    Customer customer;





    public CustomerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Customer> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View view = LayoutInflater.from(context).inflate(resource, parent, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textname = view.findViewById(R.id.textViewname);
        TextView textphone = view.findViewById(R.id.textViewphone);
        customer=new Customer();
         switchbtn=view.findViewById(R.id.switch1);
         switchbtn.setOnClickListener(this);


        Customer customer = objects.get(position);

        imageView.setBackgroundResource(R.drawable.buyer);

        textname.setText(customer.name);
        textphone.setText(customer.phone);


        return view;
    }


    @Override
    public void onClick(View v) {

        if (switchbtn.isChecked()){
            customer.status=1;
            Toast.makeText(context,"customer activated",Toast.LENGTH_SHORT).show();
        }
        else
        {
            customer.status=0;
            Toast.makeText(context,"customer deactivated",Toast.LENGTH_SHORT).show();
        }




    }

}


