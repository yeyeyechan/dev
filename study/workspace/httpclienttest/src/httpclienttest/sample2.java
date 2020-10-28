package httpclienttest;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class sample2 {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		HttpPost httpPost = new HttpPost("http://10.7.5.100:8080/contacts");
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");

		String json = "{\"name\": \"서예찬\" , \"tel\" : \"010-7585-3521\" , \"address\": \"서울\"}";
		HttpEntity postEntity = new StringEntity(json, "UTF-8");
	    httpPost.setEntity(postEntity);

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = httpClient.execute(httpPost);

		if (response.getStatusLine().getStatusCode() == 200) {
			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);
			System.out.println(body);
		} else {
			System.out.println("상태코드 : " + response.getStatusLine().getStatusCode());
		}

	}

}
