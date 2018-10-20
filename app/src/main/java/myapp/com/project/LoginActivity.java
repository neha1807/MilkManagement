package myapp.com.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText etName,etPassword;
    Button btnLogin,btnRegister;
    SharedPreferences preferences;
    String user,password;
    Animation animation1;

    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login );


        overridePendingTransition(R.anim.translate_right,R.anim.translate_left);


          etName =  findViewById(R.id.etName);
          etPassword =  findViewById(R.id.etPassword);
         btnLogin =  findViewById(R.id.btnLogin);
         btnRegister =  findViewById(R.id.btnRegister);





        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = etName.getText().toString();
                password = etPassword.getText().toString();
                preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);


                String userDetails = preferences.getString(user + password + "data","No information on that user.");
                editor = preferences.edit();
                editor.putString("display",userDetails);
                editor.commit();
                if(user.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter All details",Toast.LENGTH_LONG).show();
                }
                else {
                    if (preferences.contains(user) && preferences.contains(password)) {

                        Intent displayScreen = new Intent(LoginActivity.this, Milkman.class);
                        startActivity(displayScreen);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid username and password", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });




        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent registerScreen = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(registerScreen);
            }
        });
    }
void clearFields(){
        etName.setText("");
        etPassword.setText("");

}



}