package lz.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
                    String ret = web.execute(url);
                    
                    if(true == ret.equals("no")) 
                    	status.setText("暂时还没有司机来接您");
                    else {
                    	status.setText("司机终于来咯 他的车牌是" + ret);
                    }
                    Log.e("Commit", "own");
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
