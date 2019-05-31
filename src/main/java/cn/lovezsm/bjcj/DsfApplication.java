package cn.lovezsm.bjcj;

import cn.lovezsm.bjcj.netty.UDPServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class DsfApplication{

    public static void main(String[] args) {
        SpringApplication.run(DsfApplication.class, args);
    }
}
