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


public class HttpFunc {
	public String execute(String url) {
		String result = "";
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
		}
		return result;
	}
}
