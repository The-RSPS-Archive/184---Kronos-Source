package io.ruin.model.activities.pestcontrol;

public enum PestControlRewards {
	VOID_MACE(93, "Void Mace", 250, 8841),
	VOID_TOP(94, "Void Top", 250, 8839),
	VOID_ROBES(95, "Void Robes", 250, 8840),
	VOID_GLOVES(96, "Void Gloves", 150, 8842),
	MAGE_HELM(119, "Mage Helm", 200, 11663),
	RANGER_HELM(120, "Ranger Helm", 200, 11664),
	MELEE_HELM(121, "Melee Helm", 200, 11665);

	private int widgetId;
	private String name;
	private int cost;
	private int itemId;

	public static final PestControlRewards[] VALUES = values();

	PestControlRewards(int widgetId, String name, int cost, int itemId) {
		this.widgetId = widgetId;
		this.name = name;
		this.cost = cost;
		this.itemId = itemId;
	}

	public int widgetId() {
		return widgetId;
	}

	public String displayName() {
		return name;
	}

	public int cost() {
		return cost;
	}

	public int itemId() {
		return itemId;
	}
}
