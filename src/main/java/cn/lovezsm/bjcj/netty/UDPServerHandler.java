package cn.lovezsm.bjcj.netty;

import cn.lovezsm.bjcj.config.APConf;
import cn.lovezsm.bjcj.config.LogConf;
import cn.lovezsm.bjcj.data.DataFilter;
import cn.lovezsm.bjcj.entity.Message;
import cn.lovezsm.bjcj.utils.DataUtils;
import cn.lovezsm.bjcj.utils.LogUtil;
import cn.lovezsm.bjcj.utils.SpringUtil;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import io.netty.channel.socket.DatagramPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class UDPServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    public static boolean startTag = true;
//    public static int latticeId;
    DataUtils dataUtils;
    DataFilter dataFilter;



//    LogUtil logUtil;
//    LogConf logConf;
    public UDPServerHandler() {
//        dataUtils = SpringUtil.getBean(DataUtils.class);
//        dataFilter = SpringUtil.getBean(DataFilter.class);
//        logUtil = SpringUtil.getBean(LogUtil.class);
//        logConf = SpringUtil.getBean(LogConf.class);
    }

    private static final Logger log= LoggerFactory.getLogger(UDPServerHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        if(startTag==false){
            dataUtils.clearUpRecord(null);
            return;
        }
        String s = ByteBufUtil.hexDump(datagramPacket.content());
        System.out.println(s);
//        String str = dataFilter.filter(s);

//        if(str!=null){
//            List<Message> messages = dataUtils.analyzeData(str);
//            if(messages.size()>0){
////                logUtil.logRaw(messages);
//                for(Message message:messages){
////                    logUtil.log("("+message.getTime()+","+apConf.getApId(message.getApMac())+",'"+message.getApMac()+"','"+message.getDevMac()+"',"+logConf.getGridId()+","+message.getFrequency()+","+message.getRssi()+"),",message.getDevMac()+"_"+logConf.getGridId()+"_"+"raw.log");
//                }
//
//                dataUtils.putData(messages);
//            }
//        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        // We don't close the channel because we can keep serving requests.
    }

}
