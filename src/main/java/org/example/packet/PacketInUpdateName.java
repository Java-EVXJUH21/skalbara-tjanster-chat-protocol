package org.example.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.example.ChatServerHandler;
import org.example.Util;

public class PacketInUpdateName extends Packet {

    private String name;

    public PacketInUpdateName() {
        super((byte) 0);
    }

    @Override
    public void read(ByteBuf buf) {
        this.name = Util.readString(buf);
    }

    @Override
    public void handle(ChatServerHandler handler, ChannelHandlerContext ctx) {
        System.out.println("Update name to: " + name);
        ctx.channel().attr(ChatServerHandler.USERNAME).set(name);
    }
}
