package com.yuan.tennis.ws.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;

public class WSClient
{
	public static final String BASE_URI = "http://localhost:8281/testWS";

	private Builder builder;
	
	public WSClient(String path)
	{
	
		ClientConfig cc = new ClientConfig();
		Client client = ClientBuilder.newClient(cc);
		client.register(MoxyJsonFeature.class);
		WebTarget target = client.target(BASE_URI);
		target = target.path("rest").path(path);
		
		builder = target.request();
	}
	
	
	/**
	 * Returns the response as XML e.g : <User><Name>Pavithra</Name></User>
	 * 
	 * @param service
	 * @return
	 */
	public <T extends Object> T getResponse(Class<T> t)
	{
		return (T) builder.accept(MediaType.APPLICATION_JSON).get(t);
	}

	/**
	 * Returns the response as XML e.g : <User><Name>Pavithra</Name></User>
	 * 
	 * @param service
	 * @return
	 */
	public <T extends Object> T postRequest(Class<T> t, Object obj)
	{
		//return (T) builder.(MediaType.APPLICATION_JSON).
		//		accept(MediaType.APPLICATION_JSON).post(t, obj);
		
		return builder.post(Entity.entity(obj, MediaType.APPLICATION_JSON_TYPE), t);
		
	}
}
