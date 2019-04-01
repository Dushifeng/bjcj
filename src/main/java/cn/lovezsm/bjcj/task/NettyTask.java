package cn.lovezsm.bjcj.task;

import cn.lovezsm.bjcj.netty.UDPServer;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;


/**
 * @need: port default:16661
 */

@Component
public class NettyTask {
    UDPServer server;
    public void start(int port){
        try {
            server = new UDPServer(port);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop(){

    }

}
