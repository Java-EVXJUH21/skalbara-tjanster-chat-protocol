package org.example.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.example.ChatServerHandler;

public abstract class Packet {

    private final byte packetId;

    public Packet(byte packetId) {
        this.packetId = packetId;
    }

    public void read(ByteBuf buf) {}
    public void write(ByteBuf buf) {}
    public void handle(ChatServerHandler handler, ChannelHandlerContext ctx) {}

    public byte getPacketId() {
        return packetId;
    }
}
