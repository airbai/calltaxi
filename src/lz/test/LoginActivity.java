package lz.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.util.Log;
import android.view.*;

public class LoginActivity extends Activity {
	
	public EditText inputId, inputPwd;
	public Button btnLogin, btnRegister;

    public void onCreate(Bundle savedInstanceState){  
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_login);  
    	btnLogin = (Button)findViewById(R.id.login_button);
    	btnRegister = (Button)findViewById(R.id.register_button);
    	
    	inputId = (EditText)findViewById(R.id.login_username);
    	inputPwd = (EditText)findViewById(R.id.login_psword);

    	btnLogin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				/*
				String Sid = InputId.getText().toString();
                String Spwd = InputPwd.getText().toString();
                Log.e("login",Sid);
                Log.e("login",Spwd);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                */
			}
    	});

    	btnRegister.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				/*
				String Sid = InputId.getText().toString();
                String Spwd = InputPwd.getText().toString();
                Log.e("login",Sid);
                Log.e("login",Spwd);
                String url = "http://townboy.net";
                String ret = new HttpFunc().execute(url);
                Log.e("test", ret);
                */
				startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
				finish();
			}
    	});

    }
}
