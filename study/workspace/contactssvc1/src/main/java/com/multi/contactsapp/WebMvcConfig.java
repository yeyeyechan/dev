package com.multi.contactsapp;

import java.nio.charset.Charset;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MappingJackson2XmlView;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.APPLICATION_JSON).favorParameter(true).parameterName("output")
				.ignoreAcceptHeader(true);
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.configureContentNegotiation(configurer);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub

		registry.addResourceHandler("/photos/**").addResourceLocations("file:///c:/dev/study/temp2/")
				.setCacheControl(CacheControl.noCache());
		// .setCacheControl(CacheControl.masAge(Duration.ofDays(1));
		registry.addResourceHandler("/swagger-ui/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
		.resourceChain(false);
	}

	@Bean(name = "jsonView")
	public View getJsonView() {
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		/*
		 * view.setModelKey("data"); view.setExtractValueFromSingleKeyModel(true);
		 */
		return view;
	}

	@Bean(name = "xmlView")
	public View getXmlView() {
		MappingJackson2XmlView view = new MappingJackson2XmlView();
		view.setModelKey("data"); 
		return view;
	}

	@Bean
	public ContentNegotiatingViewResolver getContentNegotiatingViewResolver() {
		ContentNegotiatingViewResolver cnvr = new ContentNegotiatingViewResolver();
		cnvr.setContentNegotiationManager(getContentNegotiationManager());
		List<View> defaultViews = new ArrayList<View>();
		defaultViews.add(getJsonView());
		defaultViews.add(getXmlView());
		cnvr.setDefaultViews(defaultViews);
		return cnvr;
	}

	@Bean
	public ContentNegotiationManager getContentNegotiationManager() {
		Properties mediaTypes = new Properties();
		mediaTypes.put("json", "application/json");
		mediaTypes.put("xml", "application/xml");

		ContentNegotiationManagerFactoryBean cnm = new ContentNegotiationManagerFactoryBean();
		cnm.setFavorParameter(true);
		cnm.setParameterName("output");
		cnm.setIgnoreAcceptHeader(false);
		cnm.setDefaultContentType(MediaType.APPLICATION_JSON);
		cnm.setMediaTypes(mediaTypes);
		return cnm.build();
	}
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
	    return restTemplateBuilder
	            .setConnectTimeout(Duration.ofMillis(5000)) 
	            .setReadTimeout(Duration.ofMillis(5000)) 
	            .additionalMessageConverters(new StringHttpMessageConverter(Charset.forName("UTF-8")))
	            .build();
	}

	/*
	 * @Override public void addCorsMappings(CorsRegistry registry) { // TODO
	 * Auto-generated method stub registry.addMapping("/contacts/**")
	 * .allowedOrigins("http://client:8000","http://jcornor.com:8000")
	 * .allowedMethods("GET","POST","PUT","DELETE","HEAD", "OPTIONS")
	 * .allowCredentials(true) .maxAge(3600); }
	 */
	
}
