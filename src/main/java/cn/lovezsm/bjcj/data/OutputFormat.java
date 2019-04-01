package cn.lovezsm.bjcj.data;

import lombok.Data;
import org.quartz.Scheduler;

import java.util.List;
@Data
public class OutputFormat {
    private List<Object> argsStr;
    private String formatString;
    private String className;
    public String getContent(List<Object> args){
        return String.format(formatString,args);
    }

}
