package httpclienttest;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class sample1 {

	public static void main(String[] args) throws ClientProtocolException, IOException{
		// TODO Auto-generated method stub
		//HttpGet httpGet = new HttpGet("http://localhost:8080/contacts");
		HttpGet httpGet = new HttpGet("http://10.7.5.100:8080/contacts");
		httpGet.setHeader("Accept", "application/json");
		
		CloseableHttpClient client = HttpClientBuilder.create().build();
		CloseableHttpResponse response = client.execute(httpGet);
		
		if(response.getStatusLine().getStatusCode()==200) {
			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);
			System.out.println(body);
		}else {
			System.out.println("상태코드:" + response.getStatusLine().getStatusCode());
		}
	}

}
