package io.ruin.model.skills.construction.servants;

import io.ruin.cache.NPCDef;

public enum ServantDefinition {

    RICK(222, 221, 1, 20, 500, false, 6, 100),
    MAID(224, 223, 3, 25, 1_000, false, 10, 50),
    COOK(226, 225, 5, 30, 3_000, true, 16, 28),
    BUTLER(228, 227, 6, 40, 5_000, true, 20, 20),
    DEMON_BUTLER(230, 229, 8, 50, 10_000, true, 26, 12);

    private final int guildId, npcId;
    private final int varpbitValue;
    private final int levelReq;
    private final int payment;
    private final boolean sawmill;
    private final int itemCapacity;
    private final int tripTime;

    ServantDefinition(int guildId, int npcId, int varpbitValue, int levelReq, int payment, boolean sawmill, int itemCapacity, int tripTime) {
        this.guildId = guildId;
        this.npcId = npcId;
        this.varpbitValue = varpbitValue;
        this.levelReq = levelReq;
        this.payment = payment;
        this.sawmill = sawmill;
        this.itemCapacity = itemCapacity;
        this.tripTime = tripTime;
    }

    public int getItemCapacity() {
        return itemCapacity;
    }

    public boolean isSawmill() {
        return sawmill;
    }

    public int getTripTime() {
        return tripTime;
    }

    public int getPayment() {
        return payment;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public int getVarpbitValue() {
        return varpbitValue;
    }

    public int getNpcId() {
        return npcId;
    }

    public int getGuildId() {
        return guildId;
    }

    public String getOwnerOnlyLine() {
        return this == DEMON_BUTLER ? demonify("I only serve my master.") : "Sorry, I've been told to only serve the house owner.";
    }

    public String getDepositItemLine() {
        return this == DEMON_BUTLER ? demonify("I shall fly on wings of unholy flame to deposit your treasured possessions where they will be safe from all worldly harm!") : "Okay, I'll take your items to the bank.";
    }

    public String getItemNotFoundLine(boolean male) {
        return this == DEMON_BUTLER ? demonify("My " + (male ? "Lord" : "Lady") + ", I cannot conjure items out of the air. You do not have any of those items in your bank!") : (male ? "Sir" : "Madam") + ", you don't have any of those items in your bank.";
    }

    public String getItemFoundLine() {
        return this == DEMON_BUTLER ? demonify("Master, I have returned with what you asked me to retrieve.") : "Here you go.";
    }

    public String getTooManyItemsRequestedLine() {
        return this == DEMON_BUTLER ? demonify("Master, I dearly wish that I could perform your instruction in full, but alas, I can only carry 26 items.") : "Sorry, I can only carry " + itemCapacity + " items.";
    }

    public String getInventoryFullLine(int amount) {
        return this == DEMON_BUTLER ? demonify("Master, I have returned with what thou asked me to retrieve. As I see thy inventory is full, I shall wait with these " + amount + " items until thou art ready.") : "I've returned with your items. Your inventory is full, so I'll hold on to these " + amount + " items until you have some free space.";
    }

    public String getStillHaveItemsLine(int amount) {
        return this == DEMON_BUTLER ? demonify("Master, I am still carrying " + amount + " of thy items. Thou must take them before I may perform another service.") : "I'm still holding " + amount + " of your items, please free up some space.";
    }

    public String getAgreeLine() {
        return this == DEMON_BUTLER ? demonify("Very well, Master.") : "As you wish.";
    }

    public String getPaymentRequiredLine() {
        return this == DEMON_BUTLER ? demonify("Master, I must humbly request payment for mine services.") : "I will need you to pay for my services before I can continue.";
    }

    public String getThankYouLine() {
        return this == DEMON_BUTLER ? demonify("Thank you, Master.") : "Thank you.";
    }

    public String getNeedMoneyForSawmillLine() {
        return this == DEMON_BUTLER ? demonify("Master, I will require coins to compensate the sawmill operator.") : "I'll need some money to pay the sawmill operator.";
    }


    public String getDepositExplainLine() {
        return this == DEMON_BUTLER ? demonify("Master, use whatever thou desire be taken to storage on myself and I shall transport it") : "Use the item on me and I'll take it to the bank.";
    }

    public String getBankFullLine() {
        return this == DEMON_BUTLER ? demonify("Master, I have failed to safely store thy items, as thy storage is currently at maximum capacity.") : "I couldn't deposit all of your items, as your bank is full.";
    }

    public static String demonify(String text) {
        return "<col=433e87>" + text + "</col>";
    }

    static {
        for (ServantDefinition def : values()) {
            NPCDef.get(def.getNpcId()).ignoreOccupiedTiles = true;
            NPCDef.get(def.getNpcId()).occupyTiles = false;
        }
    }
}
