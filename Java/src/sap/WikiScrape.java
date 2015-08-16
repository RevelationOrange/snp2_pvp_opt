package sap;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import HTML.HTMLFile;
import HTML.HTMLTable;
import http.HTTP;



public class WikiScrape {
	
	public static void parse(String url) {
		try {
			String s = HTTP.getString(url, null);
			HTMLFile file = new HTMLFile(s);
			for (HTMLTable table : file.tables) {
				try {
					new Item(table);
				}
				catch (IllegalArgumentException e) {}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Item[] findItem(String s) {
		ArrayList<Item> found = new ArrayList<>();
		for (Item item : Item.items.values()) {
			if (item.name.toLowerCase().contains(s.toLowerCase()))
				found.add(item);
		}
		Item[] array = found.toArray(new Item[found.size()]);
		Arrays.sort(array);
		return array;
	}
	
	public static void main(String[] args) {
		parse("http://www.edgebee.com/wiki/index.php?title=Shopkeeper");
		parse("http://www.edgebee.com/wiki/index.php?title=Blacksmith");
		parse("http://www.edgebee.com/wiki/index.php?title=Carpenter");
		parse("http://www.edgebee.com/wiki/index.php?title=Druid");
		parse("http://www.edgebee.com/wiki/index.php?title=Tailor");
		parse("http://www.edgebee.com/wiki/index.php?title=Armorer");
		parse("http://www.edgebee.com/wiki/index.php?title=Leather-worker");
		parse("http://www.edgebee.com/wiki/index.php?title=Bowyer");
		parse("http://www.edgebee.com/wiki/index.php?title=Sorceress");
		parse("http://www.edgebee.com/wiki/index.php?title=Tinkerer");
		parse("http://www.edgebee.com/wiki/index.php?title=Jeweler");
		parse("http://www.edgebee.com/wiki/index.php?title=Luthier");
		parse("http://www.edgebee.com/wiki/index.php?title=Enchanter");
		System.out.println("Resolving...");
		Item.resolveAllResources();
		for (Item item : Item.items.values()) {
			System.out.println(item);
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				String s = in.readLine();
				if (s.equals("exit"))
					break;
				Item[] items = findItem(s);
				if (items.length == 0)
					System.out.println("\tNo Items found...");
				else if (items.length == 1) {
					System.out.println(items[0]);
				}
				else {
					for (int i = 0; i < items.length; i++)
						System.out.println("\t" + (i + 1) + ".\t" + items[i].name);
					int num = -1;
					while (num == -1) {
						try {
							num = Integer.parseInt(in.readLine());
							if (num < 1 || num > items.length) {
								System.out.println("Not a valid entry");
								num = -1;
							}
						}
						catch (NumberFormatException e) {
							System.out.println("Not a valid entry");
						}
					}
					System.out.println(items[num - 1]);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
