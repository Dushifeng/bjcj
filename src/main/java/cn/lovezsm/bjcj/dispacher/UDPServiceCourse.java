package cn.lovezsm.bjcj.dispacher;

import io.netty.channel.ChannelHandlerContext;

public interface UDPServiceCourse {
    void dealMessage(ChannelHandlerContext context,String message);
}
