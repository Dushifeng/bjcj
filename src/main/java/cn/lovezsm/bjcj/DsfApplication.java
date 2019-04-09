package cn.lovezsm.bjcj;

import cn.lovezsm.bjcj.netty.UDPServer;
import cn.lovezsm.bjcj.repository.BaseRepository;
import cn.lovezsm.bjcj.repository.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class DsfApplication{

    public static void main(String[] args) {
        SpringApplication.run(DsfApplication.class, args);
    }

}
