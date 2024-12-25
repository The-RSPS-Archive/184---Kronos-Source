package io.ruin.model.entity.player;

import io.ruin.model.activities.raids.xeric.party.RecruitingBoard;
import io.ruin.model.map.object.actions.impl.edgeville.Christmas;
import io.ruin.model.map.route.routes.TargetRoute;

import java.util.function.BiConsumer;

public enum PlayerAction {

    ATTACK("Attack", true, (p1, p2) -> {
        p1.face(p2);
        p1.getCombat().setTarget(p2);
    }),
    FOLLOW("Follow", false, (p1, p2) -> {
        p1.face(p2);
        p1.getMovement().following = p2;
    }),
    TRADE("Trade with", false, (p1, p2) -> {
        p1.face(p2);
        TargetRoute.set(p1, p2, () -> {
            p1.getTrade().request(p2);
            p1.faceNone(true);
        });
    }),
    CHALLENGE("Challenge", false, (p1, p2) -> {
        p1.face(p2);
        TargetRoute.set(p1, p2, () -> {
            p1.getDuel().request(p2);
            p1.faceNone(true);
        });
    }),
    PELT("Pelt", true, (p1, p2) -> {
        p1.face(p2);
        Christmas.throwSnow(p1, p2);
    }),
    FIGHT("Fight", true, (p1, p2) -> {
        p1.face(p2);
        p1.getCombat().setTarget(p2);
    }),
    INVITE("Invite", false, (p1, p2) -> {
        p1.face(p2);
        TargetRoute.set(p1, p2, () -> {
            RecruitingBoard.invite(p1, p2);
            p1.faceNone(true);
        });
    });

    public final String name;

    public final boolean top;

    public final BiConsumer<Player, Player> consumer;

    PlayerAction(String name, boolean top, BiConsumer<Player, Player> consumer) {
        this.name = name;
        this.top = top;
        this.consumer = consumer;
    }

}