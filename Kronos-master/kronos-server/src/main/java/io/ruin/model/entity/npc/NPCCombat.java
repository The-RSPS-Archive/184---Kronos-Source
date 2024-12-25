package io.ruin.model.entity.npc;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.NPCDef;
import io.ruin.data.impl.npcs.npc_combat;
import io.ruin.model.World;
import io.ruin.model.achievements.listeners.experienced.DemonSlayer;
import io.ruin.model.activities.summerevent.SummerTokens;
import io.ruin.model.activities.tasks.DailyTask;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.combat.*;
import io.ruin.model.content.PvmPoints;
import io.ruin.model.content.upgrade.ItemEffect;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.DoubleDrops;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.GoldCasket;
import io.ruin.model.item.actions.impl.WildernessKey;
import io.ruin.model.item.actions.impl.jewellery.BraceletOfEthereum;
import io.ruin.model.item.actions.impl.jewellery.RingOfWealth;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Graphic;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.actions.impl.dungeons.KourendCatacombs;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.model.skills.slayer.Slayer;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import io.ruin.services.discord.impl.RareDropEmbedMessage;
import io.ruin.utility.Broadcast;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.cache.ItemID.VORKATHS_HEAD;

public abstract class NPCCombat extends Combat {

    protected NPC npc;

    protected npc_combat.Info info;

    protected Stat[] stats;

    private int[] bonuses;

    private boolean allowRespawn = true;

    @Setter @Getter
    private boolean allowRetaliate = true;

    protected final NPCCombat init(NPC npc, npc_combat.Info info) {
        this.npc = npc;
        this.info = info;
        this.stats = new Stat[] {
                new Stat(info.attack),      //0
                new Stat(info.defence),     //1
                new Stat(info.strength),    //2
                new Stat(info.hitpoints),   //3
                new Stat(info.ranged),      //4
                new Stat(1),           //5 (prayer, not used)
                new Stat(info.magic)        //6
        };
        this.bonuses = new int[] {
                /*
                 * Attack bonuses
                */
                info.stab_attack,
                info.slash_attack,
                info.crush_attack,
                info.magic_attack,
                info.ranged_attack,
                /*
                 * Defence bonuses
                 */
                info.stab_defence,
                info.slash_defence,
                info.crush_defence,
                info.magic_defence,
                info.ranged_defence,
        };
        init();
        return this;
    }

    public void updateInfo(npc_combat.Info newInfo) { // only used when transforming!
        info = newInfo;
        if (stats[0].fixedLevel != newInfo.attack) stats[0] = new Stat(newInfo.attack);
        if (stats[1].fixedLevel != newInfo.defence) stats[1] = new Stat(newInfo.defence);
        if (stats[2].fixedLevel != newInfo.strength) stats[2] = new Stat(newInfo.strength);
        if (stats[3].fixedLevel != newInfo.hitpoints) stats[3] = new Stat(newInfo.hitpoints);
        if (stats[4].fixedLevel != newInfo.ranged) stats[4] = new Stat(newInfo.ranged);
        if (stats[6].fixedLevel != newInfo.magic) stats[6] = new Stat(newInfo.magic);

        this.bonuses = new int[] { // bonuses cannot be changed so we can just set to the new ones
                /*
                 * Attack bonuses
                */
                info.stab_attack,
                info.slash_attack,
                info.crush_attack,
                info.magic_attack,
                info.ranged_attack,
                /*
                 * Defence bonuses
                */
                info.stab_defence,
                info.slash_defence,
                info.crush_defence,
                info.magic_defence,
                info.ranged_defence,
        };
    }

    /**
     * Following
     */
    public final void follow0() {
        checkAggression();
        if(target == null || npc.isLocked()) //why can an npc be locked but still have a target.. hmm..
            return;
        if(!canAttack(target)) {
            reset();
            return;
        }
        follow();
    }

    protected void follow(int distance) {
        DumbRoute.step(npc, target, distance);
    }

    protected boolean withinDistance(int distance) {
        return DumbRoute.withinDistance(npc, target, distance);
    }

