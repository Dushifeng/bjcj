package cn.lovezsm.bjcj.data;

import cn.lovezsm.bjcj.config.APConf;
import cn.lovezsm.bjcj.config.MessageConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class DataFilter {

    @Autowired
    APConf apConf;
    @Autowired
    MessageConf conf;

    public String filter(String str){

        //1.长度 2.开头
        if(str.length()<=58){
            return null;
        }else if(!str.substring(0,4).equals("3747")){
            return null;
        }

        String devMac = str.substring(46, 58);
        if(!apConf.containMac(devMac)){
            return null;
        }
        return str;
    }

    public boolean isOKMessage(String mac){
        if(conf.getMacAllow().size()>0){
            return conf.getMacAllow().contains(mac);
        }
        if(conf.getMacRefuse().size()>0){
            return conf.getMacRefuse().contains(mac);
        }
        return true;
    }
}
