package cn.lovezsm.bjcj.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageConf {

    //    private FloorPlan<String,String> type;
    private int rssiMin;
    private List<String> macAllow = new ArrayList<>();
    private List<String> macRefuse = new ArrayList<>();

}
