package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("hj")
public enum PlayerType implements Enumerated {

    PlayerType_normal(0, -1, false, true),
    PlayerType_playerModerator(1, 0, true, true),
    PlayerType_jagexModerator(2, 1, true, false),
    PlayerType_ironman(3, 2, false, true),
    PlayerType_ultimateIronman(4, 3, false, true),
    PlayerType_hardcoreIronman(5, 10, false, true),

    DEVELOPER(6, 16, false, true),
    COMMUNITY_MANAGER(7, 17, false, true),
    SUPPORT(8, 18, false, true),
    SAPPHIRE(9, 19, false, true),
    EMERALD(10, 20, false, true),
    RUBY(11, 21, false, true),
    DIAMOND(12, 22, false, true),
    DRAGONSTONE(13, 23, false, true),
    ONYX(14, 24, false, true),
    ZENYTE(15, 25, false, true),
    GROUP_IRONMAN(16, 26, false, true),
    YOUTUBER(17, 27, false, true);

    final int id;
    public final boolean isPrivileged;
    public final boolean isUser;
    public final int modIcon;

    PlayerType(int id, int modIcon, boolean isPriviledged, boolean isUser) {
        this.id = id;
        this.modIcon = modIcon;
        this.isPrivileged = isPriviledged;
        this.isUser = isUser;
    }

    @ObfuscatedName("v")
    @ObfuscatedSignature(
            signature = "(S)I",
            garbageValue = "221"
    )
    public int getId() {
        return this.id;
    }

    @ObfuscatedName("fe")
    @ObfuscatedSignature(
            signature = "(ZI)V",
            garbageValue = "433120035"
    )
    static final void method3947(boolean var0) {
        if(var0) {
            Client.loginType = Login.field778?class158.field1950:class158.field1951;
        } else {
            Client.loginType = AbstractArchive.clientPreferences.parameters.containsKey(Integer.valueOf(class292.method5270(Login.Login_username)))?class158.field1953:class158.field1959;
        }

    }

    @ObfuscatedName("gb")
    @ObfuscatedSignature(
            signature = "(ZLkf;I)V",
            garbageValue = "-100712701"
    )
    static final void method3945(boolean var0, PacketBuffer var1) {
        Client.isInInstance = var0;
        int var2;
        int var4;
        int var5;
        int var6;
        int var7;
        int var8;
        if(!Client.isInInstance) {
            var2 = var1.readLEShortA();
            int var3 = var1.readLEShort();
            var4 = var1.readUnsignedShort();
            class289.xteaKeys = new int[var4][4];

            for(var5 = 0; var5 < var4; ++var5) {
                for(var6 = 0; var6 < 4; ++var6) {
                    class289.xteaKeys[var5][var6] = var1.readInt();
                }
            }

            MouseHandler.regions = new int[var4];
            class197.regionMapArchiveIds = new int[var4];
            class47.regionLandArchiveIds = new int[var4];
            WorldMapIcon_1.regionLandArchives = new byte[var4][];
            ArchiveLoader.regionMapArchives = new byte[var4][];
            boolean var16 = false;
            if((var2 / 8 == 48 || var2 / 8 == 49) && var3 / 8 == 48) {
                var16 = true;
            }

            if(var2 / 8 == 48 && var3 / 8 == 148) {
                var16 = true;
            }

            var4 = 0;

            for(var6 = (var2 - 6) / 8; var6 <= (var2 + 6) / 8; ++var6) {
                for(var7 = (var3 - 6) / 8; var7 <= (var3 + 6) / 8; ++var7) {
                    var8 = var7 + (var6 << 8);
                    if(!var16 || var7 != 49 && var7 != 149 && var7 != 147 && var6 != 50 && (var6 != 49 || var7 != 47)) {
                        MouseHandler.regions[var4] = var8;
                        class197.regionMapArchiveIds[var4] = class11.archive5.method4059("m" + var6 + "_" + var7);
                        class47.regionLandArchiveIds[var4] = class11.archive5.method4059("l" + var6 + "_" + var7);
                        ++var4;
                    }
                }
            }

            class306.method5763(var2, var3, true);
        } else {
            var2 = var1.readUnsignedShort();
            boolean var15 = var1.method5565() == 1;
            var4 = var1.readLEShortA();
            var5 = var1.readUnsignedShort();
            var1.method5293();

            int var9;
            for(var6 = 0; var6 < 4; ++var6) {
                for(var7 = 0; var7 < 13; ++var7) {
                    for(var8 = 0; var8 < 13; ++var8) {
                        var9 = var1.method5281(1);
                        if(var9 == 1) {
                            Client.instanceChunkTemplates[var6][var7][var8] = var1.method5281(26);
                        } else {
                            Client.instanceChunkTemplates[var6][var7][var8] = -1;
                        }
                    }
                }
            }

            var1.method5279();
            class289.xteaKeys = new int[var5][4];

            for(var6 = 0; var6 < var5; ++var6) {
                for(var7 = 0; var7 < 4; ++var7) {
                    class289.xteaKeys[var6][var7] = var1.readInt();
                }
            }

            MouseHandler.regions = new int[var5];
            class197.regionMapArchiveIds = new int[var5];
            class47.regionLandArchiveIds = new int[var5];
            WorldMapIcon_1.regionLandArchives = new byte[var5][];
            ArchiveLoader.regionMapArchives = new byte[var5][];
            var5 = 0;

            for(var6 = 0; var6 < 4; ++var6) {
                for(var7 = 0; var7 < 13; ++var7) {
                    for(var8 = 0; var8 < 13; ++var8) {
                        var9 = Client.instanceChunkTemplates[var6][var7][var8];
                        if(var9 != -1) {
                            int var10 = var9 >> 14 & 1023;
                            int var11 = var9 >> 3 & 2047;
                            int var12 = (var10 / 8 << 8) + var11 / 8;

                            int var13;
                            for(var13 = 0; var13 < var5; ++var13) {
                                if(MouseHandler.regions[var13] == var12) {
                                    var12 = -1;
                                    break;
                                }
                            }

                            if(var12 != -1) {
                                MouseHandler.regions[var5] = var12;
                                var13 = var12 >> 8 & 255;
                                int var14 = var12 & 255;
                                class197.regionMapArchiveIds[var5] = class11.archive5.method4059("m" + var13 + "_" + var14);
                                class47.regionLandArchiveIds[var5] = class11.archive5.method4059("l" + var13 + "_" + var14);
                                ++var5;
                            }
                        }
                    }
                }
            }

            class306.method5763(var4, var2, !var15);
        }

    }

    @ObfuscatedName("m")
    @ObfuscatedSignature(
            signature = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V",
            garbageValue = "1116997511"
    )
    static void method3939(String var0, String var1, String var2) {
        Login.Login_response1 = var0;
        Login.Login_response2 = var1;
        Login.Login_response3 = var2;
    }
}
