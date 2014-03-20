package lz.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StatusActivity extends Activity{
	
	public String prefix = null;
	public GateApplication app = null;
	public TextView status = null;
	public String webRet = null;
	final Handler handle = new Handler();
	final Runnable updateUI = new Runnable() {
		@Override 
		public void run() {
			if(true == webRet.equals("cancel"))
				status.setText("这个订单居然取消了诶");
			else if(true == webRet.equals("still"))
				status.setText("请火速赶往现场");
			else {
				Toast toast = Toast.makeText(StatusActivity.this, "该订单已完成", Toast.LENGTH_LONG);
				toast.show();
				startActivity(new Intent(StatusActivity.this, MapActivity.class));
			}
		}
	};
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		app = (GateApplication)getApplication();
		prefix = app.prefix;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);

		Button cancel = (Button)findViewById(R.id.btn_cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HttpFunc web = new HttpFunc();
				String url = prefix + "update.php?log_id=" + app.log_id + "&to=-1";
				web.execute(url);
				startActivity(new Intent(StatusActivity.this, MapActivity.class));
			}
		});

		status = (TextView)findViewById(R.id.status_Text);
		TextView getCar = (TextView)findViewById(R.id.getCar_text);
		getCar.setText("您的接客地点为:" + app.strAddr);
		
		HttpFunc web = new HttpFunc();
		String url = prefix + "updateLog.php?driver_id=" + app.id + "&log_id=" + app.log_id;
		String ret = web.execute(url);

		new Thread(new Update()).start();
	}
	
	public class Update implements Runnable {
		@Override 
		public void run() {
			try {
				while(true) {
                    Thread.sleep(2000);
                    HttpFunc web = new HttpFunc();
                    String url = prefix + "getLogStatus.php?log_id=" + app.log_id;
                    webRet = web.execute(url);
                    handle.post(updateUI);
				}
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
}
