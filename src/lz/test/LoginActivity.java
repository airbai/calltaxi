package lz.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.util.Log;
import android.view.*;

public class LoginActivity extends Activity {
	
	public EditText inputId, inputPwd;
	public Button btnLogin, btnRegister;
	public String prefix = null;

    public void onCreate(Bundle savedInstanceState){  
    	prefix = ((GateApplication)getApplication()).mk;
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_login);  
    	btnLogin = (Button)findViewById(R.id.login_button);
    	btnRegister = (Button)findViewById(R.id.register_button);
    	
    	inputId = (EditText)findViewById(R.id.login_username);
    	inputPwd = (EditText)findViewById(R.id.login_psword);

    	btnLogin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String Sid = inputId.getText().toString();
                String Spwd = inputPwd.getText().toString();

                String url = prefix + "login.php?id=" + Sid + "&pwd=" + Spwd + "&type=0";
                Log.e("test", url);
                String ret = new HttpFunc().execute(url);
                Log.e("ret", ret);
                if(true == ret.equals("yes"))
                	startActivity(new Intent(LoginActivity.this, MapActivity.class));
                else {
                 	new AlertDialog.Builder(LoginActivity.this).setMessage("密码错误")
                    .setPositiveButton("确定", null)
                    .setCancelable(true)
                    .show();
                }
			}
    	});

    	btnRegister.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
			}	
    	});

    }
}
