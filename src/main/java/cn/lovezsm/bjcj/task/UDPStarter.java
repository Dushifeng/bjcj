package cn.lovezsm.bjcj.task;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1000)
public class UDPStarter implements CommandLineRunner {
    @Autowired
    NettyTask nettyTask;
    @Override
    public void run(String... args) throws Exception {
        nettyTask.start(16662);
    }
}
