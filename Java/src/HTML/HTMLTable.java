package HTML;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class HTMLTable {
	private static Pattern			rowPattern	= Pattern.compile("<tr.*?>(.*?)</tr>");
	public final List<HTMLTableRow>	rows		= new ArrayList<HTMLTableRow>();
	
	public HTMLTable(String s) {
		Matcher m = rowPattern.matcher(s);
		while (m.find())
			rows.add(new HTMLTableRow(m.group(1)));
		for (HTMLTableRow row : rows)
			row.trimEmpty();
	}
	
	public void trimEmpty() {
		List<HTMLTableRow> empty = new ArrayList<HTMLTableRow>();
		for (HTMLTableRow row : rows) {
			row.trimEmpty();
			if (row.cells.isEmpty())
				empty.add(row);
		}
		rows.removeAll(empty);
	}
	
	@Override
	public String toString() {
		String s = "[";
		for (HTMLTableRow row : rows)
			s += "\n" + row;
		s += "\n]";
		return s;
	}
}
