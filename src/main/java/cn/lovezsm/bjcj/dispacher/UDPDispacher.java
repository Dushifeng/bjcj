package cn.lovezsm.bjcj.dispacher;


import cn.lovezsm.bjcj.config.GlobeConf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UDPDispacher {
    @Autowired
    GlobeConf globeConf;

    private static UDPDispacher instance = new UDPDispacher();
    private UDPDispacher(){}

    public static UDPDispacher getInstance() {
        return instance;
    }
    private static Map<String,Object> coursesTable = new ConcurrentHashMap<>();

    public void messageRecived(ChannelHandlerContext channelHandlerContext, String message){
        for(Map.Entry<String,Object> entry:coursesTable.entrySet()){
            if(globeConf.getJobConf().getJobSwitch().containsKey(entry.getKey())){
                Boolean isOpen = globeConf.getJobConf().getJobSwitch().get(entry.getKey());
                if(isOpen!=null&&isOpen==false){
                    continue;
                }else {
                    UDPServiceCourse course = (UDPServiceCourse) entry.getValue();
                    course.dealMessage(channelHandlerContext,message);
                }
            }
        }
    }

    public void setCourses(Map<String,Object> coursesMap){
        if(coursesMap!=null&&coursesMap.size()>0){
            for(Map.Entry<String,Object> entry:coursesMap.entrySet()){
                coursesTable.put(entry.getKey(),entry.getValue());
            }
        }
    }

}
