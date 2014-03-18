package lz.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
	public GateApplication app = null;
	public TextView address = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		app = (GateApplication)getApplication();
		prefix = app.prefix;
		mk = app.mk;

		app.mBMapMan.start();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commit);

		Button btnNext = (Button) findViewById(R.id.btnNext);
		btnNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                 new AlertDialog.Builder(CommitActivity.this).setMessage("确认提交订单")
                 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startActivity(new Intent(CommitActivity.this, StatusActivity.class));
					}
				 })
                 .setNegativeButton("取消", null)
                 .setCancelable(true)
                 .show();
			}
		});

		double aimLati = app.aimLati;
		double aimLong = app.aimLong;
		address = (TextView)findViewById(R.id.address);
	
	    mMKSearch = new MKSearch();  
	    mMKSearch.init(app.mBMapMan, new MySearchListener());		
	    mMKSearch.reverseGeocode(new GeoPoint((int)(aimLati * 1e6), (int) (aimLong * 1e6)));
	}
	
	public class MySearchListener implements MKSearchListener {

		@Override
		public void onGetAddrResult(MKAddrInfo result, int iError) {
			if(0 != iError) {
				Toast toast = Toast.makeText(CommitActivity.this, "搜索错误", Toast.LENGTH_LONG);
				toast.show();
				return ;
			}
		
			address.setText(result.strAddr);
			app.strAddr = result.strAddr;
			Toast toast = Toast.makeText(CommitActivity.this, result.strAddr, Toast.LENGTH_LONG);
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
