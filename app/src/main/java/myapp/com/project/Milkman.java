package myapp.com.project;

import android.content.Intent;
import android.nfc.cardemulation.CardEmulation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;

public class Milkman extends AppCompatActivity implements View.OnClickListener {

      CardView cardView,card1,card2,card3;
     Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milkman);
        cardView=findViewById(R.id.cardview);
        card1=findViewById(R.id.cardview1);
        cardView.setOnClickListener(this);
        card1.setOnClickListener(this);
        card2=findViewById(R.id.cardview2);
        card2.setOnClickListener(this);
        card3=findViewById(R.id.cardview3);
        card3.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id == R.id.cardview){
            intent=new Intent(Milkman.this,AddCustomerActivity.class);
            startActivity(intent);
        }

        if(id == R.id.cardview1){
            intent=new Intent(Milkman.this,AllCustomerActivity.class);
            startActivity(intent);
        }

        if(id == R.id.cardview2){
            intent=new Intent(Milkman.this,ViewUpdateActivity.class);
            startActivity(intent);

        }

        if(id == R.id.cardview3){
            //intent=new Intent(Milkman.this,BillActivity.class);
           // startActivity(intent);

        }

    }
}
