package cn.lovezsm.bjcj.task;

import cn.lovezsm.bjcj.config.APConf;
import cn.lovezsm.bjcj.config.AlgorithmConf;
import cn.lovezsm.bjcj.config.MessageConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class ConfigStarter implements CommandLineRunner {



    @Override
    public void run(String... args) throws Exception {

        System.out.println("启动配置项启动器....");

    }
}
