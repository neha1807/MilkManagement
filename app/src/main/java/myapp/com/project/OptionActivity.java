package myapp.com.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OptionActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.buttonuser)
    Button btn;

    @BindView(R.id.buttonmilkman)
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        btn.setOnClickListener(this);
        btn1.setOnClickListener(this);
        Animation animation1= AnimationUtils.loadAnimation(this,R.anim.translate_right);
        btn.startAnimation(animation1);
        Animation animation2=AnimationUtils.loadAnimation(this,R.anim.translate_left);
        btn1.startAnimation(animation2);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.buttonuser){
            Intent intent=new Intent(OptionActivity.this,MainActivity.class);
            startActivity(intent);
        }
        if(id == R.id.buttonmilkman){
            Intent intent=new Intent(OptionActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }
}
