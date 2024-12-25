package io.ruin.model.activities.wilderness.cluekeys;

import io.ruin.model.item.Item;
import io.ruin.model.map.Position;

/**
 * @author Adam Ali ("Kal-El") https://www.rune-server.ee/members/kal+el/
 */
public enum KeyData {
    KEY_1(new Item(3457, 1), new Position(3138, 3784, 0), "near chins in 34 wild!", "The top of Black Chinchompa Hill"),
    KEY_2(new Item(3455, 1), new Position(3132, 3911, 0), "near the wilderness lever in 49 wild!", "Near the volcano vents in 49 wild, by the wilderness lever"),
    KEY_3(new Item(3458, 1), new Position(3030, 3948, 0), "near the seaweed rocks in 54 wild!", "By the seaweed rocks in 54 wild");

    private Item item;
    private Position toDig;
    private String locationName;
    private String clue;

    KeyData(Item item, Position toDig, String locationName, String clue) {
        this.item = item;
        this.toDig = toDig;
        this.locationName = locationName;
        this.clue = clue;
    }

    public Item getItem() {
        return item;
    }

    public Position getPosition() {
        return toDig;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getClue() {
        return clue;
    }}

