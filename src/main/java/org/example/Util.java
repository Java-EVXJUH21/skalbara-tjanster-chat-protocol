package org.example;

import io.netty.buffer.ByteBuf;
import org.example.packet.Packet;

public class Util {

    public static String readString(ByteBuf byteBuf) {
        var length = byteBuf.readInt();
        var buffer = new byte[length];
        byteBuf.readBytes(buffer, 0, length);

        return new String(buffer, 0, length);
    }

    public static void writeString(ByteBuf byteBuf, String s) {
        byteBuf.writeInt(s.length());
        byteBuf.writeBytes(s.getBytes());
    }

    public static Packet createPacket(Class<? extends Packet> clazz) {
        try {
            var constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (Exception ignored) {
            return null;
        }
    }
}
