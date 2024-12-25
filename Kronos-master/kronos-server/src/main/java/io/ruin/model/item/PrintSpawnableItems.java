package io.ruin.model.item;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 2/15/2020
 */


import io.ruin.model.World;

public class PrintSpawnableItems {

    public static void main(String[] args) {
        for(SpawnableItem item : SpawnableItem.values()) {
            System.out.println("Original ID:  "+item.getId()+" - New ID: "+(item.getId() - World.spawnableOffset)+" - Item Name: "+item.name());
        }
    }
}
