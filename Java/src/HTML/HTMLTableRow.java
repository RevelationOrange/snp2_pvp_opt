package HTML;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class HTMLTableRow {
	
	private static Pattern				cellPattern	= Pattern.compile("<td.*?>(.*?)</td>");
	public final List<HTMLTableCell>	cells		= new ArrayList<HTMLTableCell>();
	
	public HTMLTableRow(String s) {
		Matcher m = cellPattern.matcher(s);
		while (m.find())
			cells.add(new HTMLTableCell(m.group(1)));
	}
	
	public void trimEmpty() {
		List<HTMLTableCell> empty = new ArrayList<HTMLTableCell>();
		for (HTMLTableCell cell : cells) {
			if (cell.value == null)
				empty.add(cell);
			else if (cell.value.length() == 0)
				empty.add(cell);
		}
		cells.removeAll(empty);
	}
	
	@Override
	public String toString() {
		String s = " <";
		for (HTMLTableCell cell : cells)
			s += "\n  " + cell;
		s += "\n >";
		return s;
	}
	
}
