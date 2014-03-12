package lz.test;
import android.app.Activity;  
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Configuration;  
import android.os.Bundle;  
import android.util.Log;
import android.view.Menu;  
import android.widget.FrameLayout;  
import android.widget.Toast;  
import com.baidu.mapapi.BMapManager;  
import com.baidu.mapapi.map.MKMapViewListener;  
import com.baidu.mapapi.map.MapController;  
import com.baidu.mapapi.map.MapPoi;  
import com.baidu.mapapi.map.MapView;  
import com.baidu.platform.comapi.basestruct.GeoPoint;  

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.BDNotifyListener;//假如用到位置提醒功能，需要import该类
//如果使用地理围栏功能，需要import如下类
import com.baidu.location.BDGeofence;
import com.baidu.location.BDLocationStatusCodes;
import com.baidu.location.GeofenceClient;
import com.baidu.location.GeofenceClient.OnAddBDGeofencesResultListener;
import com.baidu.location.GeofenceClient.OnGeofenceTriggerListener;
import com.baidu.location.GeofenceClient.OnRemoveBDGeofencesResultListener;
import com.baidu.location.LocationClientOption.LocationMode;
       
public class MapActivity extends Activity{  
	public String mK = "KyBbknEZgtH41rYQDdTjkS2U";
    public BMapManager mBMapMan = null;
    public MapView mMapView = null;
    
    public LocationClient mLocationClient = null;
    public MyLocationListener myListener = new MyLocationListener(MapActivity.this);
    
    public void getLoc() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，默认值gcj02
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);//返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
        mLocationClient.setLocOption(option); 
    }

    @Override  
    public void onCreate(Bundle savedInstanceState){  
        super.onCreate(savedInstanceState);  
        mBMapMan=new BMapManager(getApplication());  
        mBMapMan.init(mK, null);    
        
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.setAccessKey(mK);
        mLocationClient.registerLocationListener(myListener);
        
        getLoc();

        //注意：请在试用setContentView前初始化BMapManager对象，否则会报错  
        setContentView(R.layout.activity_main);  
        mMapView=(MapView)findViewById(R.id.bmapsView);  
        mMapView.setBuiltInZoomControls(true);  
        //设置启用内置的缩放控件  
        MapController mMapController=mMapView.getController();  
        // 得到mMapView的控制权,可以用它控制和驱动平移和缩放  
        GeoPoint point =new GeoPoint((int)(39.915* 1E6),(int)(116.404* 1E6));  
        //用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)  
        mMapController.setCenter(point);//设置地图中心点  
        mMapController.setZoom(12);//设置地图zoom级别   	super.On
        


        if (mLocationClient != null && false == mLocationClient.isStarted()) {
            mLocationClient.requestLocation();
            mLocationClient.start();
       /*  	new AlertDialog.Builder(MapActivity.this).setMessage(myListener.locationSb)
            .setPositiveButton("确定", null)
            .setCancelable(true)
            .show();
		*/
            Toast toast = Toast.makeText(MapActivity.this, myListener.locationSb, Toast.LENGTH_SHORT); 
            toast.show();
        }
        else
            Log.e("LocSDK3", "locClient is null or not started");
    }  

    @Override  
    protected void onDestroy(){  
            mMapView.destroy();  
            if(mBMapMan!=null) {
                    mBMapMan.destroy();  
                    mBMapMan=null;  
            }  
            super.onDestroy();  
    }  

    @Override  
    protected void onPause(){  
            mMapView.onPause();  
            if(mBMapMan!=null){  
                   mBMapMan.stop();  
            }  
            super.onPause();  
    }  

    @Override  
    protected void onResume(){  
            mMapView.onResume();  
            if(mBMapMan!=null){  
                    mBMapMan.start();  
            }  
           super.onResume();  
    }  
}   