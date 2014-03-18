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
			if(true == webRet.equals("no"))
				status.setText("暂时还没有司机来接您哦");
			else
				status.setText("牌照为" + webRet + "的车辆已经接单");
			Log.e("commit", "updateUI");
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

		Button finish = (Button)findViewById(R.id.btn_finish);
		finish.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HttpFunc web = new HttpFunc();
				String url = prefix + "update.php?log_id=" + app.log_id + "&to=1";
				web.execute(url);
				startActivity(new Intent(StatusActivity.this, MapActivity.class));
			}
		});
		
		status = (TextView)findViewById(R.id.status_Text);
		status.setText("还没接单");
		TextView getCar = (TextView)findViewById(R.id.getCar_text);
		getCar.setText("您的上车地点为:" + app.strAddr);
		
		HttpFunc web = new HttpFunc();
		String url = prefix + "Commit.php?id=" + app.id + "&lati=" + app.aimLati + "&long=" + app.aimLong;
		String ret = web.execute(url);
		app.log_id = Integer.parseInt(ret);

		new Thread(new Update()).start();
	}
	
	public class Update implements Runnable {
		@Override 
		public void run() {
			try {
				while(true) {
                    Thread.sleep(2000);
                    HttpFunc web = new HttpFunc();
                    String url = prefix + "UpdateStatus.php?log_id=" + app.log_id;
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
