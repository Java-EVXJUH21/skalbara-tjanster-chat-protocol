package org.example.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.example.ChatServerHandler;
import org.example.Util;

public class PacketInReceiveMessage extends Packet {

    private String message;

    public PacketInReceiveMessage() {
        super((byte) 1);
    }

    @Override
    public void read(ByteBuf buf) {
        this.message = Util.readString(buf);
    }

    @Override
    public void handle(ChatServerHandler handler, ChannelHandlerContext ctx) {
        var name = ctx.channel().attr(ChatServerHandler.USERNAME).get();

        var packet = new PacketOutSendMessage(name, message);
        for (var channel : ChatServerHandler.CHANNELS) {
            channel.writeAndFlush(packet);
        }
    }
}
