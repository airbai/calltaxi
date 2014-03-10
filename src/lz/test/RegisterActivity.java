package lz.test;

import android.app.Activity;
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
				finish();
			}
    	});
    }
}
