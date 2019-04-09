package cn.lovezsm.bjcj.data;

import cn.lovezsm.bjcj.config.APConf;
import cn.lovezsm.bjcj.config.GlobeConf;
import cn.lovezsm.bjcj.config.MessageConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class DataFilter {

    @Autowired
    GlobeConf globeConf;

    public String filter(String str){

        //1.长度 2.开头
        if(str.length()<=58){
            return null;
        }else if(!str.substring(0,4).equals("3747")){
            return null;
        }

        String devMac = str.substring(46, 58);
        if(!globeConf.getApConf().containMac(devMac)){
            return null;
        }
        return str;
    }

    public boolean isOKMessage(String mac){
        if(globeConf.getMessageConf().getMacAllow().size()>0){
            return globeConf.getMessageConf().getMacAllow().contains(mac);
        }
        if(globeConf.getMessageConf().getMacRefuse().size()>0){
            return globeConf.getMessageConf().getMacRefuse().contains(mac);
        }
        return true;
    }
}
