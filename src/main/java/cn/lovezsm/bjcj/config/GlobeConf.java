package cn.lovezsm.bjcj.config;

import cn.lovezsm.bjcj.data.FingerPrint;
import cn.lovezsm.bjcj.data.GridMap;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("singleton")
@ConfigurationProperties("default")
@Data
public class GlobeConf {
    private AlgorithmConf algorithmConf;
    private APConf apConf;
    private MessageConf messageConf;

    private List<FingerPrint> fingerPrints;
    private GridMap gridMap;
    private JobConf jobConf;
    private FileConf fileConf;
}
