package myapp.com.project;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.TimeUnit;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.timessquare.CalendarCellDecorator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import myapp.com.project.Adapter.UsersAdapter;
import myapp.com.project.Provider.MyContentProvider;
import myapp.com.project.model.User;

public class AllUsersActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
@BindView(R.id.listview)
    ListView listView;
   ContentResolver resolver;

    ArrayList<User> users;
    UsersAdapter adapter;
    User user;
    int pos=0;
    Intent rcv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        ButterKnife.bind(this);
        resolver=getContentResolver();
        users=new ArrayList<>();
        queryUsersFromDB();
    }

    void queryUsersFromDB(){
        String[] projection={Util.COL_ID,Util.COL_QUANTITY,Util.COL_RATE,Util.COL_DATE,Util.COL_TYPE};
        Cursor cursor=resolver.query(Util.USER_URI,projection,null,null,null);
        if(cursor!=null){

            while(cursor.moveToNext()){

                User user=new User();
                user.id=cursor.getInt(cursor.getColumnIndex(Util.COL_ID));
                user.quantity=cursor.getDouble(cursor.getColumnIndex(Util.COL_QUANTITY));
                user.rate=cursor.getDouble(cursor.getColumnIndex(Util.COL_RATE));
                user.date=cursor.getString(cursor.getColumnIndex(Util.COL_DATE));
                user.type=cursor.getString(cursor.getColumnIndex(Util.COL_TYPE));
                users.add(user);

            }
            adapter=new UsersAdapter(this,R.layout.list_item,users);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }

    }



    void showDetails(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Details of "+user.date);

       builder.setMessage(user.toString());
        builder.setPositiveButton("DONE",null);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.CYAN));


    }

    void deleteUserFromDB(){

        String where = Util.COL_ID+" = "+user.id;
        int i = resolver.delete(Util.USER_URI,where,null);
        Toast.makeText(this,user.date+" deleted..."+i,Toast.LENGTH_LONG).show();

        users.remove(pos);

        adapter.notifyDataSetChanged();

    }

    void askForDeletion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete: "+user.date);
        builder.setMessage("Are you Sure ?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUserFromDB();
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.create().show();
    }


    void showOptions(){

        String[] items={"View","Update","Delete","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch(i) {
                    case 0:
                        showDetails();
                        break;

                    case 1:
                        Intent intent = new Intent(AllUsersActivity.this, MainActivity.class);
                        intent.putExtra("keyuser", user);
                        startActivity(intent);
                        break;

                    case 2:
                        askForDeletion();
                        break;


                    case 3:
                        finish();
                        break;


                }

                }
        });
        builder.create().show();

    }
public  void netbill() {
    double total=0;
    int days=0;
    for (User item: users){ total += item.getRate();}
    for(User bill:users){
        days+=bill.getId();
    }
   /* int days=0;
   String selection=Util.COL_ID+"="+String.valueOf(rcv.getIntExtra("keyuser",0));
   // String[] projection={Util.COL_ID,Util.COL_QUANTITY,Util.COL_RATE,Util.COL_DATE,Util.COL_TYPE};
    Cursor cur=resolver.rawquery("Select * from "+Util.TAB_NAME+"WHERE"+selection,null);

    cur.moveToLast();
    String strlast=cur.getString(cur.getColumnIndex(Util.COL_DATE));
    cur.moveToNext();
    String strstart=cur.getString(cur.getColumnIndex(Util.COL_DATE));
    SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyy");
    try {
        Date enddate,startdate;
        enddate=sdf.parse(strlast);
        startdate=sdf.parse(strstart);
        long diff=enddate.getTime()-startdate.getTime();
        days = (int) java.util.concurrent.TimeUnit.DAYS.convert(diff, java.util.concurrent.TimeUnit.MILLISECONDS);

    }
    catch (ParseException e){
        e.printStackTrace();

    }*/
    AlertDialog.Builder builder1=new AlertDialog.Builder(AllUsersActivity.this);
    builder1.setTitle("Net Bill");
    builder1.setMessage("your net bill for "+listView.getAdapter().getCount()+" days is "+total);
    builder1.create().show();
    Log.d("total","Total is:"+total);



}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_netbill,menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if(id == R.id.netbill){
           netbill();
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        pos=i;
        user=users.get(i);
        showOptions();

        }
}
