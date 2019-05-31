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

    @Autowired
    GlobeConf globeConf;

    @Override
    public void run(String... args) throws Exception {
        String avgPath_2G = globeConf.getFileConf().getAvgPath_2G();
        String stdPath_2G = globeConf.getFileConf().getStdPath_2G();

        String avgPath_5G_G100 = globeConf.getFileConf().getAvgPath_5G_G100();
        String stdPath_5G_G100 = globeConf.getFileConf().getStdPath_5G_G100();

        String avgPath_5G_L100 = globeConf.getFileConf().getAvgPath_5G_L100();
        String stdPath_5G_L100 = globeConf.getFileConf().getStdPath_5G_L100();

        String gridPath = globeConf.getFileConf().getGridFile();

        FingerPrintBuilder fingerPrintBuilder_2G = new FingerPrintBuilderByFile(new File(avgPath_2G),new File(stdPath_2G),globeConf.getApConf().getApnum());
        FingerPrintBuilder fingerPrintBuilder_5G_L100 = new FingerPrintBuilderByFile(new File(avgPath_5G_L100),new File(stdPath_5G_L100),globeConf.getApConf().getApnum());
        FingerPrintBuilder fingerPrintBuilder_5G_G100 = new FingerPrintBuilderByFile(new File(avgPath_5G_G100),new File(stdPath_5G_G100),globeConf.getApConf().getApnum());


        FingerPrint fingerprint2G = fingerPrintBuilder_2G.build("2G",false);
        FingerPrint fingerPrint5G_L100 = fingerPrintBuilder_5G_L100.build("5G_L100",false);
        FingerPrint fingerPrint5G_G100 = fingerPrintBuilder_5G_G100.build("5G_G100",false);
//        FingerPrint fingerPrint5G = fingerPrintBuilder_2G.build("5G",true);

        GlobeConf globeConf = SpringUtil.getBean(GlobeConf.class);
        List<FingerPrint> fingerPrints = new ArrayList<>();
        fingerPrints.add(fingerprint2G);
        fingerPrints.add(fingerPrint5G_G100);
        fingerPrints.add(fingerPrint5G_L100);

        globeConf.setFingerPrints(fingerPrints);
        System.out.println("fingerPrints.size()------------------"+fingerPrints.size());
        GridMap gridMap = GridMap.buildByFile(new File(gridPath),"");
        globeConf.setGridMap(gridMap);
        System.out.println("启动配置项启动器....");
    }
}
