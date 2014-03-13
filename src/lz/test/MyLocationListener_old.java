package lz.test;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class MyLocationListener_old implements BDLocationListener {
	
	 public Context context;
	 public MapView mapview;

	 public MyLocationListener_old(Context tmp) {
		 context = tmp;
	 }
	 
	 public MyLocationListener_old(Context tmp, MapView tmp2) {
		 context = tmp;
		 mapview = tmp2;
	 }

	 public void onReceiveLocation(BDLocation location) {
		 if (location == null)
			 return ;
		 StringBuffer sb = new StringBuffer(256);
		 sb.append("time : ");
		 sb.append(location.getTime());
		 sb.append("\nerror code : ");
		 sb.append(location.getLocType());
		 sb.append("\nlatitude : ");
		 sb.append(location.getLatitude());
		 sb.append("\nlontitude : ");
		 sb.append(location.getLongitude());
		 sb.append("\nradius : ");
		 sb.append(location.getRadius());
		 if (location.getLocType() == BDLocation.TypeGpsLocation){
			 sb.append("\nspeed : ");
			 sb.append(location.getSpeed());
			 sb.append("\nsatellite : ");
			 sb.append(location.getSatelliteNumber());
		 } 
		 else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
			 sb.append("\naddr : ");
			 sb.append(location.getAddrStr());
		 }

		 Toast toast = Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG);
		 toast.show();
	}    
    
	public void onReceivePoi(BDLocation poiLocation) {
		if (poiLocation == null)
            return ;

		StringBuffer sb = new StringBuffer(256);
		sb.append("Poi time : ");
		sb.append(poiLocation.getTime());
		sb.append("\nerror code : ");
		sb.append(poiLocation.getLocType());
		sb.append("\nlatitude : ");
		sb.append(poiLocation.getLatitude());
		sb.append("\nlontitude : ");
		sb.append(poiLocation.getLongitude());
		sb.append("\nradius : ");
		sb.append(poiLocation.getRadius());
		if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation){
		    sb.append("\naddr : ");
		    sb.append(poiLocation.getAddrStr());
		}
		if(poiLocation.hasPoi()) {
		    sb.append("\nPoi:");
			sb.append(poiLocation.getPoi());
		 }
		 else 
			 sb.append("noPoi information");

		 Log.e("test",sb.toString());
	}
}
