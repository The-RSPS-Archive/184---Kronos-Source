package io.ruin.model.combat;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.Journal;
import io.ruin.model.inter.journal.main.PKRating;

public class WildernessRating {

    private static final double WIN = 1.0;
    private static final double LOSS = 0;

    public static final int DEFAULT_RATING = 1300;

    private static int calculateRating(int winnerRating, int loserRating, double outcome) {
        double exponent = (double) (loserRating - winnerRating) / 400;
        double expectedOutcome = (1 / (1 + (Math.pow(10, exponent))));
        return (int) Math.round(winnerRating + determineKFactor(winnerRating) * (outcome - expectedOutcome));
    }

    private static int determineKFactor(int rating) {
        int KFactor;
        if (rating < 2000)
            KFactor = 32;
        else if (rating >= 2000 && rating < 2400)
            KFactor = 24;
        else
            KFactor = 16;
        return KFactor;
    }

    static void adjustEloRatings(Player player, Player pKiller) {
        int killerElo = WildernessRating.calculateRating(pKiller.pkRating, player.pkRating, WildernessRating.WIN);
        int playerElo = WildernessRating.calculateRating(player.pkRating, pKiller.pkRating, WildernessRating.LOSS);
        pKiller.pkRating = killerElo;
        player.pkRating = playerElo;
//        if(pKiller.journal == Journal.MAIN)
//            PKRating.INSTANCE.send(pKiller);
//        if(player.journal == Journal.MAIN)
//            PKRating.INSTANCE.send(player);
    }

}