    /**
     * Attacking
     */
    public final void attack0() {
        if(target == null || hasAttackDelay() || npc.isLocked() || !attack())
            return;
        updateLastAttack(info.attack_ticks);
    }

    public boolean canAttack(Entity target) {
        if(isDead())
            return false;
        if(target == null || target.isHidden())
            return false;
        if(target.getCombat() == null)
            return false;
        if(target.getCombat().isDead())
            return false;
        if (!multiCheck(target))
            return false;
        if(npc.targetPlayer == null) {
            if(!npc.getPosition().isWithinDistance(target.getPosition()))
                return false;
            Bounds attackBounds = npc.attackBounds;
            if(attackBounds != null && !npc.getPosition().inBounds(attackBounds)) {
                DumbRoute.route(npc, npc.spawnPosition.getX(), npc.spawnPosition.getY());
                //possibly consider resetting the monster to prevent abusing this mechanic
                return false;
            }
        }
        return true;
    }

    public boolean multiCheck(Entity target) {
        return npc.inMulti() || target.getCombat().allowPj(npc);
    }

    protected Hit basicAttack() {
        return basicAttack(info.attack_animation, info.attack_style, info.max_damage);
    }

    protected Hit basicAttack(int animation, AttackStyle attackStyle, int maxDamage) {
        npc.animate(animation);
        Hit hit = new Hit(npc, attackStyle, null).randDamage(maxDamage);
        target.hit(hit);
        return hit;
    }

    protected Hit projectileAttack(Projectile projectile, int animation, AttackStyle attackStyle, int maxDamage) {
        return projectileAttack(projectile, animation, Graphic.builder().id(-1).build(), attackStyle, maxDamage);
    }

    protected Hit projectileAttack(Projectile projectile, int animation, Graphic hitgfx, AttackStyle attackStyle, int maxDamage) {
        return projectileAttack(projectile, animation, hitgfx, Graphic.builder().id(-1).build(), attackStyle, maxDamage, false);
    }

    protected Hit projectileAttack(Projectile projectile, int animation, Graphic hitgfx, Graphic splashgfx, AttackStyle attackStyle, int maxDamage, boolean ignorePrayer) {
        if (animation != -1)
            npc.animate(animation);
        int delay = projectile.send(npc, target);
        Hit hit = new Hit(npc, attackStyle, null).randDamage(maxDamage).clientDelay(delay);
        hit.afterPostDamage(e -> {
            boolean splash = hit.isBlocked();
            if (target != null) {
                target.graphics(
                        splash ? splashgfx.getId() : hitgfx.getId(),
                        splash ? splashgfx.getHeight() : hitgfx.getHeight(),
                        splash ? splashgfx.getDelay() : hitgfx.getDelay()
                );
                if (splash ? splashgfx.getSoundId() != -1 : hitgfx.getSoundId() != -1) {
                    target.publicSound(
                            splash ? splashgfx.getSoundId() : hitgfx.getSoundId(),
                            splash ? splashgfx.getSoundType() : hitgfx.getSoundType(),
                            splash ? splashgfx.getSoundDelay() : hitgfx.getSoundDelay()
                    );
                }
            }
        });

        target.hit(hit);
        return hit;
    }

    /**
     * Reset
     */
    @Override
    public void reset() {
        super.reset();
        npc.faceNone(!isDead());
        TargetRoute.reset(npc);
    }

