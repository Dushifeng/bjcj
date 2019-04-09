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

    public int getApnum() {
        return info.size();
    }
    public boolean containMac(String apMac){
        for (AP ap:info){
            if(ap.getMac().equals(apMac.toLowerCase())){
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
}
