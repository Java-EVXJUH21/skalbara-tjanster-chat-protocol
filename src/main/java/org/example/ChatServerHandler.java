package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.example.packet.Packet;
import org.example.packet.PacketInReceiveMessage;
import org.example.packet.PacketInUpdateName;

public class ChatServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    public static final ChannelGroup CHANNELS = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static final AttributeKey<String> USERNAME = AttributeKey.valueOf("username");
    private static final Class<? extends Packet>[] PACKETS;

    static {
        PACKETS = new Class[]{
                PacketInUpdateName.class,
                PacketInReceiveMessage.class
        };
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CHANNELS.add(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        var packetId = byteBuf.readByte();
        if (packetId < 0 || packetId >= PACKETS.length)
            return;

        var packet = Util.createPacket(PACKETS[packetId]);
        if (packet == null)
            return;

        packet.read(byteBuf);
        packet.handle(this, ctx);
    }
}
