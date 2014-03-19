package lz.test;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;  
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;  
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;  
import android.os.Looper;
import android.util.Log;
import android.view.Menu;  
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;  
import android.widget.Toast;  
import com.baidu.mapapi.BMapManager;  
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapViewListener;  
import com.baidu.mapapi.map.MapController;  
import com.baidu.mapapi.map.MapPoi;  
import com.baidu.mapapi.map.MapView;  
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
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

	public String mk = null;
	public String prefix = null;
    public MapView mMapView = null;
    public LocationClient mLocationClient = null;
    public MyLocationListener myListener = null;
    public MyLocationOverlay myLocationOverlay = null;
    public ItemizedOverlay itemOverlay= null;
    public GateApplication app = null;
    public boolean move = false;

    @Override  
    public void onCreate(Bundle savedInstanceState){  
    	
    	app = (GateApplication)getApplication();
    	mk = app.mk;
	    prefix = app.prefix ;
    	
        super.onCreate(savedInstanceState);  

        app.mBMapMan = new BMapManager(getApplication());
        app.mBMapMan.init(mk, null);
        getLoc();

        setContentView(R.layout.activity_main);  
        
        mapView();
        addDriverOverlay();
        
        // 做Button 监听
        Button btnCommit = (Button) findViewById(R.id.CommitButton);
        btnCommit.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GateApplication app = (GateApplication)getApplication();
                GeoPoint mapCenter = mMapView.getMapCenter();
                app.aimLati = mapCenter.getLatitudeE6()/1e6;
                app.aimLong = mapCenter.getLongitudeE6()/1e6;

				startActivity(new Intent(MapActivity.this, CommitActivity.class));
			}
		});
        
        new Thread(new Update()).start();
    }  
    
    public class Update implements Runnable {
    	@Override
    	public void run() {
    		Looper.prepare();
    		while(true) {
    			try {
    				Thread.sleep(2000);
    				itemOverlay.removeAll();
    				
    				String url = prefix + "getDriver.php";
    				String ret = new HttpFunc().execute(url);
    				JSONArray json = new JSONArray(ret);

    				double last = 0; 
    				for(int i = 0 ;i < json.length(); i++) { 
    					double value = json.getDouble(i);
    					if(0 == i % 2)
    						last = value;
    					else {
    						GeoPoint p1 = new GeoPoint((int) (last * 1E6), (int) (value * 1E6));  
    						OverlayItem item = new OverlayItem(p1,"","");  
    						itemOverlay.addItem(item);
    					}
    				}
                    mMapView.refresh();  
    			} 
    			catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	}
    }
    
    public void mapView() {
        mMapView = (MapView)findViewById(R.id.bmapsView);  

        mMapView.setBuiltInZoomControls(true);  
        //设置启用内置的缩放控件  
        MapController mMapController = mMapView.getController();  
        // 得到mMapView的控制权,可以用它控制和驱动平移和缩放  
        GeoPoint point = new GeoPoint((int)(39.915* 1E6),(int)(116.404* 1E6));  
        //用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)  
        mMapController.setCenter(point);//设置地图中心点  
        mMapController.setZoom(18);//设置地图zoom级别
    }
    
    public void addSelfOverlay(double Lati, double Long) {
        myLocationOverlay = new MyLocationOverlay(mMapView);
        LocationData locData = new LocationData();
        locData.latitude = Lati;
        locData.longitude = Long;
        myLocationOverlay.setData(locData);
        mMapView.getOverlays().add(myLocationOverlay);
        mMapView.refresh();
        mMapView.getController().animateTo(new GeoPoint((int)( locData.latitude * 1e6),(int)(locData.longitude * 1e6)));
    }
    
    public void addDriverOverlay() {
        Drawable mark = getResources().getDrawable(R.drawable.icon_marka);  
        itemOverlay = new ItemizedOverlay(mark, mMapView);  
        mMapView.getOverlays().add(itemOverlay);  
    }

    public void getLoc() {
        myListener = new MyLocationListener(MapActivity.this, 
    		(MapView)findViewById(R.id.bmapsView));
        
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.setAccessKey(mk);
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，默认值gcj02
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);//返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
        mLocationClient.setLocOption(option); 

        if (mLocationClient != null && false == mLocationClient.isStarted()) {
            mLocationClient.requestLocation();
            mLocationClient.start();
        }
        else
            Log.e("LocSDK3", "locClient is null or not started");
    }

     public class MyLocationListener implements BDLocationListener {
	
                 public Context context;
                 public MapView mapview;

                 public MyLocationListener(Context tmp) {
                         context = tmp;
                 }
                 
                 public MyLocationListener(Context tmp, MapView tmp2) {
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
                         
                         if(false == move) {
                        	 addSelfOverlay(location.getLatitude() ,location.getLongitude());
                        	 move = true;
                         }
                         else {
                        	 LocationData locData = new LocationData();
                        	 locData.latitude = location.getLatitude();
                        	 locData.longitude = location.getLongitude();
                        	 locData.direction = location.getDirection();
                        	 myLocationOverlay.setData(locData);
                         }
                        mMapView.refresh();
                }    
            
                public void onReceivePoi(BDLocation poiLocation) {
                }
        }
     
    @Override  
    protected void onDestroy(){  
            mMapView.destroy();  
            if(app.mBMapMan!=null) {
                    app.mBMapMan.destroy();  
                    app.mBMapMan=null;  
            }  
            super.onDestroy();  
    }  

    @Override  
    protected void onPause(){  
            mMapView.onPause();  
            if(null != mLocationClient && true == mLocationClient.isStarted())
            	mLocationClient.stop();

            if(app.mBMapMan!=null)
            	app.mBMapMan.stop();  
            super.onPause();  
    }  

    @Override  
    protected void onResume(){  
            mMapView.onResume();  
            if(app.mBMapMan!=null)
                app.mBMapMan.start();  
           super.onResume();  
    }  
}   
