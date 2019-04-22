package cn.lovezsm.bjcj.controllers;

import cn.lovezsm.bjcj.utils.DataUtil;
import cn.lovezsm.bjcj.entity.*;
import cn.lovezsm.bjcj.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    DataUtil dataUtil;
    @Autowired
    LocationService ls = new LocationService();

    @GetMapping("/getAPsInfo")
    @ResponseBody
    public List<AP> getAPLoc(){
//        List<AP> info = apConf.getInfo();
        return null;
    }
    @GetMapping("/getLocInfo")
    @ResponseBody
    public List<LocalizeReturnVal> getLocInfo(){
//        List<WebShowLocData> datas = ls.test1();
        List<LocalizeReturnVal> locVal = dataUtil.getLocVal();
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
}
