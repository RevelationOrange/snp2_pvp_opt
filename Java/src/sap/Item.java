package sap;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import HTML.HTMLTable;
import HTML.HTMLTableRow;



public class Item implements Comparable<Item> {
	
	private static Pattern	namePattern			= Pattern.compile("\\*?<a.*>(.*)</a>.*");
	private static Pattern	countPattern		= Pattern.compile(".*?([0-9]+)x");
	private static Pattern	itemcountPattern	= Pattern.compile(".*?([0-9]+)x\\s*(.*)");
	private static Pattern	linkPattern			= Pattern.compile("<a.*?>(.*?)</a>");
	private static Pattern	unlockPattern		= Pattern.compile("<a.*?title=\"(.*?) Recipe\".*?>.*?</a>.*?\\(([0-9]+)x\\)");
	
	public static HashMap<String, Item> items = new HashMap<String, Item>();
	
	public String					name;
	public List<ResourceIngredient>	ingredients		= new ArrayList<>();
	public List<ItemIngredient>		requiredItems	= new ArrayList<>();
	public List<String>				usedIn			= new ArrayList<>();
	public List<ItemIngredient>		unlocks			= new ArrayList<>();
	public int						timeToCraft;						// * 7.5s
	
	public boolean resourcesResolved = false;
	
	public Item(HTMLTable table) {
		table.trimEmpty();
		if (table.rows.isEmpty())
			throw new IllegalArgumentException();
		HTMLTableRow row = table.rows.get(0);
		Matcher m = namePattern.matcher(row.cells.get(0).value);
		if (!m.find())
			throw new IllegalArgumentException(row.cells.get(0).value + " is not item name.");
		name = strip(m.group(1)).trim();
		row = getRow(table, " Resources");
		if (row == null)
			throw new IllegalArgumentException("No resource entry");
		String[] resources = row.cells.get(1).value.split("<br />");
		for (String s : resources) {
			ResourceIngredient i = getIngredient(s);
			if (i != null)
				ingredients.add(i);
			else
				requiredItems.add(getItemIngredient(s));
		}
		row = getRow(table, "Crafting Speed");
		if (row == null)
			throw new IllegalArgumentException("No speed entry");
		timeToCraft = getCraftingSpeed(row.cells.get(1).value);
		items.put(name, this);
		row = getRow(table, "Needed for Recipes:");
		if (row == null)
			throw new IllegalArgumentException("No need entry");
		for (int i = 1; i < row.cells.size(); i++) {
			Matcher mat = linkPattern.matcher(row.cells.get(i).value);
			while (mat.find())
				usedIn.add(mat.group(1));
		}
		row = getRow(table, "Unlocks");
		if (row == null)
			throw new IllegalArgumentException("No need entry");
		for (int i = 1; i < row.cells.size(); i++) {
			Matcher mat = unlockPattern.matcher(row.cells.get(i).value);
			while (mat.find()) {
				unlocks.add(new ItemIngredient(Integer.parseInt(mat.group(2)), strip(mat.group(1))));
			}
		}
		items.put(name, this);
	}
	
	private ResourceIngredient getIngredient(String s) {
		Matcher m = countPattern.matcher(s);
		if (!m.find())
			throw new IllegalArgumentException("NonIngredient");
		Resource resource = Resource.get(s);
		if (resource == null)
			return null;
		return new ResourceIngredient(Integer.parseInt(m.group(1)), resource);
	}
	
	private ItemIngredient getItemIngredient(String s) {
		Matcher m = itemcountPattern.matcher(s);
		if (!m.find())
			throw new IllegalArgumentException("NonIngredient");
		return new ItemIngredient(Integer.parseInt(m.group(1)), strip(m.group(2)));
	}
	
	private int getCraftingSpeed(String s) {
		s = s.trim();
		if (s.contains("Very Long"))
			return 10;
		if (s.contains("Long"))
			return 7;
		if (s.contains("Medium"))
			return 4;
		if (s.contains("Short"))
			return 3;
		if (s.contains("Fast"))
			return 2;
		return -1;
	}
	
	public HTMLTableRow getRow(HTMLTable table, String firstValue) {
		for (HTMLTableRow row : table.rows) {
			if (!row.cells.isEmpty() && row.cells.get(0).value.contains(firstValue))
				return row;
		}
		return null;
	}
	
	private static String strip(String value) {
		value = value.replaceAll("&#39;", "'");
		Matcher matcher = Pattern.compile("</?.*?/?>").matcher(value);
		return matcher.replaceAll("");
	}
	
	@Override
	public int compareTo(Item item) {
		return name.compareTo(item.name);
	}
	
	@Override
	public String toString() {
		String s = name;
		if (!unlocks.isEmpty()) {
			s += "\tUnlocks:";
			for (ItemIngredient i : unlocks)
				s += i + "\t";
		}
		s += "\n\tCrafting time:\t" + timeToCraft * 7.5f + "s";
		for (ResourceIngredient i : ingredients)
			s += "\n\t\t" + i;
		for (ItemIngredient i : requiredItems)
			s += "\n\t\t(" + i + ")";
		if (!usedIn.isEmpty()) {
			s += "\n\tUsed In:";
			for (String i : usedIn)
				s += "\n\t\t" + i;
		}
		return s;
	}
	
	/**************************************************/
	
	public static void resolveAllResources() {
		List<Item> items = new ArrayList<>(Item.items.values());
		List<Item> done = new ArrayList<>();
		while (!items.isEmpty()) {
			for (Item item : items) {
				if (item.resolveResources())
					done.add(item);
			}
			items.removeAll(done);
			done.clear();
		}
	}
	
	public boolean resolveResources() {
		for (ItemIngredient ing : requiredItems) {
			Item i = items.get(ing.item);
			if (!i.resourcesResolved)
				return false;
		}
		for (ItemIngredient ingredient : requiredItems) {
			Item item = items.get(ingredient.item);
			for (ResourceIngredient ing : item.ingredients)
				addResource(ingredient.count * ing.count, ing.resource);
			timeToCraft += ingredient.count * item.timeToCraft;
		}
		return resourcesResolved = true;
	}
	
	private void addResource(int count, Resource resource) {
		for (ResourceIngredient res : ingredients) {
			if (res.resource == resource) {
				res.count += count;
				return;
			}
		}
		ingredients.add(new ResourceIngredient(count, resource));
	}
	
	/**************************************************/
	
	public static class ResourceIngredient {
		
		public int				count;
		public final Resource	resource;
		
		public ResourceIngredient(int count, Resource resource) {
			this.count = count;
			this.resource = resource;
		}
		
		@Override
		public String toString() {
			return count + "x\t" + resource;
		}
	}
	
	public static class ItemIngredient {
		
		public final int	count;
		public final String	item;
		
		public ItemIngredient(int count, String item) {
			this.count = count;
			this.item = item.trim();
		}
		
		@Override
		public String toString() {
			return count + "x\t" + item;
		}
	}
	
}
