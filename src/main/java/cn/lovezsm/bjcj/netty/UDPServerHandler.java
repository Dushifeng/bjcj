package cn.lovezsm.bjcj.netty;

import cn.lovezsm.bjcj.config.GlobeConf;
import cn.lovezsm.bjcj.entity.Message;
import cn.lovezsm.bjcj.utils.DataUtil;
import cn.lovezsm.bjcj.utils.LogUtil;
import cn.lovezsm.bjcj.utils.SpringUtil;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import io.netty.channel.socket.DatagramPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class UDPServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    public static boolean startTag = true;
//    public static int latticeId;

    DataUtil dataUtil;
    GlobeConf globeConf;

    LogUtil logUtil;
//    LogConf logConf;
    public UDPServerHandler() {
        dataUtil = SpringUtil.getBean(DataUtil.class);
//        dataFilter = SpringUtil.getBean(DataFilter.class);
        logUtil = SpringUtil.getBean(LogUtil.class);
//        logConf = SpringUtil.getBean(LogConf.class);
        globeConf = SpringUtil.getBean(GlobeConf.class);
    }

    private static final Logger log= LoggerFactory.getLogger(UDPServerHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        if(startTag==false){
            dataUtil.clearUpRecord(null);
            return;
        }
        String s = ByteBufUtil.hexDump(datagramPacket.content());
//        System.out.println(s);
//        String apMac= s.substring(46, 58);
//        logUtil.log(System.currentTimeMillis()+":"+s,apMac+".log");
        String str = this.filter(s);
//
        if(str!=null){

            List<Message> messages = dataUtil.analyzeData(str,null);
            if(messages.size()>0){
//                logUtil.logRaw(messages);
                for(Message message:messages){
                    logUtil.log(message.getTime()+","+message.getApMac()+","+message.getDevMac()+","+logUtil.getLogConf().getGridId()+","+globeConf.getApConf().getApId(message.getApMac())+","+message.getFrequency()+","+message.getRssi(),message.getDevMac()+logUtil.getLogConf().getLogPath()+"raw.log");
//                    System.out.println(message);
                }
                dataUtil.putData(messages);
            }
        }
    }

    public static String filter(String str){
        //1.长度 2.开头
        if(str.length()<=58){
            return null;
        }else if(!str.substring(0,4).equals("3747")){
            return null;
        }
        return str;
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
