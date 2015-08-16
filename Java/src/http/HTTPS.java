package http;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;



public class HTTPS {
	
	private static final String USER_AGENT = "Mozilla/5.0";
	
	public static HttpsURLConnection get(String url, Map<String, String> properties) throws IOException {
		HttpsURLConnection con = buildConnection(url, "GET", properties);
		con.connect();
		System.out.println("\nSending 'GET' request to URL : " + escapeHTML(url));
		System.out.println("Response Code : " + con.getResponseCode());
		// Side Effect: this actually fires the request. May not fire if not called.
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
	
	public static HttpsURLConnection post(String url, Map<String, String> properties) throws IOException {
		HttpsURLConnection con = buildConnection(url, "POST", properties);
		con.setDoOutput(true);
		con.connect();
		System.out.println("\nSending 'POST' request to URL : " + escapeHTML(url));
		System.out.println("Response Code : " + con.getResponseCode());
		// Side Effect: this actually fires the request. May not fire if not called.
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
	
	public static HttpsURLConnection put(String url, Map<String, String> properties) throws IOException {
		HttpsURLConnection con = buildConnection(url, "PUT", properties);
		con.connect();
		System.out.println("\nSending 'PUT' request to URL : " + escapeHTML(url));
		System.out.println("Response Code : " + con.getResponseCode());
		// Side Effect: this actually fires the request. May not fire if not called.
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
	
	public static HttpsURLConnection delete(String url, Map<String, String> properties) throws IOException {
		HttpsURLConnection con = buildConnection(url, "DELETE", properties);
		con.connect();
		System.out.println("\nSending 'DELETE' request to URL : " + escapeHTML(url));
		System.out.println("Response Code : " + con.getResponseCode());
		// Side Effect: this actually fires the request. May not fire if not called.
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
	
	private static HttpsURLConnection buildConnection(String url, String method, Map<String, String> properties) throws IOException {
		URL obj = new URL(escapeHTML(url));
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		
		con.setRequestMethod(method);
		con.setRequestProperty("User-Agent", USER_AGENT);
		if (properties != null)
			for (Entry<String, String> entry : properties.entrySet())
				con.setRequestProperty(entry.getKey(), entry.getValue());
		return con;
	}
	
	private static String response(HttpsURLConnection con) throws IOException {
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