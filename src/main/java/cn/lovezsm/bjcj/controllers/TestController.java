package cn.lovezsm.bjcj.controllers;

import cn.lovezsm.bjcj.config.APConf;
import cn.lovezsm.bjcj.config.GlobeConf;
import cn.lovezsm.bjcj.data.TestPageReport;
import cn.lovezsm.bjcj.service.TestService;
import cn.lovezsm.bjcj.utils.DataUtil;
import cn.lovezsm.bjcj.utils.FileUtil;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import web.ConfData;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

import static org.quartz.DateBuilder.evenSecondDate;
import static org.quartz.DateBuilder.futureDate;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Controller
public class TestController {
    @Autowired
    TestService testService;

    @Autowired
    GlobeConf globeConf;


    @ResponseBody
    @PostMapping("/updateRawFile")
    public List<TestPageReport> updateFile(HttpSession session, @RequestParam("file[]") MultipartFile[] files){
        if(session.getAttribute("reports")==null){
            session.setAttribute("reports",new ArrayList<TestPageReport>());
        }
        List<TestPageReport> reports = new ArrayList<TestPageReport>();
        if(files!=null&&files.length>0) {
            for(MultipartFile file:files){
                if(isMacNameFile(file)){
                    TestPageReport report = testService.getSingleMacFileReport(file);
                    if (report!=null)
                        reports.add(report);
                }else {
                    List<TestPageReport> reports1 = testService.separationRawFile(file);
                    reports.addAll(reports1);
                }

            }
        }
        ArrayList<TestPageReport> reports1 = (ArrayList<TestPageReport>) session.getAttribute("reports");
        reports1.addAll(reports);

        return reports;
    }





    private boolean isMacNameFile(MultipartFile file) {
        APConf apConf = globeConf.getApConf();
        return apConf.containMac(FileUtil.getFileNameNoEx(file.getOriginalFilename()));
    }

}
