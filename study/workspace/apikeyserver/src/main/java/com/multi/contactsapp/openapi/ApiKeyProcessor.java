package com.multi.contactsapp.openapi;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class ApiKeyProcessor {

	private static final String API_KEY_PROPERTIES = "apiKey.properties";
	private static final String MAX_COUNT = "max";

	private static int maxCount;

	private Properties prop;

	@Autowired
	private ApiKeyRepository repository;

	public ApiKeyProcessor() throws ApiKeyException {

		this(ApiKeyProcessor.class.getResource(API_KEY_PROPERTIES));
	}

	public ApiKeyProcessor(URL url) throws ApiKeyException {

		prop = new Properties();

		try {
			prop.load(url.openStream());
			maxCount = Integer.parseInt(prop.getProperty(MAX_COUNT));
		} catch (IOException e) {
			e.printStackTrace();
			throw new ApiKeyException("Could not find API KEY FILE", "E001");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiKeyException(e.getMessage());
		}
	}

	public String requestNewAPIKey(ApiKeyVO apiKeyVO) throws Exception {

		  String apiKey = DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes());    
		  System.out.println("## hostName : " + apiKeyVO);
		  System.out.println("## keyValue: " + apiKey);
		  apiKeyVO.setApiKey(apiKey);
		  try {
		    repository.create(apiKeyVO);
		  } catch (Exception e) {
		    throw new ApiKeyException("SAME KEY IS ALREADY EXIST.");
		  }
		  return apiKey;
		}




		public void checkApiKey(String hostname, String apiKey) throws ApiKeyException {
		  ApiKeyVO vo = repository.read(apiKey);
		  if (vo == null) {
		    throw new ApiKeyException("OPEN API KEY IS UNREGISTED ");
		  }
		  if (hostname == null || hostname.equals(vo.getHostName()) == false) {
		    throw new ApiKeyException("HOSTNAME IS NOT VALID!!");
		  }
		  if (vo.getCount() >= maxCount) {
		    throw new ApiKeyException("EXCESSIVE NUMBER OF REQUEST");
		  }
		  repository.update(apiKey);		//사용 카운트 1증가
		}

}
