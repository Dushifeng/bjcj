package cn.lovezsm.bjcj.controllers;

import cn.lovezsm.bjcj.config.APConf;
import cn.lovezsm.bjcj.config.AlgorithmConf;
import cn.lovezsm.bjcj.config.MessageConf;
import cn.lovezsm.bjcj.entity.AP;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ConfController {



//    private static String SUCCESS = "success";
//    @ResponseBody
//    @GetMapping("getAPConf")
//    public APConf getAPConf(){
//        List<APConf> all = apConfRepository.findAll();
//        if(all.isEmpty()){
//            return null;
//        }
//        return all.get(0);
//    }
//    @ResponseBody
//    @GetMapping("getAlgorithmConf")
//    public AlgorithmConf getAlgorithmConf(){
//        return algorithmRepository.findAll().get(0);
//    }
//
//    @PostMapping(value = "updateAPConf",produces = "application/json;utf-8")
//    @ResponseBody
//    public String updateAPConf(String apsjson){
//        List<AP> aps = JSONArray.parseArray(apsjson,AP.class);
//        System.out.println(aps);
//        apConfRepository.deleteAll();
//        apConfRepository.save(new APConf(aps));
//        return SUCCESS;
//    }

//    @ResponseBody
//    @GetMapping("updateData")
//    public List<Object> updateData(){
//
//        List<APConf> apConfs = apConfRepository.findAll();
//
//    }
}
