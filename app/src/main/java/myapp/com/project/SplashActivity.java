package myapp.com.project;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        handler.sendEmptyMessageDelayed(201,3000);

    }
    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            if(msg.what==201){
                Intent intent=new Intent(SplashActivity.this,OptionActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
}

