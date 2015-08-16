package sap;

public enum Resource {
	
	IRON("Iron Ore"),
	WOOD("Wood"),
	HERBS("Wild Herbs"),
	LEATHER("Raw Leather"),
	FABRIC("Fabric"),
	ELFWOOD("Elfwood Sticks"),
	GLASS("Glass"),
	POWDER("Powder"),
	DYES("Dyes"),
	STEEL("Steel Rods"),
	IRONWOOD("Ironwood"),
	OIL("Oil"),
	GEMS("Gems"),
	CRYSTAL("Crystals"),
	MITHRIL("Mithril Lumps"),
	ACID("Acid"),
	TEAR("Faery's Tear"),
	ROCK("Volcanic Rock"),
	SHELL("Abyss Shell"),
	FEATHER("Phoenix Feather"),
	ICE("Eternal Ice"),
	HORN("Demon Horn"),
	MOON("Moon Stone"),
	ROARING("Roaring Powder"),
	SILK("Golden Silk"),
	ESSENCE("Shadow Essence"),
	SCALE("Dragon Scale"),
	SUN("Rising Sun"),
	LEAF("Divine Leaf");
	
	public final String name;
	
	private Resource(String name) {
		this.name = name;
	}
	
	public static Resource get(String s) {
		for (Resource res : values()) {
			if (s.contains(res.name))
				return res;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
