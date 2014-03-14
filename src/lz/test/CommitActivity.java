package lz.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CommitActivity extends Activity {

	public String prefix = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		prefix = ((GateApplication)getApplication()).prefix;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commit);

		Button btnNext = (Button) findViewById(R.id.btnNext);
		btnNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(CommitActivity.this, StatusActivity.class));
			}
		});

		TextView address = (TextView)findViewById(R.id.address);
		address.setText("hao de!");
	//	address.setText();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	@Override
	protected void onPause() {
		super.onPause();
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
}
