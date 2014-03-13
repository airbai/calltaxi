package lz.test;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class TaxiItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private Context context;
	
	public TaxiItemizedOverlay(Drawable mark,MapView mapView){  
            super(mark,mapView);  
		// TODO Auto-generated constructor stub
	}
	protected boolean onTap(int index) {  
        //在此处理item点击事件  
		Toast.makeText(this.context, "fuck", Toast.LENGTH_SHORT).show();
        return true; 
    } 
    public boolean onTap(GeoPoint pt, MapView mapView){  
        //在此处理MapView的点击事件，当返回 true时  
        super.onTap(pt,mapView);  
        return false;  
    } 

}
