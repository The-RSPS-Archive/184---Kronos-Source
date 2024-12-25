package io.ruin.model.activities.wilderness

import io.ruin.model.item.loot.LootItem
import io.ruin.model.item.loot.LootTable


/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 6/13/2020
 */

class LarranChestLoot : LootTable() {

    init {
        addTable(29,
                LootItem(995, 50_000, 1_000_000, 1), //coins
                LootItem(533, 100, 300, 1), //big bones
                LootItem(537, 100, 300, 1), //dragon bones
                LootItem(1334, 1, 3, 1), //rune scim
                LootItem(1128, 1, 3, 1), //rune plate
                LootItem(1080, 1, 3, 1), //rune legs
                LootItem(565, 200, 600, 1), //blood rune
                LootItem(560, 200, 600, 1), //death rune
                LootItem(562, 400, 900, 1), //chaos rune
                LootItem(11237, 50, 150, 1) //dragon arrowtip
        )
        addTable(15,
                LootItem(1216, 1, 3, 1), //d dagger
                LootItem(4588, 1, 3, 1), //d scim
                LootItem(386, 25, 250, 1), //shark
                LootItem(6686, 15, 100, 1), //sara brew
                LootItem(3027, 10, 100, 1), //super restore
                LootItem(1514, 150, 250, 1), //magic log
                LootItem(5295, 4, 6, 1), //ranarr seed
                LootItem(392, 100, 300, 100) //manta ray
        )
        addTable(5,
                LootItem(11232, 100, 250, 1), //coins
                LootItem(452, 25, 75, 1) //rune ore
        )
        addTable(1,
                LootItem(22610, 1, 1, 1), //vesta spear
                LootItem(22613, 1, 1, 1), //vesta sword
                LootItem(22616, 1, 1, 1), //vesta chest
                LootItem(22619, 1, 1, 1), //vesta skirt
                LootItem(22622, 1, 1, 1), //statius warhammer
                LootItem(22625, 1, 1, 1), //statius helm
                LootItem(22628, 1, 1, 1), //statius plate
                LootItem(22631, 1, 1, 1), //statius legs
                LootItem(22634, 25, 75, 1), //Morrigans axe
                LootItem(22636, 25, 75, 1), //morrigans javelin
                LootItem(22638, 1, 1, 1), //morrigans coif
                LootItem(22641, 1, 1, 1), //morrigans body
                LootItem(22644, 1, 1, 1), //morrigans chaps
                LootItem(22647, 1, 1, 1), //zuriels staff
                LootItem(22650, 1, 1, 1), //zuriels hood
                LootItem(22653, 1, 1, 1), //zuriels top
                LootItem(22656, 1, 1, 1) //zuriels bottom
        )
    }

}