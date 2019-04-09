package cn.lovezsm.bjcj.task;

import cn.lovezsm.bjcj.config.APConf;
import cn.lovezsm.bjcj.config.AlgorithmConf;
import cn.lovezsm.bjcj.config.GlobeConf;
import cn.lovezsm.bjcj.config.MessageConf;
import cn.lovezsm.bjcj.data.FingerPrint;
import cn.lovezsm.bjcj.data.FingerPrintBuilder;
import cn.lovezsm.bjcj.data.FingerPrintBuilderByFile;
import cn.lovezsm.bjcj.data.GridMap;
import cn.lovezsm.bjcj.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(1)
public class ConfigStarter implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        String avgPath_2G = "C:\\data\\Fingerprint_avg_2GHZ.dat";
        String stdPath_2G = "C:\\data\\Fingerprint_std_2GHZ.dat";
        String gridPath = "C:\\data\\grid.txt";
        FingerPrintBuilder fingerPrintBuilder = new FingerPrintBuilderByFile(new File(avgPath_2G),new File(stdPath_2G),6);
        FingerPrint fingerprint2G = fingerPrintBuilder.build("2G",false);
        FingerPrint fingerPrint5G = fingerPrintBuilder.build("5G",true);
        GlobeConf globeConf = SpringUtil.getBean(GlobeConf.class);
        List<FingerPrint> fingerPrints = new ArrayList<>();
        fingerPrints.add(fingerprint2G);
        fingerPrints.add(fingerPrint5G);
        globeConf.setFingerPrints(fingerPrints);

        GridMap gridMap = GridMap.buildByFile(new File(gridPath),"北京城建");
        globeConf.setGridMap(gridMap);
        System.out.println("启动配置项启动器....");
    }
}
