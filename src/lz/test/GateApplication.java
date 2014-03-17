package lz.test;
import com.baidu.mapapi.BMapManager;

import android.app.Application;
import android.util.Log;

public class GateApplication extends Application {

	public String mk = "";
	public String prefix = "";
	public BMapManager mBMapMan = null;
	double aimLati, aimLong;
	public String strAddr = null;
	public String id = null;

	@Override
	public void onCreate() {
		setMk();
		setPrefix();
		super.onCreate();
	}
	public void setMk() {
		Log.e("test", "guess right");
		mk = "KyBbknEZgtH41rYQDdTjkS2U";
	}
	public void setPrefix() {
		Log.e("test", "guess right");
		prefix = "http://10.0.2.2/";
	}
}
