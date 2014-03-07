package lz.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.util.Log;
import android.view.*;

public class login extends Activity {
	
	public EditText id, pwd;
	public Button login, register;

    public void onCreate(Bundle savedInstanceState){  
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_login);  
    	login = (Button)findViewById(R.id.login);
    	register = (Button)findViewById(R.id.register);
    	
        id = (EditText)findViewById(R.id.id);
        pwd = (EditText)findViewById(R.id.pwd);

    	login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String Sid = id.getText().toString();
                String Spwd = pwd.getText().toString();
                Log.e("login",Sid);
                Log.e("login",Spwd);

			}
    	});

    	register.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String Sid = id.getText().toString();
                String Spwd = pwd.getText().toString();
                Log.e("login",Sid);
                Log.e("login",Spwd);
                String url = "http://townboy.net";
                String ret = new HttpFunc().execute(url);
                Log.e("test", ret);
			}
    	});

    }
}
