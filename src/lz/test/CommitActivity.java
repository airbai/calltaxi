package lz.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.search.*;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class CommitActivity extends Activity {

	public String prefix = null;
	public String mk = null;
	MKSearch mMKSearch = null;
	public BMapManager mBMapMan = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		GateApplication app = (GateApplication)getApplication();
		prefix = app.prefix;
		mk = app.mk;
		mBMapMan = app.mBMapMan;
	//	mBMapMan.start();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commit);

		Button btnNext = (Button) findViewById(R.id.btnNext);
		btnNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(CommitActivity.this, StatusActivity.class));
			}
		});

		double aimLati = app.aimLati;
		double aimLong = app.aimLong;
		TextView address = (TextView)findViewById(R.id.address);
		address.setText(Double.toString(aimLong));
		
	    mMKSearch = new MKSearch();  
	    mMKSearch.init(mBMapMan, new MySearchListener());		
	    mMKSearch.reverseGeocode(new GeoPoint((int)(aimLati * 1e6), (int) (aimLong * 1e6)));
	}
	
	public class MySearchListener implements MKSearchListener {

		@Override
		public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
			Log.e("test", "success");
			if(0 != arg1) {
				Toast toast = Toast.makeText(CommitActivity.this, "搜索错误", Toast.LENGTH_LONG);
				toast.show();
				return ;
			}
			
			Toast toast = Toast.makeText(CommitActivity.this, "搜索成功", Toast.LENGTH_LONG);
			toast.show();
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
				int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
		
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
