package cn.lovezsm.bjcj.controllers;

import cn.lovezsm.bjcj.config.GlobeConf;
import cn.lovezsm.bjcj.utils.DataUtil;
import cn.lovezsm.bjcj.entity.*;
import cn.lovezsm.bjcj.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class MainController {
    @Autowired
    DataUtil dataUtil;
    @Autowired
    LocationService ls = new LocationService();

    List<LocalizeReturnVal> testLR = new ArrayList<>();
    Timer timer;
    @Autowired
    GlobeConf globeConf;

    @GetMapping("/getAPsInfo")
    @ResponseBody
    public List<AP> getAPLoc(){
//        List<AP> info = apConf.getInfo();

        return globeConf.getApConf().getInfo();
    }

    Map<Integer,Double[]> restrictedArea=new HashMap<>();

    @GetMapping("/getLocInfo")
    @ResponseBody
    public  List<LocalizeReturnVal> getLocInfo(){
//        {x1:72,y1:11,x2:166,y2:92},
//        {x1:69,y1:92,x2:163,y2:118},
//        {x1:263,y1:10,x2:455,y2:92},
//        {x1:358,y1:92,x2:497,y2:173},
//        {x1:263,y1:666,x2:396,y2:748},
        if(restrictedArea.isEmpty()){
            restrictedArea.put(1,new Double[]{0d,0d,7d,6d});
            restrictedArea.put(2,new Double[]{14d,0d,28d,6d});
            restrictedArea.put(3,new Double[]{0d,6d,6.8d,8.1d});
            restrictedArea.put(4,new Double[]{21d,6d,28d,12d});
            restrictedArea.put(5,new Double[]{14d,48d,23.5,54d});
            restrictedArea.put(6,new Double[]{14d,32d,19.25,46.2});
        }

        List<LocalizeReturnVal> locVal = dataUtil.getLocVal();
//        List<LocalizeReturnVal> submitData = new ArrayList<>();
        for(LocalizeReturnVal val:locVal){
            double x = val.getX();
            double y = val.getY();
            if(val.getDevMac().equals("a4be2bed0a30")||val.getDevMac().equals("a4be2bed0a30")||val.getDevMac().equals("340a98839bb0")||val.getDevMac().equals("a4be2bed00d0")){
                System.out.println(val.getX()+"  "+val.getY()+"  "+val);
            }
            for(Map.Entry<Integer,Double[]> entry:restrictedArea.entrySet()){
                Double[] area = entry.getValue();
                if((area[0]<=x&&area[2]>=x)&&(area[1]<=y&&area[3]>=y)){
                    val.setValid(false);
                }
            }
        }

//        for(LocalizeReturnVal val:locVal){
//            if(val.isValid()){
//                submitData.add(val);
//            }
//        }

        Map<String,Object> map = new HashMap<>();
        map.put("locPoint",locVal);
        map.put("heatMap",ls.calculateDeviceDensity(locVal));
        return locVal;
    }
    @ResponseBody
    @GetMapping("/getHeatMapData")
    public CalculateDeviceDensityReturnVal getHeatMap(){
//        CalculateDeviceDensityReturnVal heatMapData = ls.getHeatMapData();
////        if (heatMapData == null){
////            return new CalculateDeviceDensityReturnVal();
////        }
////        return heatMapData;
        return null;
    }

    @GetMapping("/")
    public String bjcjPage(){
        return "bjcjshowpage";
//        return "SZDS";
    }
}
