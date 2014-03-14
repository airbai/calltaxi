package lz.test;
import android.app.Activity;  
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;  
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;  
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
	public String mK = "KyBbknEZgtH41rYQDdTjkS2U";
    public BMapManager mBMapMan = null;
    public MapView mMapView = null;
    public LocationClient mLocationClient = null;
    public MyLocationListener myListener = null;
    public MyLocationOverlay myLocationOverlay = null;
    
    @Override  
    public void onCreate(Bundle savedInstanceState){  
        super.onCreate(savedInstanceState);  

        mBMapMan=new BMapManager(getApplication());  
        mBMapMan.init(mK, null);    
        // 开始定位
        getLoc();

        //注意：请在试用setContentView前初始化BMapManager对象，否则会报错  
        setContentView(R.layout.activity_main);  
        
        mapView();

        Point cent = mMapView.getCenterPixel();
        String pr = cent.x + " " + cent.y + "\n";

      /*  new AlertDialog.Builder(MapActivity.this).setMessage(pr)
        .setPositiveButton("确定", null)
        .setCancelable(true)
        .show();
        */
        
        addSelfOverlay();
        addDriverOverlay();
        
        // 做Button 监听
        Button btnCommit = (Button) findViewById(R.id.CommitButton);
        btnCommit.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MapActivity.this, CommitActivity.class));
			}
		});
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
        mMapController.setZoom(12);//设置地图zoom级别
    }
    
    public void addSelfOverlay() {
// 		自己的位置图层
        myLocationOverlay = new MyLocationOverlay(mMapView);
        LocationData locData = new LocationData();
        locData.latitude = 30.3;
        locData.longitude = 120.2;
        myLocationOverlay.setData(locData);
        mMapView.getOverlays().add(myLocationOverlay);
        mMapView.refresh();
        mMapView.getController().animateTo(new GeoPoint((int)( locData.latitude * 1e6),(int)(locData.longitude * 1e6)));
    }
    
    public void addDriverOverlay() {
    	
//		司机的自定义图层
        double mLat1 = 30.3205910000;
        double mLon1 = 120.3497580000;  
        double mLat2 = 30.3198430000;  
        double mLon2 = 120.3593520000;  
        double mLat3 = 30.3270600000;  
        double mLon3 = 120.3958800000;  
        // 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)  
        GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));  
        GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));  
        GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));  
        //准备overlay图像数据，根据实情情况修复  

        Drawable mark= getResources().getDrawable(R.drawable.icon_marka);  
        //用OverlayItem准备Overlay数据  
        OverlayItem item1 = new OverlayItem(p1,"item1","item1");  
        //使用setMarker()方法设置overlay图片,如果不设置则使用构建ItemizedOverlay时的默认设置  
        OverlayItem item2 = new OverlayItem(p2,"item2","item2");  
        item2.setMarker(mark);  
        OverlayItem item3 = new OverlayItem(p3,"item3","item3");  
           
        //创建IteminizedOverlay  
        //ItemizedOverlay itemOverlay = new ItemizedOverlay(mark ,mMapView);
        TaxiItemizedOverlay itemOverlay = new TaxiItemizedOverlay(mark, mMapView);  
        //将IteminizedOverlay添加到MapView中  
          
        //mMapView.getOverlays().clear();  
        mMapView.getOverlays().add(itemOverlay);  
           
        //现在所有准备工作已准备好，使用以下方法管理overlay.  
        //添加overlay, 当批量添加Overlay时使用addItem(List<OverlayItem>)效率更高  
        itemOverlay.addItem(item1);  
        itemOverlay.addItem(item2);  
        itemOverlay.addItem(item3);  
        mMapView.refresh();  
        /*
        删除overlay .  
        itemOverlay.removeItem(itemOverlay.getItem(0));  
        mMapView.refresh();  
        清除overlay  
        itemOverlay.removeAll();  
        mMapView.refresh();  
        */
    }

    public void getLoc() {
        myListener = new MyLocationListener(MapActivity.this, 
    		(MapView)findViewById(R.id.bmapsView));
       // myListener = new MyLocationListener(MapActivity.this);
        
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.setAccessKey(mK);
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
                         
                        LocationData locData = new LocationData();
                        locData.latitude = location.getLatitude();
                        locData.longitude = location.getLongitude();
                        locData.direction = location.getDirection();
                        myLocationOverlay.setData(locData);

                        mMapView.refresh();
                        GeoPoint mapCenter = mMapView.getMapCenter();
                        String pr = sb.toString() + "\n" + Double.toString(mapCenter.getLatitudeE6()/1e6);
                        pr = pr + "\n" + Double.toString(mapCenter.getLongitudeE6()/1e6) + "\n";
             

                        Toast toast = Toast.makeText(context, pr, Toast.LENGTH_SHORT);
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
            if(null != mLocationClient && true == mLocationClient.isStarted())
            	mLocationClient.stop();

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
