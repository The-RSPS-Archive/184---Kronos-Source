package io.ruin.model.item.actions.impl.boxes.mystery;

import io.ruin.api.utils.Random;
import io.ruin.cache.Icon;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.impl.Pet;
import io.ruin.utility.Broadcast;

import java.util.ArrayList;
import java.util.List;

public class PetMysteryBox {

    static {
        boolean[] owned = new boolean[Pet.values().length]; //basically static (works since players process one at a time on the same thread)
        ItemAction.registerInventory(6828, "open", (player, item) -> {
            player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Open your Pet Mystery Box and receive a random pet?", item, () -> {
                player.closeDialogue();
                /**
                 * Check for pet
                 */
                if(player.pet != null)
                    owned[player.pet.ordinal()] = true;
                /**
                 * Check inv for pets
                 */
                for(Item invItem : player.getInventory().getItems()) {
                    if(invItem == null)
                        continue;
                    Pet pet = invItem.getDef().pet;
                    if(pet == null)
                        continue;
                    owned[pet.ordinal()] = true;
                }
                /**
                 * Check bank for pets
                 */
                for(Item bankItem : player.getBank().getItems()) {
                    if(bankItem == null)
                        continue;
                    Pet pet = bankItem.getDef().pet;
                    if(pet == null)
                        continue;
                    owned[pet.ordinal()] = true;
                }
                /**
                 * Find potential pets
                 */
                List<Pet> potentialPets = new ArrayList<>();
                for(Pet pet : Pet.values()) {
                    if(owned[pet.ordinal()])
                        owned[pet.ordinal()] = false;
                    else if(pet.mysteryBox)
                        potentialPets.add(pet);
                }
                if(potentialPets.isEmpty()) {
                    player.dialogue(new ItemDialogue().one(6828, "You already have every obtainable pet unlocked!"));
                    return;
                }
                /**
                 * Give pet! :)
                 */
                Pet pet = Random.get(potentialPets);
                String descriptiveName = ItemDef.get(pet.itemId).descriptiveName;
                item.setId(pet.itemId);
                player.dialogue(new ItemDialogue().one(pet.itemId, "You have unlocked " + descriptiveName + "."));
                if(World.isLive()) {
                    if (potentialPets.size() == 1)
                        Broadcast.GLOBAL.sendNews(Icon.MYSTERY_BOX, "Pet Mystery Box", player.getName() + " has just unlocked every pet!");
                    else
                        Broadcast.GLOBAL.sendNews(Icon.MYSTERY_BOX, "Pet Mystery Box", player.getName() + " has just received " + descriptiveName + ".");
                }
            }));
        });
    }

}
