package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.IdHolder;

import java.util.function.Consumer;

@IdHolder(ids = {100, 8, 54, 39})
public class InputHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        if(opcode == 8) {
            Consumer<Integer> consumer = player.consumerInt;
            if(consumer != null) {
                player.consumerInt = null;
                consumer.accept(in.readInt());
                if(player.retryIntConsumer) {
                    player.consumerInt = consumer;
                    player.retryIntConsumer = false;
                }
            }
        } else if(opcode == 54) {
            Consumer<Integer> consumer = player.consumerInt;
            if(consumer != null) {
                player.consumerInt = null;
                consumer.accept(in.readUnsignedShort());
                if(player.retryIntConsumer) {
                    player.consumerInt = consumer;
                    player.retryIntConsumer = false;
                }
            }
        } else {
            Consumer<String> consumer = player.consumerString;
            if(consumer != null) {
                player.consumerString = null;
                consumer.accept(in.readString());
                if(player.retryStringConsumer) {
                    player.consumerString = consumer;
                    player.retryStringConsumer = false;
                }
            }
        }
    }

}