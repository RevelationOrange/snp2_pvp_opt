package http;


import java.io.IOException;
import java.net.HttpURLConnection;



public class HTTPConnectionException extends IOException {
	
	private static final long	serialVersionUID	= 1L;
	public final int			errorCode;
	
	public HTTPConnectionException(HttpURLConnection con) throws IOException {
		super(con.getResponseCode() + " " + con.getResponseMessage());
		errorCode = con.getResponseCode();
	}
	
}
