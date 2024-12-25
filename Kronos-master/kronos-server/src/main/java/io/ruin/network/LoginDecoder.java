package io.ruin.network;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.netty.MessageDecoder;
import io.ruin.api.protocol.Response;
import io.ruin.api.protocol.login.LoginInfo;
import io.ruin.api.utils.IPAddress;
import io.ruin.api.utils.Random;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.model.World;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerLogin;
import io.ruin.process.LoginWorker;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.regex.Pattern;

@Slf4j
public class LoginDecoder extends MessageDecoder<Channel> {

    public LoginDecoder() {
        super(null, false);
    }

    @Override
    protected void handle(Channel channel, InBuffer in, int opcode) {
        ServerWrapper.log("Incoming: " + IPAddress.get(channel));
        if(opcode == 14) {
            /*
             * Login handshake request
             */
            confirmHandshake(channel);
        } else if(opcode == 16 || opcode == 18) {
            /*
             * World login request
             */
            int revision = in.readInt();
            int subRevision = in.readInt();
            if (subRevision != SUB_REVISION) {
                /*
                 * Invalid client version.
                 */
                Response.GAME_UPDATED.send(channel);
                return;
            }

            in.skip(1);
            if (isAtWildernessLimitForIP(channel)) {
                /*
                 * Multiple of the same IP is already in the wilderness.
                 */
                Response.CONNECTION_LIMIT.send(channel);
                return;
            }
            if(revision == BUILD_HASH) {
                reconnecting = opcode == 18;
                LoginInfo loginInfo = decode(channel, in, World.id);
                if(loginInfo != null) {
                    LoginWorker.add(new PlayerLogin(loginInfo));
                }
            } else {
                /*
                 * Invalid client version.
                 */
                Response.GAME_UPDATED.send(channel);
            }
        }
    }

    @Override
    protected int getSize(int opcode) {
        switch(opcode) {
            case 14: return 0;          //login handshake request
            case 16: return VAR_SHORT;  //world login request
            case 18: return VAR_SHORT;  //world login request (from dc)
        }
        return UNHANDLED;
    }

    /**
     * Separator
     */

    private static final int BUILD_HASH = 184;
    private static final int SUB_REVISION = 5;

    private static final BigInteger EXPONENT = new BigInteger("83923922839219287732917015149195732073695536052984669036231778384894490439511932467277679466826619631057457711190000931928848746974072996674449163992099423404866521840525018174269929252811697346444185514151969525075984167323004389765704130745055780821036980587747264383960689940905074229888881011617738648897");

    private static final BigInteger MODULUS = new BigInteger("94210824259843347324509385276594109263523823612210415282840685497179394322370180677069205378760490069724955139827325518162089726630921395369270393801925644637806226306156731189625154078707248525519618118185550146216513714101970726787284175941436804270501308516733103597242337227056455402809871503542425244523");

    private static boolean reconnecting = false;

    private static void confirmHandshake(Channel channel) {
        channel.writeAndFlush(Unpooled.buffer(9).writeByte(0).writeLong(Random.getLong()));
    }

