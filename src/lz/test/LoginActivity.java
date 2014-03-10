package lz.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.util.Log;
import android.view.*;

public class LoginActivity extends Activity {
	
	public EditText InputId, InputPwd;
	public Button BtnLogin, BtnRegister;

    public void onCreate(Bundle savedInstanceState){  
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_login);  
    	BtnLogin = (Button)findViewById(R.id.login_button);
    	BtnRegister = (Button)findViewById(R.id.register_button);
    	
    	InputId = (EditText)findViewById(R.id.login_username);
    	InputPwd = (EditText)findViewById(R.id.login_psword);

    	BtnLogin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String Sid = InputId.getText().toString();
                String Spwd = InputPwd.getText().toString();
                Log.e("login",Sid);
                Log.e("login",Spwd);

			}
    	});

    	BtnRegister.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String Sid = InputId.getText().toString();
                String Spwd = InputPwd.getText().toString();
                Log.e("login",Sid);
                Log.e("login",Spwd);
                String url = "http://townboy.net";
                String ret = new HttpFunc().execute(url);
                Log.e("test", ret);
			}
    	});

    }
}
