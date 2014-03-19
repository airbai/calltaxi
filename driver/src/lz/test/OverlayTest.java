package lz.test;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;

class OverlayTest extends ItemizedOverlay<OverlayItem> {  
    //用MapView构造ItemizedOverlay  
    public OverlayTest(Drawable mark,MapView mapView){  
            super(mark,mapView);  
    }  

    protected boolean onTap(int index) {  
        //在此处理item点击事件  
    	Log.e("Overlaytest", "" + index);
        return true;  
    }  

    public boolean onTap(GeoPoint pt, MapView mapView){  
    	//在此处理MapView的点击事件，当返回 true时
    	super.onTap(pt,mapView);  
        return false;  
    }  
}          
