package HTML;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class HTMLFile {
	private static Pattern			tablePattern	= Pattern.compile("<table.*?>(.*?)</table>");
	public final List<HTMLTable>	tables			= new ArrayList<HTMLTable>();
	
	public HTMLFile(String s) {
		Matcher m = tablePattern.matcher(s);
		while (m.find()) {
			tables.add(new HTMLTable(m.group(1)));
		}
	}
	
}
