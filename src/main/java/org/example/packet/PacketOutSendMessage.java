package org.example.packet;

import io.netty.buffer.ByteBuf;
import org.example.Util;

public class PacketOutSendMessage extends Packet {

    private final String name, message;

    public PacketOutSendMessage(String name, String message) {
        super((byte) 0);

        this.name = name;
        this.message = message;
    }

    @Override
    public void write(ByteBuf buf) {
        Util.writeString(buf, name);
        Util.writeString(buf, message);
    }
}
