package http;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;



public class HTTP {
	
	private static final String USER_AGENT = "Mozilla/5.0";
	
	public static HttpURLConnection get(String url, Map<String, String> properties) throws IOException {
		HttpURLConnection con = buildConnection(url, "GET", properties);
		System.out.println("\nSending 'GET' request to URL : " + escapeHTML(url));
		int response = con.getResponseCode();
		// Side Effect: this actually fires the request. May not fire if not called.
		if (response != 200)
			throw new HTTPConnectionException(con);
		return con;
	}
	
	public static String getString(String url, Map<String, String> properties) throws IOException {
		return response(get(url, properties));
	}
	
	public static JSONObject getJSON(String url, Map<String, String> properties) throws IOException {
		return new JSONObject(getString(url, properties));
	}
	
	public static JSONArray getJSONArray(String url, Map<String, String> properties) throws IOException {
		return new JSONArray(getString(url, properties));
	}
	
	/**************************************************/
	
	public static HttpURLConnection post(String url, Map<String, String> properties) throws IOException {
		HttpURLConnection con = buildConnection(url, "POST", properties);
		con.setDoOutput(true);
		con.connect();
		System.out.println("\nSending 'POST' request to URL : " + escapeHTML(url));
		int response = con.getResponseCode();
		// Side Effect: this actually fires the request. May not fire if not called.
		if (response != 200)
			throw new HTTPConnectionException(con);
		return con;
	}
	
	public static String postString(String url, Map<String, String> properties) throws IOException {
		return response(post(url, properties));
	}
	
	public static JSONObject postJSON(String url, Map<String, String> properties) throws IOException {
		return new JSONObject(postString(url, properties));
	}
	
	public static JSONArray postJSONArray(String url, Map<String, String> properties) throws IOException {
		return new JSONArray(postString(url, properties));
	}
	
	/**************************************************/
	
	public static HttpURLConnection put(String url, Map<String, String> properties) throws IOException {
		HttpURLConnection con = buildConnection(url, "PUT", properties);
		con.connect();
		System.out.println("\nSending 'PUT' request to URL : " + escapeHTML(url));
		int response = con.getResponseCode();
		// Side Effect: this actually fires the request. May not fire if not called.
		if (response != 200)
			throw new HTTPConnectionException(con);
		return con;
	}
	
	public static String putString(String url, Map<String, String> properties) throws IOException {
		return response(put(url, properties));
	}
	
	public static JSONObject putJSON(String url, Map<String, String> properties) throws IOException {
		return new JSONObject(putString(url, properties));
	}
	
	public static JSONArray putJSONArray(String url, Map<String, String> properties) throws IOException {
		return new JSONArray(putString(url, properties));
	}
	
	/**************************************************/
	
	public static HttpURLConnection delete(String url, Map<String, String> properties) throws IOException {
		HttpURLConnection con = buildConnection(url, "DELETE", properties);
		con.connect();
		System.out.println("\nSending 'DELETE' request to URL : " + escapeHTML(url));
		int response = con.getResponseCode();
		// Side Effect: this actually fires the request. May not fire if not called.
		if (response != 200)
			throw new HTTPConnectionException(con);
		return con;
	}
	
	public static String deleteString(String url, Map<String, String> properties) throws IOException {
		return response(delete(url, properties));
	}
	
	public static JSONObject deleteJSON(String url, Map<String, String> properties) throws IOException {
		return new JSONObject(deleteString(url, properties));
	}
	
	public static JSONArray deleteJSONArray(String url, Map<String, String> properties) throws IOException {
		return new JSONArray(deleteString(url, properties));
	}
	
	/**************************************************/
	
	private static String escapeHTML(String s) {
		StringBuilder out = new StringBuilder(Math.max(16, s.length()));
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == ' ') {
				out.append("+");
			}
			else {
				out.append(c);
			}
		}
		return out.toString();
	}
	
	private static HttpURLConnection buildConnection(String url, String method, Map<String, String> properties) throws IOException {
		URL obj = new URL(escapeHTML(url));
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod(method);
		con.setRequestProperty("User-Agent", USER_AGENT);
		if (properties != null)
			for (Entry<String, String> entry : properties.entrySet())
				con.setRequestProperty(entry.getKey(), entry.getValue());
		return con;
	}
	
	private static String response(HttpURLConnection con) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		return response.toString();
	}
	
}