    /**
     * Death
     */
    @Override
    public void startDeath(Hit killHit) {
        setDead(true);

        if(target != null)
            reset();
        Killer killer = getKiller();
        if(npc.deathStartListener != null) {
            npc.deathStartListener.handle(npc, killer, killHit);
            if(npc.isRemoved())
                return;
        }
        npc.startEvent(event -> {
            npc.lock();
            event.delay(1);
            if(info.death_animation != -1)
                npc.animate(info.death_animation);
            if(info.death_ticks > 0)
                event.delay(info.death_ticks);
            dropItems(killer);

            if (killer != null && killer.player != null) {
                Slayer.onNPCKill(killer.player, npc);
                if (npc.getDef().killCounter != null)
                    npc.getDef().killCounter.apply(killer.player).increment(killer.player);
                if(info.pet != null && Random.rollDie(info.pet.dropAverage))
                    info.pet.unlock(killer.player);
                DailyTask.checkNPCKill(killer.player, npc);
                DemonSlayer.check(killer.player, npc);
            }

            if(npc.deathEndListener != null) {
                npc.deathEndListener.handle(npc, killer, killHit);
                if(npc.isRemoved())
                    return;
            } else if(info.respawn_ticks < 0) {
                npc.remove();
                return;
            }

            if (info.respawn_ticks > 0)
                npc.setHidden(true);
            if (!allowRespawn())
                return;

            event.delay(info.respawn_ticks);
            respawn();
        });
    }

    public final void respawn() {
        npc.animate(info.spawn_animation);
        npc.getPosition().copy(npc.spawnPosition);
        resetKillers();
        restore();
        setDead(false);
        npc.setHidden(false);
        if(npc.respawnListener != null)
            npc.respawnListener.onRespawn(npc);
        npc.unlock();
    }

    public void setAllowRespawn(boolean allowRespawn) {
        this.allowRespawn = allowRespawn;
    }

    public boolean allowRespawn() {
        return allowRespawn;
    }

    public void dropItems(Killer killer) {
        NPCDef def = npc.getDef();
        Position dropPosition = getDropPosition();
        Player pKiller = killer == null ? null : killer.player;
        if (pKiller == null) {
            return;
        }

        /*
         * Drop table loots
         */
        LootTable t = def.lootTable;
        if(t != null) {
            int rolls = DoubleDrops.getRolls(killer.player);
            for(int i = 0; i < rolls; i++) {
                List<Item> items = t.rollItems(i == 0);
                if(items != null) {
                    handleDrop(killer, dropPosition, pKiller, items);
                }
            }
        }

        /*
         * Handle giving player vorkaths head after 50 kills.
         */
        vorkathHead(dropPosition, pKiller);

        /*
         * Gives players PVM Points
         */
        PvmPoints.addPoints(pKiller, npc);
        /*
         * Casket loots
         */
        GoldCasket.drop(pKiller, npc, dropPosition);
        /*
         * Summer Loot
         */
        SummerTokens.npcKill(pKiller, npc, dropPosition);
        /*
         * Catacombs loot
         */
        KourendCatacombs.drop(pKiller, npc, dropPosition);

        /*
         * Roll for OSRS wilderness key
         */
        if(World.wildernessKeyEvent)
            WildernessKey.rollForWildernessBossKill(pKiller, npc);

        /*
         * PvP Item loots
         */
        Wilderness.rollPvPItemDrop(pKiller, npc, dropPosition);

        /*
         * Roll for wilderness clue key
         */
        Wilderness.rollClueKeyDrop(pKiller, npc, dropPosition);

        /*
         * Blood Money
         */
        Wilderness.bloodMoneyDrop(pKiller, npc);

        /*
         * Resource packs
         */
        Wilderness.resourcePackWithBoss(pKiller, npc);

    }

    private void vorkathHead(Position dropPosition, Player pKiller) {
        if (pKiller.vorkathKills.getKills() >= 50 && !pKiller.obtained50KCVorkathHead) {
            Item item = new Item(VORKATHS_HEAD);
            GroundItem groundItem = new GroundItem(item).position(dropPosition);
            groundItem.spawn();
            pKiller.obtained50KCVorkathHead = true;
        }
    }

