package cn.lovezsm.bjcj.config;

import cn.lovezsm.bjcj.entity.AP;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;


@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class APConf {
    private List<AP> info;
    private int coefficientNum=3;
    public int getApnum() {
        return info.size();
    }
    public boolean containMac(String apMac){

        String[] split = apMac.split(":");
        StringBuffer mac = new StringBuffer();
        for (String s:split){
            mac.append(s.toLowerCase());
        }
        apMac = mac.toString();
        for (AP ap:info){
            if(ap.getMac().equals(apMac)){
                return true;
            }
        }
        return false;
    }

    public int getApId(String apMac){
        for(int i=0;i<info.size();i++){
            if(info.get(i).getMac().equals(apMac)){
                return i;
            }
        }
        return -1;
    }

    public int getCoefficientNum() {
        return coefficientNum;
    }

    public int getFrequencyCoefficient(int frequency) {
        if(frequency<=14){
            return 0;
        }
        else if(frequency<=64){

            return 1;

        }else{

            return 2;
        }


    }
}
