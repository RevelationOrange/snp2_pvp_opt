package HTML;


import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class HTMLTableCell {
	
	private static Pattern	pattern	= Pattern.compile("<.*?>(.*?)</.*?>");
	public String			value;
	
	public HTMLTableCell(String text) {
		value = text;
	}
	
	private String value(String value) {
		Matcher matcher = pattern.matcher(value);
		if (matcher.find())
			return matcher.group(1);
		else
			return value;
	}
	
	private String strip(String value) {
		Matcher matcher = Pattern.compile("</?.*?/?>").matcher(value);
		return matcher.replaceAll("");
	}
	
	@Override
	public String toString() {
		return value;
	}
	
}
