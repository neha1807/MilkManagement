package myapp.com.project.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import myapp.com.project.Customer;
import myapp.com.project.R;
import myapp.com.project.model.Bill;

public class CustomerAdapter1 extends ArrayAdapter<Customer>{

    Context context;
    int resource;
    ArrayList<Customer> objects;
    Bill bill;

    public CustomerAdapter1(@NonNull Context context, int resource, @NonNull ArrayList<Customer> objects) {
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
        TextView textName = view.findViewById(R.id.textViewName);
        TextView textStatus=view.findViewById(R.id.textViewStatus);
        bill=new Bill();

String st=String.valueOf(bill.status);

        Customer customer = objects.get(position);
        imageView.setBackgroundResource(R.drawable.buyer);
        textName.setText(customer.name);
        textStatus.setText(st);



        return view;
    }
}
