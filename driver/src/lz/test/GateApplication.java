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
	int log_id = 0;

	@Override
	public void onCreate() {
		setMk();
		setPrefix();
		super.onCreate();
	}
	public void setMk() {
		mk = "KyBbknEZgtH41rYQDdTjkS2U";
	}
	public void setPrefix() {
		prefix = "http://10.0.2.2/";
	}
}
