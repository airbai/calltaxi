package lz.test;

import android.app.Activity;
import android.os.Bundle;

public class StatusActivity extends Activity{
	
	public String prefix = null;
	public GateApplication app = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		app = (GateApplication)getApplication();
		prefix = app.mk;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
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
