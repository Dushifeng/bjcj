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


@Component
@ConfigurationProperties(prefix = "ap")
@Scope("singleton")
public class APConf {
    private List<AP> info;
    int apnum;

    public int getApnum() {
        return apnum;
    }


    @PostConstruct
    public void init(){
        for(int i=0;i<info.size();i++){
            AP ap = info.get(i);
            ap.setMac(ap.getMac().toLowerCase());
            ap.setId(i);
        }
        apnum = info.size();

    }

    public void setApnum(int apnum) {
        this.apnum = apnum;
    }

    public List<AP> getInfo() {
        return info;
    }

    public void setInfo(List<AP> info) {
        this.info = info;
    }

    public int getApId(String apMac) {
        for(AP ap:info){
            if (ap.getMac().equals(apMac)){
                return ap.getId();
            }
        }
        return -1;
    }

    public boolean containMac(String apMac){
        for (AP ap:info){
            if(ap.getMac().equals(apMac.toLowerCase())){
                return true;
            }
        }
        return false;
    }
}
