package cn.lovezsm.bjcj.config;

import cn.lovezsm.bjcj.entity.AP;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;


@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class APConf {
    private List<AP> info;

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
