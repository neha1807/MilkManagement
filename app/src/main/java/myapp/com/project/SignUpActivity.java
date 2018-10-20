package myapp.com.project;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editname,editphone,editpassword,editconfirm;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Button btn;

    String name,Passw,phone,confirm;
    void clearfields(){
        editname.setText("");
        editphone.setText("");
        editpassword.setText("");
        editconfirm.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("Sign Up");
        editname=findViewById(R.id.editText);
        editphone=findViewById(R.id.editText4);
        editpassword=findViewById(R.id.editText2);
        editconfirm=findViewById(R.id.editText5);

        btn=findViewById(R.id.button2);
        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        preferences=getSharedPreferences("MYPREFS",MODE_PRIVATE);
        name=editname.getText().toString();
        phone=editphone.getText().toString();
         Passw=editpassword.getText().toString();
         confirm=editconfirm.getText().toString();
         if(name.isEmpty() && phone.isEmpty() && Passw.isEmpty() && confirm.isEmpty()){
             Toast.makeText(this,"please enter correct details",Toast.LENGTH_LONG).show();
         }
         else {
             if (Passw.equals(confirm)) {
                 Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show();

             } else {
                 Toast.makeText(this, "password and confirm password should be same", Toast.LENGTH_LONG).show();

             }
         }
        editor=preferences.edit();

        editor.putString(name,name);
        editor.commit();
        editor.putString(Passw,Passw);
        editor.commit();
        editor.putString(name + Passw + "data",name + "\n" + phone );
        editor.commit();
        clearfields();



    }
}