    private static LoginInfo decode(Channel channel, InBuffer in, int worldId) {
        int secureBufLength = in.readUnsignedShort();
        byte[] rsaPayload = new byte[secureBufLength];
        in.readBytes(rsaPayload);
        BigInteger rsaValue = new BigInteger(rsaPayload).modPow(EXPONENT, MODULUS);
        InBuffer rsaBuffer = new InBuffer(rsaValue.toByteArray());

        boolean successfulEncryption = rsaBuffer.readByte() == 1;
        if(!successfulEncryption) {
            /**
             * Invalid RSA header key.
             */
            Response.BAD_SESSION_ID.send(channel);
            return null;
        }

        int[] keys = new int[]{rsaBuffer.readInt(), rsaBuffer.readInt(), rsaBuffer.readInt(), rsaBuffer.readInt()};
        long reportedSeed = rsaBuffer.readLong();
        int[] previousXteaKeys;

        int tfaCode = 0;
        boolean tfaTrust = false;
        int tfaTrustHash = 0;
        String email = "";
        String password;
        if (reconnecting) {
            previousXteaKeys = new int[]{rsaBuffer.readInt(), rsaBuffer.readInt(), rsaBuffer.readInt(), rsaBuffer.readInt()};
            tfaCode = -1;
            password = null;
        } else {
            int loginType = rsaBuffer.readByte();
            if (loginType == 0 || loginType == 1) {
                /**
                 * Login attempt with 2fa code
                 */
                tfaCode = rsaBuffer.readMedium();
                tfaTrust = loginType == 1;
                rsaBuffer.skip(1);
            } else if (loginType == 3) {
                /**
                 * Login attempt with a 2fa trust key
                 */
                tfaTrustHash = rsaBuffer.readInt();
            } else if (loginType == 2) {
                /**
                 * No code no trust key
                 */
                rsaBuffer.skip(4);
            }/* else {
                *//**
                 * Login attempt with email
                 *//*
                email = rsaBuffer.readString();
            }*/

            rsaBuffer.skip(1);
            password = rsaBuffer.readString();
        }

        byte[] bytes = new byte[in.remaining()];
        in.readBytes(bytes);
        in = new InBuffer(bytes);
        in.decode(keys, 0, bytes.length);

        int mac_length = in.readUnsignedByte();
        byte[] mac_data = new byte[mac_length];
        in.readBytes(mac_data);

        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < mac_data.length; index++) {
            sb.append(String.format("%02X%s", mac_data[index], (index < mac_length - 1) ? "-" : ""));
        }
        String macAddress = sb.toString();
        String uuid = in.readString();
        String name = in.readString();
        Pattern pattern = Pattern.compile("[A-Za-z0-9_ ]+");
        if(name == null || name.length() > 12 || name.length() < 3 || (name = name.trim()).isEmpty() || !pattern.matcher(name).matches() || password == null || (password = password.trim()).isEmpty()) {
            /**
             * Invalid login
             */
            Response.INVALID_LOGIN.send(channel);
            return null;
        }
        int clientSettings = in.readUnsignedByte();
        boolean resizable = (clientSettings >> 1) == 1;
        boolean lowDetail = (clientSettings & 0xff) == 1;
        int clientWidth = in.readUnsignedShort();
        int clientHeight = in.readUnsignedShort();

        in.skip(24); //random.dat data
        in.readString(); //from params
        in.readInt(); //from params

        /* device info */
        int check = in.readUnsignedByte();
        in.readUnsignedByte();
        if (check != 8 ) {
            /*
             * Invalid login
             */
            Response.INVALID_LOGIN.send(channel);
            return null;
        }
        int osType = in.readUnsignedByte();
        int bit64 = in.readUnsignedByte();
        int osVersionType = in.readUnsignedByte();
        //there is more, but we don't need it.

        /* skipping lots of data */
        if(tfaTrustHash != 0) {
            /**
             * Check if 2fa trust hash matches
             */
            int osTypeStored = (tfaTrustHash >> 24) & 0xff;
            int bit64Stored = (tfaTrustHash >> 16) & 0xff;
            int osVersionTypeStored = (tfaTrustHash >> 8) & 0xff;
            if(osTypeStored != osType || bit64Stored != bit64 || osVersionTypeStored != osVersionType)
                tfaTrustHash = 0; //device doesn't match up
        } else if(tfaTrust) {
            /**
             * Create a new 2fa trust hash
             */
            tfaTrustHash = osType << 24 | bit64 << 16 | osVersionType << 8 | Random.get(1, 254);
        }

        return new LoginInfo(channel, name, password, email, macAddress, uuid, tfaCode, tfaTrust, tfaTrustHash, worldId, keys);
    }

    /**
     * Checks if the {@code channel}'s remote ip address has 2 or more players already in the wilderness.
     */
    private static boolean isAtWildernessLimitForIP(Channel channel) {
        int ip = IPAddress.toInt(IPAddress.get(channel)),
            count = 0;

        for (Player player : Wilderness.players) {
            if (player.getIpInt() == ip && ++count >= 2) {
                return true;
            }
        }

        return false;
    }

}