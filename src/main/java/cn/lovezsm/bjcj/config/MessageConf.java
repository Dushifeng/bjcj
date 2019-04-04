package cn.lovezsm.bjcj.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@Scope("singleton")
@ConfigurationProperties("message")
public class MessageConf {

    //    private FloorPlan<String,String> type;
    private int rssiMin;
    private List<String> macAllow = new ArrayList<>();
    private List<String> macRefuse = new ArrayList<>();

    public int getRssiMin() {
        return rssiMin;
    }

    public void setRssiMin(int rssiMin) {
        this.rssiMin = rssiMin;
    }

    public List<String> getMacAllow() {
        return macAllow;
    }

    public void setMacAllow(List<String> macAllow) {
        this.macAllow = macAllow;
    }

    public List<String> getMacRefuse() {
        return macRefuse;
    }

    public void setMacRefuse(List<String> macRefuse) {
        this.macRefuse = macRefuse;
    }
}