    private void handleDrop(Killer killer, Position dropPosition, Player pKiller, List<Item> items) {
        for(Item item : items) {
            if (item.getId() == 21820) {
                if (BraceletOfEthereum.handleEthereumDrop(pKiller, item)) {
                    continue;
                }
            }
            if (item.getId() == COINS_995) {
                if (RingOfWealth.check(pKiller, item)) {
                    pKiller.getInventory().addOrDrop(item);
                    continue;
                }
            }
            boolean dropItem = true;
            for(Item equipment : pKiller.getEquipment().getItems()) {
                if(equipment != null && equipment.getDef() != null) {
                    List<String> upgrades = AttributeExtensions.getEffectUpgrades(equipment);
                    boolean hasEffect = upgrades != null;
                    if (hasEffect) {
                        for (String s : upgrades) {
                            try {
                                if (s.equalsIgnoreCase("empty"))
                                    continue;
                                ItemEffect effect = ItemEffect.valueOf(s);
                                dropItem = effect.getUpgrade().modifyDroppedItem(pKiller, item);
                            } catch (Exception ex) {
                                System.err.println("Unknown upgrade { " + s + " } found!");
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
            /*
             * Donator Benefit: [Noted dragon bones in wilderness]
             */
            if (item.getId() == 534 || item.getId() == 536 || item.getId() == 6812 || item.getId() == 11943 || item.getId() == 22124) {
                if (pKiller.isSapphire() && pKiller.wildernessLevel > 0) {
                    item.setId(item.getDef().notedId);
                }
            }

            /*
             * Donator Benefit: [Noted herbs in wilderness]
             */
            if (item.getDef().name.toLowerCase().contains("grimy")) {
                if (pKiller.isDiamond() && pKiller.wildernessLevel > 0) {
                    if (item.getDef().notedId > -1)
                        item.setId(item.getDef().notedId);
                }
            }

            if (item.getDef().name.toLowerCase().contains("statius") ||
                    item.getDef().name.toLowerCase().contains("vesta") ||
                    item.getDef().name.toLowerCase().contains("zuriel")) {
                pKiller.sendMessage("You have been red skulled and tele-blocked because of your loot!");
                pKiller.getCombat().skullHighRisk();
                pKiller.getCombat().teleblock();
            }

            /*
             * Modify drop for specific npc
             */
            if(npc.dropListener != null)
                npc.dropListener.dropping(killer, item);

            /*
             * Global Broadcast
             */
            if(item.lootBroadcast != null || item.getDef().dropAnnounce) {
                getRareDropAnnounce(pKiller, item);
            }

            /*
             * Local Broadcast!
             */
            if (info.local_loot) {
                getLocalAnnounce(pKiller, item);
            }

            /*
             * Spawn the item on the ground.
             */
            if (dropItem)
            new GroundItem(item).position(dropPosition).owner(pKiller).spawn();
        }
    }

    private void getRareDropAnnounce(Player pKiller, Item item) {
        int amount = item.getAmount();
        String message = pKiller.getName() + " just received ";
        if(amount > 1)
            message += NumberUtils.formatNumber(amount) + " x " + item.getDef().name;
        else
            message += item.getDef().descriptiveName;
        if (item.lootBroadcast != null) {
            item.lootBroadcast.sendNews(pKiller, message + " from " + npc.getDef().descriptiveName + "!");
        } else {
            Broadcast.GLOBAL.sendNews(pKiller, message + " from " + npc.getDef().descriptiveName + "!");
        }
        RareDropEmbedMessage.sendDiscordMessage(message, npc.getDef().descriptiveName, item.getId());
    }

    private void getLocalAnnounce(Player pKiller, Item item) {
        npc.localPlayers().forEach(p -> p.sendMessage(Color.DARK_GREEN.wrap(pKiller.getName() + " received a drop: " + NumberUtils.formatNumber(item.getAmount()) + " x " + item.getDef().name)));
    }

    public Position getDropPosition() {
        return npc.getPosition();
    }

    public void restore() {
        for(Stat stat : stats)
            stat.restore();
        npc.resetFreeze();
        npc.cureVenom(0);
    }

    /**
     * Misc
     */
    @Override
    public boolean allowRetaliate(Entity attacker) {
        if(npc.targetPlayer != null && attacker != npc.targetPlayer) //npc has a designated target
            return false;
        if (npc.isLocked())
            return false;
        if (!allowRetaliate)
            return false;
        if(target != null && target.getCombat().getTarget() == npc) { //npc is being attacked by target
            boolean prioritizePlayer = target.npc != null && attacker.player != null; //target is an npc and attacker is a player
            if(!prioritizePlayer)
                return false;
        }
        return true;
    }

    @Override
    public AttackStyle getAttackStyle() {
        return info.attack_style;
    }

    @Override
    public AttackType getAttackType() {
        return null;
    }

    @Override
    public double getLevel(StatType statType) {
        int i = statType.ordinal();
        return i >= stats.length ? 0 : stats[i].currentLevel;
    }

    public Stat getStat(StatType statType) {
        return stats[statType.ordinal()];
    }

    @Override
    public double getBonus(int bonusType) {
        return bonusType >= bonuses.length ? 0 : bonuses[bonusType];
    }

    @Override
    public Killer getKiller() {
        if(npc.targetPlayer != null) {
            Killer killer = new Killer();
            killer.player = npc.targetPlayer;
            return killer;
        }
        //Player's didn't like this mechanic so we removed it.
       /* if (killers.entrySet().stream().filter(e -> e.getValue().player != null).anyMatch(e -> e.getValue().player.getGameMode().isIronMan()) // ironman did damage
                && killers.entrySet().stream().filter(e -> e.getValue().player != null).anyMatch(e -> !e.getValue().player.getGameMode().isIronMan())) { // but so did a non-ironman :(
            killers.entrySet().removeIf(e -> e.getValue().player != null && e.getValue().player.getGameMode().isIronMan()); // remove all iron men from potential killer list
        }*/
        return super.getKiller();
    }

    @Override
    public int getDefendAnimation() {
        return info.defend_animation;
    }

    public int getMaxDamage() {
        return info.max_damage;
    }

    public npc_combat.Info getInfo() {
        return info;
    }

    @Override
    public double getDragonfireResistance() {
        return 0;
    }

    public final void checkAggression() {
        if (target == null && isAggressive()) {
            target = findAggressionTarget();
            if (target != null)
                faceTarget();
        }
    }

    protected Entity findAggressionTarget() {
        if (npc.localPlayers().isEmpty())
            return null;
        if (npc.hasTarget())
            return null;
        List<Player> targets = npc.localPlayers().stream()
                .filter(this::canAggro)
                .collect(Collectors.toList()); // i don't mind if this is done in a different way as long as it picks a RANDOM target that passes the canAggro check
        if (targets.isEmpty())
            return null;
        return Random.get(targets);
    }

    protected int getAggressiveLevel() {
        if (npc.wildernessSpawnLevel > 0)
            return Integer.MAX_VALUE;
        else if (info.aggressive_level == -1)
            return npc.getDef().combatLevel * 2;
        else
            return info.aggressive_level;
    }

    public boolean isAggressive() {
        return getInfo().aggressive_level != 0;
    }

    protected boolean canAggro(Player player) {
        int aggroLevel = getAggressiveLevel();
        return player.getCombat().getLevel() <= aggroLevel // in level range
                && canAttack(player) // can attack
                && !player.isIdle // player isn't idle
                && (player.inMulti() || !player.getCombat().isDefending(12))
                && DumbRoute.withinDistance(npc, player, getAggressionRange()) // distance and line of sight
                && (npc.inMulti() || (StreamSupport.stream(npc.localNpcs().spliterator(), false)
                .noneMatch(n -> n.getCombat() != null && n.getCombat().getTarget() == player && !n.getCombat().isAttacking(10) && !n.getMovement().isAtDestination()))) // not 100% sure how i feel about this check, but it ensures multiple npcs don't try to go for the same player at the same time in a single-way zone since they wouldn't be able to attack upon reaching
                && (npc.aggressionImmunity == null || !npc.aggressionImmunity.test(player));

    }

    public int getAggressionRange() {
        return npc.wildernessSpawnLevel > 0 ? 2 : 4;
    }

    public int getAttackBoundsRange() {
        return 12;
    }

    @Override
    public void faceTarget() {
        npc.face(target);
    }

    /**
     * Handler functions
     */
    public abstract void init();

    public abstract void follow();

    public abstract boolean attack();

    public int getRandomDropCount() {
        return info.random_drop_count;
    }
}