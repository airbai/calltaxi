package lz.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;


public class HttpFunc {
	@SuppressLint("NewApi")
	public String execute(String url) {
		String result = "";
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()  
        .detectDiskReads()  
        .detectDiskWrites()  
        .detectNetwork()   // or .detectAll() for all detectable problems  
        .penaltyLog()  
        .build());  
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()  
        .detectLeakedSqlLiteObjects()  
        .detectLeakedClosableObjects()  
        .penaltyLog()  
        .penaltyDeath()  
        .build());  
		try {
            BufferedReader reader = null;
            HttpGet request = new HttpGet();
            HttpClient client = new DefaultHttpClient();
                        
            request.setURI(new URI(url));
            HttpResponse reponse = client.execute(request);
            reader = new BufferedReader(new InputStreamReader(reponse.getEntity().getContent()));
            
            StringBuffer strBuffer = new StringBuffer("");
            String line = null;
            while((line = reader.readLine()) != null) 
            	strBuffer.append(line);
            result = strBuffer.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			Log.e("test","fail");
		}
		return result;
	}
}
