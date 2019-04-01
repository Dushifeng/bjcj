package cn.lovezsm.bjcj;

import cn.lovezsm.bjcj.config.APConf;
import cn.lovezsm.bjcj.config.AlgorithmConf;
import cn.lovezsm.bjcj.entity.AP;
import cn.lovezsm.bjcj.repository.APConfRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DsfApplicationTests {

    @Test
    public void contextLoads() {

    }
    private static final Logger log= LoggerFactory.getLogger(DsfApplicationTests.class);

    @Autowired
    AlgorithmConf algorithmConf;
    @Autowired
    APConfRepository apConfRepository;
    @Test
    public void testMongo(){
        System.out.println("ok");
        APConf apconf = new APConf();
        List<AP> aps = new ArrayList<>();
        aps.add(new AP("70476002A30B",35,6.3,0));
        aps.add(new AP("70476002A30C",39,9.3,1));
        aps.add(new AP("70476002A30D",33,16.3,2));
        aps.add(new AP("70476002A30E",17,14.3,3));
        aps.add(new AP("70476002A30F",22,19.3,4));
        aps.add(new AP("70476002A30A",27,13.8,5));
        apconf.setInfo(aps);
        apConfRepository.save(apconf);
    }


}
