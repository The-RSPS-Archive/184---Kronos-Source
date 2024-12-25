package io.ruin.model.activities.pestcontrol;

import io.ruin.model.map.Position;

/**
 * Represents all possible types to assign along with their respective attributes.
 * @author Heaven
 */
public enum PestControlGameSettings {

	NOVICE(40, 200, 7, 2950, "Novice", new Position(2657, 2639, 0), 1689, 1690, 1714, 1715, 1716, 1717, 1709, 1710, 1724, 1725, 1726, 1727, 1704, 1705, 1734, 1694, 1695, 1696, 1697),
	INTERMEDIATE(70, 250, 12, 2951, "Intermediate", new Position(2644, 2644, 0), 1691, 1692, 1718, 1719, 1720, 1721, 1711, 1713, 1728, 1729, 1705, 1706, 1735, 1696, 1697, 1698, 1699),
	VETERAN(100, 250, 15, 2952, "Veteran", new Position(2638, 2653, 0), 1692, 1693, 1720, 1721, 1723, 1712, 1713, 1730, 1731, 1732, 1733, 1707, 1708, 1736, 1737, 1700, 1701, 1702, 1703);

	private final int combatLvReq;
	private final int portalHp;
	private final int points;
	private final int voidKnightId;
	private final String title;
	private final Position landerExit;
	private final int[] pests;

	PestControlGameSettings(int combatLvReq, int portalHp, int points, int voidKnightId, String title, Position landerExit, int... pests) {
		this.combatLvReq = combatLvReq;
		this.portalHp = portalHp;
		this.points = points;
		this.voidKnightId = voidKnightId;
		this.title = title;
		this.landerExit = landerExit;
		this.pests = pests;
	}

	public int combatLvReq() {
		return combatLvReq;
	}

	public int portalHp() {
		return portalHp;
	}

	public int voidKnightId() {
		return voidKnightId;
	}

	public int points() {
		return points;
	}

	public String title() {
		return title;
	}

	public Position exitTile() {
		return landerExit;
	}

	public int[] pests() {
		return pests;
	}
}
