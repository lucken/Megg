package com.radiumdigital.meggnify;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.radiumdigital.meggnify.lib.LocalContext;
import com.radiumdigital.meggnify.lib.Utils;

public class MeggnifyAPI {

	private static MeggnifyAPI instance;

    private Context context;
	
	public static MeggnifyAPI sharedInstace() {
		if (instance == null) {
			synchronized(MeggnifyAPI.class) {
				if (instance == null) {
					instance = new MeggnifyAPI();
				}
			}
		}
		return instance;
	}
	
	protected MeggnifyAPI() {
		context = LocalContext.getContext();
	}
	
	List<NameValuePair> requestParameters(List<NameValuePair> parameters) {
		List<NameValuePair> requestParams = new ArrayList<NameValuePair>(parameters);
		requestParams.add(new BasicNameValuePair("auth_token", getToken(context)));
		//requestParams.add(new BasicNameValuePair("user_id", MeggnifyAPI.UUID()));
		
		return requestParams;
	}

    private String getToken(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String token  = prefs.getString(Constants.PREF_USER_TOKEN, "");
        if (token.isEmpty()) {
            return null;
        }
        return token;
    }

	String replaceParameters(List<NameValuePair> parameters, String url) {
		String urlString = url;
		
		return urlString;
	}
	
	String queryStringFromParameters(List<NameValuePair> parameters) {
		String queryString = URLEncodedUtils.format(parameters, "utf-8");
		
		return queryString;
	}
	
	HttpGet requestGetForUrl(String url, List<NameValuePair> parameters) {
		List<NameValuePair> requestParams = requestParameters(parameters);
		String requestUrl = replaceParameters(requestParams, url);
		String queryString = queryStringFromParameters(requestParams);
		requestUrl = requestUrl + "?" + queryString;
		
		HttpGet request = new HttpGet(requestUrl);
		return request;
	}
	
	HttpPost requestPostForUrl(String url, String parameters) {
		List<NameValuePair> requestParams = new ArrayList<NameValuePair>();
		String requestUrl = replaceParameters(requestParams, url);
		
		HttpPost request = new HttpPost(requestUrl);
        request.setHeader("Accept", "application/json");
		request.setHeader("Content-Type", "application/json");
		try {
            StringEntity data = new StringEntity(parameters);
			request.setEntity(data);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return request;
	}
	
	public InputStream postParams(String url, String parameters) {
        HttpClient client = new DefaultHttpClient();
		HttpPost request = requestPostForUrl(url, parameters);
		
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (response == null) {
			return null;
		} else if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            InputStream stream = null;
            try {
                stream = entity.getContent();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return stream;
		} else {
			return null;
		}
	}
	
	public InputStream getFromUrl(String url) {
		return getFromUrl(url, new ArrayList<NameValuePair>());
	}
	
	public InputStream getFromUrl(String url, List<NameValuePair> parameters) {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = requestGetForUrl(url, parameters);
		
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (response == null) {
			return null;
		} else if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			InputStream stream = null;
			try {
				stream = entity.getContent();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return stream;
		} else {
			return null;
		}
	}
	
	public static Boolean generateUUIDOnce() {
		return false;
	}
	
	public static String UUID() {
		return null;
	}

}
