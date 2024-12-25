package io.ruin.model.skills.construction.servants;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.actions.edgeville.SawmillOperator;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.model.skills.construction.House;
import io.ruin.model.skills.construction.Material;
import io.ruin.model.skills.prayer.Bone;
import io.ruin.process.event.Event;
import kilim.Pausable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.ruin.cache.ItemID.COINS_995;


public class Servant extends NPC {

    private static final int ACTIONS_FOR_PAYMENT = 8;

    private enum State {
        IDLE,
        GREETING,
        FOLLOWING,
        FETCHING
    }

    private final ServantSave save;
    private final House house;
    private State state;

    public Servant(ServantSave save, House house) {
        super(save.getHiredServant().getNpcId());
        this.save = save;
        this.house = house;
        this.state = State.IDLE;
        addEvent(event -> {
            while (true) {
                if (tick(event)) {
                    event.delay(1);
                } else
                    break;
            }
        });
        NPCAction.register(this, 1, (player, npc) -> interact(player));
        ItemNPCAction.register(this, (player1, item, npc1) -> itemUsed(player1, item));
    }

    private void itemUsed(Player player, Item item) {
        if (!player.isInOwnHouse()) {
            faceTemp(player);
            player.dialogue(new NPCDialogue(getId(), getDefinition().getOwnerOnlyLine()));
            return;
        }
        if (save.getActions() >= ACTIONS_FOR_PAYMENT && !takePaymentFromMoneybag()) {
            faceTemp(player);
            player.dialogue(new NPCDialogue(getId(), getDefinition().getPaymentRequiredLine()),
                    new OptionsDialogue("Choose an option",
                            new Option("Pay the servant.", () -> manualPayment(player)),
                            new Option("No.")
                    )
            );
            return;
        }
        List<Option> options = new ArrayList<>(3);
        if (canUnnote(item)) {
            options.add(new Option("Un-note it", () -> player.integerInput("Enter amount:", amt -> unnote(player, item, amt))));
        }
        SawmillOperator.Plank plankType = SawmillOperator.Plank.getFromLog(item.getId());
        if (getDefinition().isSawmill() && plankType != null) {
            options.add(new Option("Take it to the sawmill", () -> {
                player.integerInput("Enter how many planks to make:", amount -> makePlanks(player, item.getId(), amount));
            }));
        }
        options.add(new Option("Deposit it in bank", () -> player.integerInput("Enter amount to deposit:", amount -> deposit(player, item.getId(), amount))));
        if (options.size() == 1)
            options.get(0).consumer.accept(player);
        else {
            player.dialogue(new OptionsDialogue("Choose an option", options));
        }

    }

    private void inventoryFullDialogue(Player player) {
        int amount = getInventory().getCount();
        player.dialogue(new NPCDialogue(getId(), getDefinition().getInventoryFullLine(amount)),
                new OptionsDialogue("Choose an option",
                        new Option("Hold on to them for now", () -> player.dialogue(new NPCDialogue(getId(), getDefinition().getAgreeLine()))),
                        new Option("Take them to the bank", () -> player.dialogue(new NPCDialogue(getId(), getDefinition().getAgreeLine()), new ActionDialogue(this::depositInventory)))
                )
        );
    }

    private void depositInventory() {
        addEvent(event -> {
            state = State.FETCHING;
            setHidden(true);
            event.delay(getDefinition().getTripTime());
            if (!player.isInOwnHouse()) {
                setHidden(false);
                return;
            }
            getMovement().teleport(player.getPosition());
            setHidden(false);
            getRouteFinder().routeEntity(player);
            player.getBank().deposit(getInventory(), false);
            if (!getInventory().isEmpty()) {
                inventoryFullDialogue(player);
            }
            save.incrementActions();
            startFollowing();
        });
    }

    private boolean canUnnote(Item item) {
        return item.getDef().isNote() && Bone.get(item.getDef().fromNote().id) == null;
    }

