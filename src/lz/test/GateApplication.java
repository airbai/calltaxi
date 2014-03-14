package lz.test;
import android.app.Application;
import android.util.Log;

public class GateApplication extends Application {

	public String mk = "";
	public String prefix = "";
	double aimLati, aimLong;

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
