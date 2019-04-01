package cn.lovezsm.bjcj;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;


public class QuoteOfTheMomentServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {


    @Override
    public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {

        ByteBuf buf = packet.copy().content();
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println(body);//打印收到的信息

    }
}