    private void interact(Player player) {
        if (!player.isInOwnHouse()) {
            faceTemp(player);
            player.dialogue(new NPCDialogue(getId(), getDefinition().getOwnerOnlyLine()));
            return;
        }
        if (!getInventory().isEmpty()) {// are we holding items? if yes, try to give them
            if (!giveInventory(player)) {
                faceTemp(player);
                player.dialogue(new NPCDialogue(getId(), getDefinition().getStillHaveItemsLine(getInventory().getCount())));
                return;
            }
        }
        if (save.getActions() >= ACTIONS_FOR_PAYMENT && !takePaymentFromMoneybag()) { // do we need to get paid?
            faceTemp(player);
            player.dialogue(new NPCDialogue(getId(), getDefinition().getPaymentRequiredLine()),
                    new OptionsDialogue("Choose an option",
                            new Option("Pay the servant.", () -> manualPayment(player)),
                            new Option("No.")
                    )
            );
            return;
        }
        if (save.getLastAction() != null) { // give option to repeat last task with only 1 click
            player.dialogue(new OptionsDialogue("Choose an option",
                    new Option("Repeat last task: " + save.getLastAction().toString(save.getLastActionItemId(), save.getLastActionItemAmount()), this::repeatLast),
                    new Option("Something else...", () -> showOptions(player))
            ));
        } else {
            showOptions(player);
        }

    }

    private void showOptions(Player player) {
        player.dialogue(new OptionsDialogue("Choose a task",
                        new Option(state == State.FOLLOWING ? "Stop following me" : "Follow me", () -> {
                            if (state == State.FOLLOWING)
                                stopFollowing();
                            else
                                startFollowing();
                            player.dialogue(new NPCDialogue(getId(), getDefinition().getAgreeLine()));
                        }),
                        new Option("Take something to the bank", () -> {
                            player.dialogue(new NPCDialogue(getId(), getDefinition().getDepositExplainLine()));
                        }),
                        new Option("Un-note an item", () -> {
                            player.dialogue(new NPCDialogue(getId(), getDefinition().getDepositExplainLine()));
                        }),
                        new Option("Bring something from the bank", () -> configWithdraw(player))
                )
        );
    }

    /**
     * @return true if ALL items were given, false otherwise
     */
    private boolean giveInventory(Player player) {
        for (Item item : getInventory().getItems()) {
            if (item != null) {
                item.move(item.getId(), item.getAmount(), player.getInventory());
            }
        }
        return getInventory().isEmpty();
    }

    private boolean tick(Event event) throws Pausable {
        switch (state) {
            case IDLE:
            case GREETING:
                return true;
            case FOLLOWING:
                follow(event);
                return true;
            case FETCHING:
                return true;
            default:
                return false;
        }
    }

    private void follow(Event event) throws Pausable {
        Player owner = house.getOwner();
        if (!owner.isInOwnHouse()) {
            state = State.IDLE;
            return;
        }
        if (owner.getHeight() != getHeight()) {
            getMovement().teleport(owner.getPosition());
            event.delay(1);
        }
        face(owner);
        int destX, destY;
        if (owner.getMovement().hasMoved()) {
            destX = owner.getMovement().lastFollowX;
            destY = owner.getMovement().lastFollowY;
        } else {
            destX = owner.getMovement().followX;
            destY = owner.getMovement().followY;
        }
        if (destX == -1 || destY == -1)
            DumbRoute.step(this, owner, 1);
        else if (!npc.isAt(destX, destY))
            DumbRoute.step(this, destX, destY);
    }

    public void startFollowing() {
        state = State.FOLLOWING;
        face(house.getOwner());
    }

    public void stopFollowing() {
        state = State.IDLE;
        faceNone(false);
        getMovement().teleport(house.localToAbs(house.getEntryPosition()));
    }

    public ServantDefinition getDefinition() {
        return save.getHiredServant();
    }

    @Override
    public boolean isRandomWalkAllowed() {
        return super.isRandomWalkAllowed() && state == State.IDLE;
    }

    public ItemContainer getInventory() {
        return save.getInventory();
    }

    private void manualPayment(Player player) {
        if (player.getInventory().contains(COINS_995, getDefinition().getPayment())) {
            player.getInventory().remove(COINS_995, getDefinition().getPayment());
            save.resetActions();
            player.dialogue(new PlayerDialogue("Okay, here's your payment."),
                    new NPCDialogue(getId(), getDefinition().getThankYouLine()));
        } else {
            player.dialogue(new MessageDialogue("You don't have enough coins. Your servant requires " + NumberUtils.formatNumber(getDefinition().getPayment()) + " coins as payment."));
        }
    }

