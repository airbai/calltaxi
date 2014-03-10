package lz.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.util.Log;
import android.view.*;

public class RegisterActivity extends Activity {
	
	public Button btnRegisterBack, btnFinishRegister;
	public EditText inputRegisterId, inputRegisterPwd, inputRegisterRePwd;
	
    public void onCreate(Bundle savedInstanceState){  
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_register);
    	
    	btnRegisterBack = (Button)findViewById(R.id.register_back_button);
    	btnFinishRegister = (Button)findViewById(R.id.finish_register_button);
    	inputRegisterId = (EditText)findViewById(R.id.register_username);
    	inputRegisterPwd = (EditText)findViewById(R.id.register_psword);
    	inputRegisterRePwd = (EditText)findViewById(R.id.register_repsword);
    	
    	btnRegisterBack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
                 startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
			}
		});

    	btnFinishRegister.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				String Sid = inputRegisterId.getText().toString();
                String Spwd = inputRegisterPwd.getText().toString();
                String Srepwd = inputRegisterRePwd.getText().toString();
                
                if(false == Spwd.equals(Srepwd)) {
                 	new AlertDialog.Builder(RegisterActivity.this).setMessage("两次密码不一致")
                 	.setPositiveButton("确定", null)
                 	.setCancelable(true)
                 	.show();
                	return ;
                }
                
                String url = "http://10.0.2.2/register.php?id=" + Sid + "&pwd=" + Spwd + "&type=0";
                Log.e("test", url);
                String ret = new HttpFunc().execute(url);
                Log.e("ret", ret);

                if(true == ret.equals("exist")) {
                 	new AlertDialog.Builder(RegisterActivity.this).setMessage("您的帐号已经存在")
                    .setPositiveButton("确定", null)
                    .setCancelable(true)
                    .show();
                }
                else {
                 	new AlertDialog.Builder(RegisterActivity.this).setMessage("注册成功")
                    .setPositiveButton("确定", null)
                    .setCancelable(true)
                    .show();
                 	startActivity(new Intent(RegisterActivity.this, MapActivity.class));
                }
			}
    	});
    }
}
