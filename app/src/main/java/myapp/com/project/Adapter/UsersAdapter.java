package myapp.com.project.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import myapp.com.project.R;
import myapp.com.project.model.User;

public class UsersAdapter extends ArrayAdapter<User> {

    Context context;
    int resource;
    ArrayList<User> objects;

    public UsersAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(resource,parent,false);
        TextView textdate=view.findViewById(R.id.textViewdate);
        TextView textrate=view.findViewById(R.id.textViewPrice);
        TextView textquantity=view.findViewById(R.id.textViewquantity);
        User user=objects.get(position);
        textdate.setText(user.date);
        textrate.setText(String.valueOf(user.rate));
        textquantity.setText(String.valueOf(user.quantity));

        return view;
    }
}