    private boolean takePaymentFromMoneybag() {
        if (house.hasServantsMoneybag() && house.getMoneyInMoneybag() >= getDefinition().getPayment()) {
            house.setMoneyInMoneybag(house.getMoneyInMoneybag() - getDefinition().getPayment());
            if (house.getMoneyInMoneybag() > getDefinition().getPayment())
                house.getOwner().sendMessage("Your servant has taken their payment from the moneybag. The moneybag has " + NumberUtils.formatNumber(house.getMoneyInMoneybag()) + " coins left in it.");
            else if (house.getMoneyInMoneybag() > 0)
                house.getOwner().sendMessage("<col=ff0000>Your servant has taken their payment from the moneybag. The moneybag has " + NumberUtils.formatNumber(house.getMoneyInMoneybag()) + " coins left in it, which will not be enough for the servant's next payment.");
            else
                house.getOwner().sendMessage("<col=ff0000>Your servant has taken their payment from the moneybag. The moneybag is now empty.");
            save.resetActions();
            return true;
        }
        return false;
    }

    private void repeatLast(Player player) {
        int id = save.getLastActionItemId();
        int amount = save.getLastActionItemAmount();
        switch (save.getLastAction()) {
            case WITHDRAW:
                withdrawFromBank(player, id, amount);
                break;
            case TAKE_TO_SAWMILL:
                makePlanks(player, id, amount);
                break;
            case UNNOTE:
                unnote(player, new Item(id, amount), amount);
                break;
            case DEPOSIT:
                deposit(player, id, amount);
                break;
        }
    }

    private void makePlanks(Player player, int logs, int amount) {
        SawmillOperator.Plank plank = SawmillOperator.Plank.getFromLog(logs);
        if (plank == null)
            return;
        if (!player.getInventory().contains(COINS_995, plank.cost)) {
            player.dialogue(new NPCDialogue(getId(), getDefinition().getNeedMoneyForSawmillLine()));
            return;
        }
        if (!player.getInventory().contains(plank.woodId, 1)) {
            player.dialogue(new MessageDialogue("You don't have any logs."));
            return;
        }
        int finalAmount = Math.min(Math.min(player.getInventory().getAmount(plank.woodId), player.getInventory().getAmount(COINS_995) / plank.cost), amount);
        if (finalAmount <= 0)
            return;
        player.getInventory().remove(plank.woodId, finalAmount);
        player.getInventory().remove(COINS_995, finalAmount * plank.cost);
        startEvent(event -> {
            state = State.FETCHING;
            setHidden(true);
            event.delay(getDefinition().getTripTime());
            if (!player.isInOwnHouse()) {
                setHidden(false);
                return;
            }
            getMovement().teleport(player.getPosition());
            setHidden(false);
            getRouteFinder().routeEntity(player);
            int toPlayer = Math.min(finalAmount, player.getInventory().getFreeSlots());
            int toServant = finalAmount - toPlayer;
            if (toPlayer > 0)
                player.getInventory().add(plank.plankId, toPlayer);
            if (toServant > 0) {
                getInventory().add(plank.plankId, toServant);
                inventoryFullDialogue(player);
            }
            save.incrementActions();
            save.setLastAction(ServantAction.TAKE_TO_SAWMILL);
            save.setLastActionItemId(plank.woodId);
            save.setLastActionItemAmount(finalAmount);
            startFollowing();
        });

    }

    //items that servants can fetch from the bank
    private static final List<Material> BANK_ITEMS = Arrays.asList(Material.REGULAR_PLANK, Material.OAK_PLANK, Material.TEAK_PLANK, Material.MAHOGANY_PLANK, Material.SOFT_CLAY, Material.LIMESTONE_BRICK, Material.STEEL_BAR, Material.BOLT_OF_CLOTH, Material.GOLD_LEAF, Material.MARBLE_BLOCK, Material.MAGIC_STONE);

