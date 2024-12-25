package io.ruin.model.stat;

import io.ruin.model.entity.player.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
public class StatRequirement {
    public final StatType statType;
    public final int requiredLevel;
    private boolean canBoost;

    public boolean hasRequirement(Player player){
       Stat currentLevel = player.getStats().get(statType);

       if(canBoost)
           return currentLevel.currentLevel >= requiredLevel;
       else
           return currentLevel.fixedLevel >= requiredLevel;
    }
}
