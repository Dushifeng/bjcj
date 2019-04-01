package cn.lovezsm.bjcj.data;

import cn.lovezsm.bjcj.config.APConf;
import cn.lovezsm.bjcj.config.MessageConf;
import cn.lovezsm.bjcj.repository.APConfRepository;
import cn.lovezsm.bjcj.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class DataFilter {

    @Autowired
    APConfRepository apConfRepository;
    @Autowired
    MessageRepository messageRepository;



    public String filter(String str){
        APConf apConf = apConfRepository.findAll().get(0);

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
        MessageConf conf = messageRepository.findAll().get(0);
        if(conf.getMacAllow().size()>0){
            return conf.getMacAllow().contains(mac);
        }
        return conf.getMacRefuse().contains(mac);
    }
}