    private void configWithdraw(Player player) {
        startFollowing();
        OptionScroll.open(player, "Choose a material", true, BANK_ITEMS.stream().map(mat -> new Option(ItemDef.get(mat.getItemId()).name, () -> {
            player.integerInput("Enter amount:", amount -> withdrawFromBank(player, mat.getItemId(), amount));
        })).collect(Collectors.toList()));
    }
    private void withdrawFromBank(Player player, int id, int amount) {
        if (amount <= 0)
            return;
        int bankAmount = player.getBank().getAmount(id);
        if (bankAmount == 0) {
            player.dialogue(new NPCDialogue(getId(), getDefinition().getItemNotFoundLine(player.getAppearance().isMale())));
            return;
        }
        if (amount > getDefinition().getItemCapacity()) {
            player.dialogue(new NPCDialogue(getId(), getDefinition().getTooManyItemsRequestedLine()));
        }
        amount = Math.min(amount, Math.min(getDefinition().getItemCapacity(), bankAmount));
        if (amount == 0) { // ????
            return;
        }
        int finalAmount = amount;
        addEvent(event -> {
            state = State.FETCHING;
            setHidden(true);
            event.delay(getDefinition().getTripTime());
            if (!player.isInOwnHouse()) {
                setHidden(false);
                return;
            }
            getMovement().teleport(player.getPosition());
            setHidden(false);
            getRouteFinder().routeEntity(player);
            player.getBank().remove(id, finalAmount);
            int toPlayer = Math.min(finalAmount, player.getInventory().getFreeSlots());
            int toServant = finalAmount - toPlayer;
            if (toPlayer > 0)
                player.getInventory().add(id, toPlayer);
            if (toServant > 0) {
                getInventory().add(id, toServant);
                inventoryFullDialogue(player);
            }
            save.incrementActions();
            save.setLastAction(ServantAction.WITHDRAW);
            save.setLastActionItemId(id);
            save.setLastActionItemAmount(finalAmount);
            startFollowing();
        });
    }

    private void deposit(Player player, int id, int amount) {
        if (amount <= 0)
            return;
        if (amount > getDefinition().getItemCapacity()) {
            player.dialogue(new NPCDialogue(getId(), getDefinition().getTooManyItemsRequestedLine()));
        }
        amount = Math.min(amount, Math.min(getDefinition().getItemCapacity(), player.getInventory().getAmount(id)));
        if (amount == 0) { // ????
            return;
        }
        int finalAmount = amount;
        player.getInventory().remove(id, amount);
        addEvent(event -> {
            state = State.FETCHING;
            setHidden(true);
            event.delay(getDefinition().getTripTime());
            if (!player.isInOwnHouse()) {
                setHidden(false);
                return;
            }
            getMovement().teleport(player.getPosition());
            setHidden(false);
            getRouteFinder().routeEntity(player);
            int deposited = player.getBank().add(id, finalAmount);
            int toServant = finalAmount - deposited;
            if (toServant > 0) {
                getInventory().add(id, toServant);
                player.dialogue(new NPCDialogue(getId(), getDefinition().getBankFullLine()));
            }
            save.incrementActions();
            save.setLastAction(ServantAction.DEPOSIT);
            save.setLastActionItemId(id);
            save.setLastActionItemAmount(finalAmount);
            startFollowing();
        });
    }

    private void unnote(Player player, Item item, int amount) {
        if (amount <= 0 || !canUnnote(item))
            return;
        int finalAmount = Math.min(getDefinition().getItemCapacity(), Math.min(player.getInventory().getAmount(item.getId()), amount));
        final int id = item.getDef().fromNote().id;
        player.getInventory().remove(item.getId(), finalAmount);
        addEvent(event -> {
            state = State.FETCHING;
            setHidden(true);
            event.delay(getDefinition().getTripTime());
            if (!player.isInOwnHouse()) {
                setHidden(false);
                return;
            }
            getMovement().teleport(player.getPosition());
            setHidden(false);
            getRouteFinder().routeEntity(player);
            int toPlayer = Math.min(finalAmount, player.getInventory().getFreeSlots());
            int toServant = finalAmount - toPlayer;
            if (toPlayer > 0)
                player.getInventory().add(id, toPlayer);
            if (toServant > 0) {
                getInventory().add(id, toServant);
                inventoryFullDialogue(player);
            }
            save.incrementActions();
            save.setLastAction(ServantAction.UNNOTE);
            save.setLastActionItemId(id);
            save.setLastActionItemAmount(finalAmount);
            startFollowing();
        });
    }
}
